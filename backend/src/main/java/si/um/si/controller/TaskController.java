package si.um.si.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import si.um.si.model.Task;
import si.um.si.model.enums.Taskstatus;
import si.um.si.service.TaskService;


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("api/tasks")
    public List<Task> getAllTasks(@RequestParam(required = false) String status) {
        if (status != null) {
            try {
                Taskstatus taskStatus = Taskstatus.valueOf(status.toUpperCase());
                return taskService.getTasksByStatus(taskStatus);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status: " + status);
            }
        }
        return taskService.getAllTasks(); // Retrieve all tasks if no status is provided
    }

    @GetMapping("api/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        return taskService.getTaskById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("api/tasks")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
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
}
