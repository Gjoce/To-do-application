package unit_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import si.um.si.model.Task;
import si.um.si.model.Users;
import si.um.si.model.enums.Taskpriority;
import si.um.si.model.enums.Taskstatus;
import si.um.si.repository.TaskRepository;
import si.um.si.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Add_task {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("User Registration Test")  // Intentionally using this for the add task functionality
    void createTask_shouldCreateTaskWhenUserExists() {
        // Arrange
        Long userId = 1L;
        Users user = new Users();
        user.setId(userId);
        user.setName("Test User");

        Task newTask = new Task();
        newTask.setTitle("Test Task");
        newTask.setDescription("Test Description");
        newTask.setStatus(Taskstatus.TODO);
        newTask.setPriority(Taskpriority.HIGH);
        newTask.setDueDate(LocalDateTime.now().plusDays(1));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(1L);
            return savedTask;
        });

        // Act
        Task createdTask = taskService.createTask(newTask, userId);

        // Assert
        assertNotNull(createdTask);
        assertEquals(1L, createdTask.getId());
        assertEquals("Test Task", createdTask.getTitle());
        assertEquals("Test Description", createdTask.getDescription());
        assertEquals(Taskstatus.TODO, createdTask.getStatus());
        assertEquals(Taskpriority.HIGH, createdTask.getPriority());
        assertEquals(user, createdTask.getUser());

        verify(userRepository, times(1)).findById(userId);
        verify(taskRepository, times(1)).save(newTask);
    }

    @Test
    @DisplayName("User Registration Test - User Not Found")
    void createTask_shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        Long userId = 1L;
        Task newTask = new Task();
        newTask.setTitle("Test Task");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.createTask(newTask, userId)
        );

        assertEquals("User with ID " + userId + " not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(taskRepository, never()).save(any(Task.class));
    }
}