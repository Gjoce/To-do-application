package si.um.si.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import si.um.si.model.Task;
import si.um.si.model.Users;
import si.um.si.model.enums.Taskstatus;
import si.um.si.repository.TaskRepository;
import si.um.si.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task, Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        // Add validation for the user data here
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("User data is incomplete or invalid");
        }

        task.setUser(user);
        return taskRepository.save(task);
    }


    public Optional<Task> updateTask(long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setTitle(updatedTask.getTitle());
                    existingTask.setDescription(updatedTask.getDescription());
                    existingTask.setStatus(updatedTask.getStatus());
                    existingTask.setPriority(updatedTask.getPriority());
                    existingTask.setDueDate(updatedTask.getDueDate());
                    return taskRepository.save(existingTask);
                });
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task with ID " + id + " not found");
        }
        taskRepository.deleteById(id);
    }

    public List<Task> getFavoriteTasksByUser(Long userId) {
        return taskRepository.findByFavoriteAndUserId(true, userId);
    }

    public Optional<Task> updateFavoriteStatus(long taskId, boolean isFavorite) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setFavorite(isFavorite);
                    return taskRepository.save(task);
                });
    }



    public List<Task> getTasksByStatus(Taskstatus status) {
        return taskRepository.findByStatus(status);
    }
}
