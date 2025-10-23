

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import si.um.si.model.Task;
import si.um.si.model.enums.Taskpriority;
import si.um.si.model.enums.Taskstatus;
import si.um.si.repository.TaskRepository;
import si.um.si.service.TaskService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Filter_task {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Task createMockTask(String title, String description, Taskstatus status, Taskpriority priority, int daysUntilDue) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setPriority(priority);
        task.setDueDate(LocalDateTime.now().plusDays(daysUntilDue));
        return task;
    }

    @RepeatedTest(3)
    @DisplayName("Get Tasks By Status - Positive Scenario")
    void getTasksByStatus_shouldReturnTasksWhenStatusExists() {
        // Arrange
        Task task1 = createMockTask("Task 1", "Description 1", Taskstatus.PENDING, Taskpriority.HIGH, 1);
        Task task2 = createMockTask("Task 2", "Description 2", Taskstatus.PENDING, Taskpriority.LOW, 2);

        // Mock repository behavior
        when(taskRepository.findByStatus(Taskstatus.PENDING)).thenReturn(Arrays.asList(task1, task2));

        // Act
        List<Task> tasks = taskService.getTasksByStatus(Taskstatus.PENDING);

        // Assert
        assertNotNull(tasks, "Tasks list should not be null");
        assertEquals(2, tasks.size(), "Tasks list size should be 2");
        assertTrue(tasks.contains(task1), "Tasks list should contain task1");
        assertTrue(tasks.contains(task2), "Tasks list should contain task2");

        // Verify the interaction with the repository
        verify(taskRepository, times(1)).findByStatus(Taskstatus.PENDING);
    }

    @RepeatedTest(3)
    @DisplayName("Get Tasks By Status - No Tasks Found Scenario")
    void getTasksByStatus_shouldReturnEmptyListWhenNoTasksFound() {
        // Arrange
        when(taskRepository.findByStatus(Taskstatus.RUNNING)).thenReturn(Collections.emptyList());

        // Act
        List<Task> tasks = taskService.getTasksByStatus(Taskstatus.RUNNING);

        // Assert
        assertNotNull(tasks, "Tasks list should not be null");
        assertTrue(tasks.isEmpty(), "Tasks list should be empty");

        // Verify the interaction with the repository
        verify(taskRepository, times(1)).findByStatus(Taskstatus.RUNNING);
    }

    @RepeatedTest(3)
    @DisplayName("Get Tasks By Status - Negative Scenario (Exception Handling)")
    void getTasksByStatus_shouldThrowExceptionWhenRepositoryFails() {
        // Arrange
        when(taskRepository.findByStatus(Taskstatus.PENDING))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskService.getTasksByStatus(Taskstatus.PENDING);
        });

        assertEquals("Database error", exception.getMessage(), "Exception message should match the expected");

        // Verify the interaction with the repository
        verify(taskRepository, times(1)).findByStatus(Taskstatus.PENDING);
    }
}
