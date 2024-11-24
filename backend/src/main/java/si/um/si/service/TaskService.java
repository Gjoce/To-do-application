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

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //Prejmi vse taske
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    //Najdi po Id task
    public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }
    @Autowired
    private UserRepository userRepository;


    //Naredi novi task
    public Task createTask(Task task, Long userId) {
        // Ensure userRepository is injected via dependency injection
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        // Assign the user to the task
        task.setUser(user);


        // Save the task to the database
        return taskRepository.save(task);
    }


    //Pososdobi
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
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByStatus(Taskstatus status) {
        return taskRepository.findByStatus(status); // Fetch tasks by status
    }

}
