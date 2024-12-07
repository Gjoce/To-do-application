package si.um.si.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.um.si.model.Attachment;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByTaskId(Long taskId);
}
