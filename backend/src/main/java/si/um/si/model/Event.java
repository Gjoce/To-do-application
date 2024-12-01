package si.um.si.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "Event name is required.")
    private String name;

    @Column(length = 1000)
    @Size(max = 1000, message = "Description must be under 1000 characters.")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Start time is required.")
    private LocalDateTime startTime;

    @Column(nullable = false)
    @NotNull(message = "End time is required.")
    private LocalDateTime endTime;

    @Column(nullable = false)
    @NotBlank(message = "Location is required.")
    private String location;

    @Min(value = 1, message = "Maximum participants must be at least 1.")
    private int maxParticipants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // Add this annotation
    private Users user;


    @ManyToMany
    @JoinTable(
            name = "Event_Participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore //very important to add this annotation
    private List<Users> participants = new ArrayList<>();

    // Default Constructor
    public Event() {}

    // Parameterized Constructor
    public Event(String name, String description, LocalDateTime startTime, LocalDateTime endTime,
                 String location, int maxParticipants, Users user) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.user = user;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Users> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Users> participants) {
        this.participants = participants;
    }

    public void addParticipant(Users user) {
        participants.add(user);
    }

    public void removeParticipant(Users user) {
        participants.remove(user);
    }

    public void setCreatedBy(Users user) {
        this.user = user;
    }
}
