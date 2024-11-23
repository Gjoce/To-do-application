package si.um.si.repository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import si.um.si.model.Event;

@Repository
public interface EvenRepository extends JpaRepository<Event,Long> {
    List<Event> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findByUserId(Long userId);
    List<Event> findByTaskId(Long taskId);
    List<Event> findByType(String type);
}