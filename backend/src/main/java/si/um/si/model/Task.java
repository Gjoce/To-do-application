package si.um.si.model;
import si.um.si.model.enums.*;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(nullable = false)
    private String Title;

    @Column(length = 1000)
    private String Description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Taskstatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Taskpriority priority;

    private LocalDateTime dueDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Task() {}

    public Task(String title, String Description, Taskstatus status, Taskpriority priority, LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.Title = title;
        this.Description = Description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    //Get in set metode

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Taskstatus getStatus() {
        return status;
    }

    public void setStatus(Taskstatus status) {
        this.status = status;
    }

    public Taskpriority getPriority() {
        return priority;
    }

    public void setPriority(Taskpriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    //Avtomatsko posodabljanje
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }




}
