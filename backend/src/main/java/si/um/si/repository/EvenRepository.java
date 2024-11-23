package si.um.si.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import si.um.si.model.Event;
import si.um.si.model.Users;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EvenRepository extends JpaRepository<Event, Long> {

    // Find all events created by a specific user
    List<Event> findByCreatedById(Long userId);

    // Find all events a user is participating in
    List<Event> findByParticipantsId(Long userId);

    // Search events by name or description containing a keyword
    List<Event> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    // Find events starting after a specific time
    List<Event> findByStartTimeAfterOrderByStartTime(LocalDateTime startTime);

    @Query("SELECT e FROM Event e WHERE SIZE(e.participants) < e.maxParticipants")
    List<Event> findByParticipantsSizeLessThanMaxParticipants();
    List<Event> findByParticipantsContaining(Users user);
}
