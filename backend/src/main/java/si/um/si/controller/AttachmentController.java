package si.um.si.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import si.um.si.model.Attachment;
import si.um.si.service.AttachmentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("taskId") Long taskId) {
        try {
            Attachment attachment = attachmentService.saveAttachment(file, taskId);
            return ResponseEntity.ok("File uploaded successfully with ID: " + attachment.getId());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Attachment>> getAttachments(@PathVariable Long taskId) {
        List<Attachment> attachments = attachmentService.getAttachmentsByTaskId(taskId);
        return ResponseEntity.ok(attachments);
    }
}