package si.um.si.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import si.um.si.model.Attachment;
import si.um.si.repository.AttachmentRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AttachmentService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment saveAttachment(MultipartFile file, Long taskId) throws IOException {
        System.out.println("File size before saving: " + file.getSize());

        // Ensure the upload directory exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new IOException("Failed to create directory at " + uploadDir);
            }
        }


        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IOException("File name is not valid");
        }

        Path filePath = Paths.get(uploadDir, fileName);


        file.transferTo(filePath.toFile());


        File savedFile = filePath.toFile();
        System.out.println("File size after saving: " + savedFile.length());

        // Save metadata to the database
        Attachment attachment = new Attachment();
        attachment.setTaskId(taskId);
        attachment.setFileName(fileName);
        attachment.setFilePath(filePath.toString());
        attachment.setFileType(file.getContentType());
        return attachmentRepository.save(attachment);
    }


    public List<Attachment> getAttachmentsByTaskId(Long taskId) {

        return attachmentRepository.findByTaskId(taskId);
    }



}

