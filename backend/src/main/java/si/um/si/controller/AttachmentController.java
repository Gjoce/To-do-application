package si.um.si.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import si.um.si.model.Attachment;
import si.um.si.service.AttachmentService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:5173")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("taskId") Long taskId) {
        System.out.println("Received file: " + file.getOriginalFilename() + " with size: " + file.getSize());

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No file uploaded"));
        }

        try {
            System.out.println("Calling saveAttachment method...");
            attachmentService.saveAttachment(file, taskId);  // Log this
            System.out.println("Attachment saved successfully");

            return ResponseEntity.ok(new SuccessResponse("File uploaded successfully", file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();  // Print exception stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error uploading file: " + e.getMessage()));
        }
    }

    @GetMapping("/attachments/certain/{taskId}")
    public ResponseEntity<?> getAttachment(@PathVariable Long taskId) {
        List<Attachment> attachments = attachmentService.getAttachmentsByTaskId(taskId);
        if (attachments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No attachments found for the given task ID");
        }

        // Return the file URL (assuming it's stored in the filePath field)
        String fileUrl = attachments.get(0).getFilePath(); // External URL e.g., OneDrive link
        return ResponseEntity.ok(Map.of("fileUrl", fileUrl));
    }





    @GetMapping("/attachments/{fileName:.+}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir, fileName);
            if (!Files.exists(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            byte[] fileContent = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}


class SuccessResponse {
    private String message;
    private String fileName;

    public SuccessResponse(String message, String fileName) {
        this.message = message;
        this.fileName = fileName;
    }


    public String getMessage() { return message; }
    public String getFileName() { return fileName; }
}

class ErrorResponse {
    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }


    public String getError() { return error; }
}
