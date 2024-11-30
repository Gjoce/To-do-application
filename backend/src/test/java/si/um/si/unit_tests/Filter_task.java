package si.um.si.unit_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Filter_task {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @RepeatedTest(3)
    @DisplayName("Get Tasks By Status - Positive Scenario")
    void getTasksByStatus_shouldReturnTasksWhenStatusExists() {
        // Arrange
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        task1.setStatus(Taskstatus.PENDING);
        task1.setPriority(Taskpriority.HIGH);
        task1.setDueDate(LocalDateTime.now().plusDays(1));

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        task2.setStatus(Taskstatus.PENDING);
        task2.setPriority(Taskpriority.LOW);
        task2.setDueDate(LocalDateTime.now().plusDays(2));

        // Mock repository behavior
        when(taskRepository.findByStatus(Taskstatus.PENDING)).thenReturn(Arrays.asList(task1, task2));

        // Act
        List<Task> tasks = taskService.getTasksByStatus(Taskstatus.PENDING);

        // Assert
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));

        // Verify the interaction with the repository
        verify(taskRepository, times(1)).findByStatus(Taskstatus.PENDING);
    }

    @RepeatedTest(3)
    @DisplayName("Get Tasks By Status - No Tasks Found Scenario")
    void getTasksByStatus_shouldReturnEmptyListWhenNoTasksFound() {
        // Arrange
        when(taskRepository.findByStatus(Taskstatus.RUNNING)).thenReturn(Arrays.asList());

        // Act
        List<Task> tasks = taskService.getTasksByStatus(Taskstatus.RUNNING);

        // Assert
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());

        // Verify the interaction with the repository
        verify(taskRepository, times(1)).findByStatus(Taskstatus.RUNNING);
    }
}
