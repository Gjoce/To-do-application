
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import si.um.si.model.Task;
import si.um.si.model.Users;
import si.um.si.repository.TaskRepository;
import si.um.si.repository.UserRepository;
import si.um.si.service.TaskService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    // getTaskById() Tests
    @Test
    @DisplayName("getTaskById - Positive: returns task when found")
    void testGetTaskById_Positive() {
        Task task = new Task();
        task.setId(1L);
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("getTaskById - Negative: returns empty when not found")
    void testGetTaskById_Negative() {
        Mockito.when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("getTaskById - Edge: ID is 0, should return empty")
    void testGetTaskById_Edge() {
        Mockito.when(taskRepository.findById(0L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(0L);
        assertFalse(result.isPresent());
    }

    // createTask() Tests
    @Test
    @DisplayName("createTask - Positive: valid user and task")
    void testCreateTask_Positive() {
        Users user = new Users();
        user.setId(1L);
        user.setUsername("testUser");

        Task task = new Task();
        task.setTitle("New Task");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task, 1L);
        assertNotNull(result);
        assertEquals("testUser", result.getUser().getUsername());
    }

    @Test
    @DisplayName("createTask - Negative: null task throws exception")
    void testCreateTask_NullTask() {
        Users user = new Users();
        user.setId(1L);
        user.setUsername("testUser");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(null, 1L));
    }

    @Test
    @DisplayName("createTask - Negative: user not found")
    void testCreateTask_UserNotFound() {
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());
        Task task = new Task();

        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(task, 2L));
    }

    @Test
    @DisplayName("createTask - Edge: user with empty username")
    void testCreateTask_EmptyUsername() {
        Users user = new Users();
        user.setId(1L);
        user.setUsername("");

        Task task = new Task();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(task, 1L));
    }

    //updateTask() Tests
    @Test
    @DisplayName("updateTask - Positive: updates existing task successfully")
    void testUpdateTask_Positive() {
        Task existing = new Task();
        existing.setId(1L);
        existing.setTitle("Old");

        Task updated = new Task();
        updated.setTitle("New");

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Task> result = taskService.updateTask(1L, updated);
        assertTrue(result.isPresent());
        assertEquals("New", result.get().getTitle());
    }

    @Test
    @DisplayName("updateTask - Negative: returns empty when task not found")
    void testUpdateTask_Negative() {
        Mockito.when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.updateTask(2L, new Task());
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("updateTask - Edge: updates with empty fields")
    void testUpdateTask_Edge() {
        Task existing = new Task();
        existing.setId(1L);

        Task emptyUpdate = new Task();

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Task> result = taskService.updateTask(1L, emptyUpdate);
        assertTrue(result.isPresent());
        assertNull(result.get().getTitle());
    }

    // deleteTask() Tests
    @Test
    @DisplayName("deleteTask - Positive: deletes when exists")
    void testDeleteTask_Positive() {
        Mockito.when(taskRepository.existsById(1L)).thenReturn(true);
        taskService.deleteTask(1L);
        Mockito.verify(taskRepository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteTask - Negative: throws when not found")
    void testDeleteTask_Negative() {
        Mockito.when(taskRepository.existsById(2L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(2L));
    }

    @Test
    @DisplayName("deleteTask - Edge: null ID throws IllegalArgumentException")
    void testDeleteTask_Edge_NullId() {
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(null));
    }



    // updateFavoriteStatus() Tests
    @Test
    @DisplayName("updateFavoriteStatus - Positive: updates favorite to true")
    void testUpdateFavoriteStatus_Positive() {
        Task task = new Task();
        task.setId(1L);
        task.setFavorite(false);

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Task> result = taskService.updateFavoriteStatus(1L, true);
        assertTrue(result.isPresent());
        assertTrue(result.get().isFavorite());
    }

    @Test
    @DisplayName("updateFavoriteStatus - Negative: task not found")
    void testUpdateFavoriteStatus_Negative() {
        Mockito.when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Task> result = taskService.updateFavoriteStatus(99L, true);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("updateFavoriteStatus - Edge: toggles from true to false")
    void testUpdateFavoriteStatus_Edge() {
        Task task = new Task();
        task.setId(1L);
        task.setFavorite(true);

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Task> result = taskService.updateFavoriteStatus(1L, false);
        assertTrue(result.isPresent());
        assertFalse(result.get().isFavorite());
    }
}
