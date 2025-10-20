
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskService_UpdateFavoriteStatus_Tests {

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

    @Test
    @DisplayName("UpdateFavoriteStatus – success (task exists, status updated to true)")
    void updateFavoriteStatus_successTrue() {
        // Arrange
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setFavorite(false);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Optional<Task> result = taskService.updateFavoriteStatus(taskId, true);

        // Assert
        assertTrue(result.isPresent(), "Task should be found");
        assertTrue(result.get().isFavorite(), "Favorite status should be true");
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    @DisplayName("UpdateFavoriteStatus – success (task exists, status updated to false)")
    void updateFavoriteStatus_successFalse() {
        // Arrange
        Long taskId = 2L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setFavorite(true);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Optional<Task> result = taskService.updateFavoriteStatus(taskId, false);

        // Assert
        assertTrue(result.isPresent(), "Task should be found");
        assertFalse(result.get().isFavorite(), "Favorite status should be false");
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    @DisplayName("UpdateFavoriteStatus – failure (task not found)")
    void updateFavoriteStatus_taskNotFound() {
        // Arrange
        Long invalidId = 99L;
        when(taskRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act
        Optional<Task> result = taskService.updateFavoriteStatus(invalidId, true);

        // Assert
        assertTrue(result.isEmpty(), "If task not found, should return empty Optional");
        verify(taskRepository, times(1)).findById(invalidId);
        verify(taskRepository, never()).save(any());
    }
}
