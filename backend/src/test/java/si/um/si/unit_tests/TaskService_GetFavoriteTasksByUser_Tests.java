
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

class TaskService_GetFavoriteTasksByUser_Tests {

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
    @DisplayName("GetFavoriteTasksByUser – success (returns list of favorite tasks)")
    void getFavoriteTasks_success() {
        // Arrange
        Long userId = 10L;
        Task task1 = new Task();
        task1.setId(1L);
        task1.setFavorite(true);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setFavorite(true);

        when(taskRepository.findByFavoriteAndUserId(true, userId)).thenReturn(Arrays.asList(task1, task2));

        // Act
        List<Task> result = taskService.getFavoriteTasksByUser(userId);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "There should be 2 favorite tasks");
        assertTrue(result.stream().allMatch(Task::isFavorite), "All tasks should be marked as favorite");

        verify(taskRepository, times(1)).findByFavoriteAndUserId(true, userId);
    }

    @Test
    @DisplayName("GetFavoriteTasksByUser – no favorite tasks found")
    void getFavoriteTasks_emptyList() {
        // Arrange
        Long userId = 20L;
        when(taskRepository.findByFavoriteAndUserId(true, userId)).thenReturn(Collections.emptyList());

        // Act
        List<Task> result = taskService.getFavoriteTasksByUser(userId);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result list should be empty when no favorites found");

        verify(taskRepository, times(1)).findByFavoriteAndUserId(true, userId);
    }

    @Test
    @DisplayName("GetFavoriteTasksByUser – repository error")
    void getFavoriteTasks_repositoryError() {
        // Arrange
        Long userId = 30L;
        when(taskRepository.findByFavoriteAndUserId(true, userId))
                .thenThrow(new RuntimeException("Database connection error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                taskService.getFavoriteTasksByUser(userId)
        );

        assertEquals("Database connection error", exception.getMessage());
        verify(taskRepository, times(1)).findByFavoriteAndUserId(true, userId);
    }
}
