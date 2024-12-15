package si.um.si.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import si.um.si.model.Attachment;
import si.um.si.repository.AttachmentRepository;

import java.io.IOException;
import java.util.List;

@Service
public class AttachmentService {

    @Value("${onedrive.access-token}")
    private String accessToken;

    @Value("${onedrive.upload-url}")
    private String oneDriveUploadUrl;

    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment saveAttachment(MultipartFile file, Long taskId) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileUrl = uploadFileToOneDrive(file);  // Upload to OneDrive

        // Log the file information
        System.out.println("File uploaded to OneDrive: " + fileName + " URL: " + fileUrl);

        Attachment attachment = new Attachment();
        attachment.setTaskId(taskId);  // Set task ID
        attachment.setFileName(fileName);  // Set file name
        attachment.setFilePath(fileUrl);  // Set file URL (from OneDrive)
        attachment.setFileType(file.getContentType());  // Set file type (MIME type)

        // Log the attachment details before saving
        System.out.println("Saving attachment: " + attachment.toString());

        // Save the attachment to the database
        Attachment savedAttachment = attachmentRepository.save(attachment);
        System.out.println("Attachment saved with ID: " + savedAttachment.getId());

        return savedAttachment;
    }

    private String uploadFileToOneDrive(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);

        // Construct the URL for uploading the file to OneDrive
        String uploadUrl = oneDriveUploadUrl + fileName + ":/content";
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Send the file to OneDrive using the PUT method
            ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.PUT, entity, String.class);

            // Check for successful response
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return extractFileUrlFromResponse(response.getBody()); // Extract file URL from response
            } else {
                throw new IOException("Failed to upload file to OneDrive: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new IOException("Error uploading file to OneDrive: " + e.getMessage(), e);
        }
    }

    private String extractFileUrlFromResponse(String responseBody) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // Log the entire response for debugging
            System.out.println("OneDrive response: " + responseBody);

            // Extract the webUrl (file URL) from the response body
            String fileUrl = rootNode.path("webUrl").asText(null);
            if (fileUrl == null || fileUrl.isEmpty()) {
                throw new IOException("File URL not found in OneDrive response");
            }
            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error extracting file URL from OneDrive response", e);
        }
    }

    public List<Attachment> getAttachmentsByTaskId(Long taskId) {
        return attachmentRepository.findByTaskId(taskId);
    }
}
