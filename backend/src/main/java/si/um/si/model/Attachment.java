package si.um.si.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setFileName(String originalFilename) {
        this.fileName = originalFilename;
    }

    public void setFilePath(String string) {
        this.filePath = string;
    }

    public void setFileType(String contentType) {
        this.fileType = contentType;
    }

    public String getId() {
        return id.toString();
    }

    // Getters and setters
}
