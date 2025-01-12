package si.um.si.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import si.um.si.model.Task;
import si.um.si.model.enums.Taskstatus;
import si.um.si.service.TaskService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {
    private final TaskService taskService;

    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("api/tasks")
    public List<Task> getAllTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Boolean favorite,
            @RequestParam Long userId) {
        if (favorite != null && favorite) {
            return taskService.getFavoriteTasksByUser(userId);
        }
        if (status != null) {
            try {
                Taskstatus taskStatus = Taskstatus.valueOf(status.toUpperCase());
                return taskService.getTasksByStatus(taskStatus);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status: " + status);
            }
        }
        return taskService.getAllTasks();
    }


    @GetMapping("api/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        return taskService.getTaskById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("api/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestParam Long userId) {
        Task createdTask = taskService.createTask(task, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("api/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("api/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("api/tasks/favorites")
    public List<Task> getFavoriteTasks(@RequestParam Long userId) {
        return taskService.getFavoriteTasksByUser(userId);
    }
    @PutMapping("api/tasks/{id}/favorite")
    public ResponseEntity<Task> updateFavoriteStatus(@PathVariable long id, @RequestParam boolean isFavorite) {
        return taskService.updateFavoriteStatus(id, isFavorite)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}

