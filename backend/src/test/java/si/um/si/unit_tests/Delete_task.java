package si.um.si.unit_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Delete_task {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Delete Task - Task Exists (Positive Scenario)")
    void deleteTask_shouldDeleteTaskWhenTaskExists() {
        // Arrange
        Long taskId = 3L;
        Task newTask = new Task();
        newTask.setTitle("Test Task");
        newTask.setDescription("Test Description");
        newTask.setStatus(Taskstatus.PENDING);
        newTask.setPriority(Taskpriority.HIGH);
        newTask.setDueDate(LocalDateTime.now().plusDays(1));

        // Mock repository behavior
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(newTask));
        when(taskRepository.existsById(taskId)).thenReturn(true);

        // Act
        taskService.deleteTask(taskId);

        // Assert
        verify(taskRepository, times(1)).deleteById(taskId);
    }


    @Test
    @DisplayName("Delete Task - Task Does Not Exist (Negative Scenario)")
    void deleteTask_shouldThrowExceptionWhenTaskDoesNotExist() {
        // Arrange
        Long taskId = 4L;

        // Mock repository behavior to return empty for the given task ID
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.deleteTask(taskId)
        );

        assertEquals("Task with ID " + taskId + " not found", exception.getMessage());
        verify(taskRepository, times(0)).deleteById(taskId);
    }
}
