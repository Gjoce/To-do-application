package si.um.si.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import si.um.si.model.Attachment;
import si.um.si.repository.AttachmentRepository;

import java.io.IOException;
import java.util.List;

@Service
public class AttachmentService {

    @Value("${file.upload-dir}")

    String accessToken = System.getenv("ACCESS_TOKEN");
    String oneDriveUploadUrl = System.getenv("ONEDRIVE_UPLOAD_URL");

    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment saveAttachment(MultipartFile file, Long taskId) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IOException("File name is not valid");
        }


        String fileUrl = uploadFileToOneDrive(file);


        Attachment attachment = new Attachment();
        attachment.setTaskId(taskId);
        attachment.setFileName(fileName);
        attachment.setFileType(file.getContentType());
        attachment.setFilePath(fileUrl);
        return attachmentRepository.save(attachment);
    }

    private String uploadFileToOneDrive(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);

        String uploadUrl = oneDriveUploadUrl + fileName + ":/content";
        RestTemplate restTemplate = new RestTemplate();


        ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.PUT, entity, String.class);


        return extractFileUrlFromResponse(response.getBody());
    }

    private String extractFileUrlFromResponse(String responseBody) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();


            JsonNode rootNode = objectMapper.readTree(responseBody);


            return rootNode.path("webUrl").asText();
        } catch (Exception e) {
       
            e.printStackTrace();
            return null;
        }
    }

    public List<Attachment> getAttachmentsByTaskId(Long taskId) {
        return attachmentRepository.findByTaskId(taskId);
    }
}
