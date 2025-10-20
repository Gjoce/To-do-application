
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import si.um.si.model.Task;
import si.um.si.repository.TaskRepository;
import si.um.si.repository.UserRepository;
import si.um.si.service.TaskService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskService_GetAllTasks_Tests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // 🟢 Positive scenario: Tasks exist
    @Test
    @DisplayName("GetAllTasks – returns list of tasks when tasks exist")
    void getAllTasks_ok() {
        // Arrange
        Task t1 = new Task();
        t1.setTitle("Task 1");
        Task t2 = new Task();
        t2.setTitle("Task 2");

        when(taskRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        // Act
        List<Task> result = taskService.getAllTasks();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(task -> "Task 1".equals(task.getTitle())));
        verify(taskRepository, times(1)).findAll();
    }

    // 🔴 Negative scenario: No tasks in database
    @Test
    @DisplayName("GetAllTasks – returns empty list when no tasks exist")
    void getAllTasks_empty() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Task> result = taskService.getAllTasks();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Expected empty list when no tasks exist");
        verify(taskRepository, times(1)).findAll();
    }

    // 🔴 Negative scenario: Database error
    @Test
    @DisplayName("GetAllTasks – throws exception when repository fails")
    void getAllTasks_repositoryError() {
        // Arrange
        when(taskRepository.findAll()).thenThrow(new RuntimeException("Database failure"));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> taskService.getAllTasks());
        assertEquals("Database failure", ex.getMessage());
        verify(taskRepository, times(1)).findAll();
    }
}
