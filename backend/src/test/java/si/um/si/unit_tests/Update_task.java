

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import si.um.si.model.Users;
import si.um.si.model.Task;
import si.um.si.model.Event;
import si.um.si.repository.UserRepository;
import si.um.si.repository.TaskRepository;
import si.um.si.repository.EvenRepository;
import si.um.si.service.UsersService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

public class Update_task {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private EvenRepository eventRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // **Positive Scenario: User Registration - Success**
    @Test
    void testRegisterUser_Success() {
        String email = "newuser@example.com";
        String username = "newUser";
        String password = "password123";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        // Dynamically return a Users object with the properties from the input
        when(userRepository.save(any(Users.class))).thenAnswer(invocation -> {
            Users user = invocation.getArgument(0);
            user.setPassword("encodedPassword"); // Simulate the saved encoded password
            return user;
        });

        Users user = usersService.registerUser(username, email, password);

        assertNotNull(user);
        assertEquals("encodedPassword", user.getPassword());
    }


    // **Negative Scenario: User Registration - Email Already in Use**
    @Test
    void testRegisterUser_EmailAlreadyInUse() {
        String email = "test@example.com";
        String username = "testUser";
        String password = "password123";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new Users()));

        assertThrows(IllegalArgumentException.class, () -> {
            usersService.registerUser(username, email, password);
        });
    }

    // **Positive Scenario: User Login - Success**
    @Test
    void testLoginUser_Success() {
        String email = "test@example.com";
        String password = "password123";
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        Optional<Users> result = usersService.loginUser(email, password);
        assertTrue(result.isPresent());
    }

    // **Negative Scenario: User Login - User Not Found**
    @Test
    void testLoginUser_UserNotFound() {
        String email = "test@example.com";
        String password = "password123";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Users> result = usersService.loginUser(email, password);
        assertFalse(result.isPresent());
    }

    // **Positive Scenario: Get User Tasks - Success**
    @Test
    void testGetUserTasks_Success() {
        Long userId = 1L;
        Users user = new Users();
        Task task = new Task();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.findByUserId(userId)).thenReturn(List.of(task));

        List<Task> tasks = usersService.getUserTasks(userId);
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
    }

    // **Negative Scenario: Get User Tasks - User Not Found**
    @Test
    void testGetUserTasks_UserNotFound() {
        Long invalidUserId = 999L;

        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            usersService.getUserTasks(invalidUserId);
        });
    }

    // **Positive Scenario: Apply to Event - Success**
    @Test
    void testApplyToEvent_Success() {
        Long userId = 1L;
        Long eventId = 1L;
        Users user = new Users();
        Event event = new Event();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        event.setParticipants(List.of(user));
        when(eventRepository.save(event)).thenReturn(event);

        Event updatedEvent = usersService.applyToEvent(userId, eventId);
        assertNotNull(updatedEvent);
        assertTrue(updatedEvent.getParticipants().contains(user));
    }

    // **Negative Scenario: Apply to Event - User Not Found**
    @Test
    void testApplyToEvent_UserNotFound() {
        Long invalidUserId = 999L;
        Long eventId = 1L;

        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            usersService.applyToEvent(invalidUserId, eventId);
        });
    }

    // **Negative Scenario: Apply to Event - Event Not Found**
    @Test
    void testApplyToEvent_EventNotFound() {
        Long userId = 1L;
        Long invalidEventId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(new Users()));
        when(eventRepository.findById(invalidEventId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            usersService.applyToEvent(userId, invalidEventId);
        });
    }

    // **Negative Scenario: Apply to Event - User Already Applied**
    @Test
    void testApplyToEvent_UserAlreadyApplied() {
        Long userId = 1L;
        Long eventId = 1L;
        Users user = new Users();
        Event event = new Event();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // Mock that the user is already a participant
        event.setParticipants(List.of(user));
        when(eventRepository.save(event)).thenReturn(event); // Ensure save is not called if user is already in the event

        // Test if user is not added again and the save method is not invoked
        Event updatedEvent = usersService.applyToEvent(userId, eventId);
        assertNotNull(updatedEvent);
        assertTrue(updatedEvent.getParticipants().contains(user));  // User should already be present
        verify(eventRepository, never()).save(event); // Ensure save is not called again
    }

    // **Negative Scenario: Apply to Event - Invalid User & Event ID**
    @Test
    void testApplyToEvent_InvalidUserAndEvent() {
        Long invalidUserId = 100L;
        Long invalidEventId = 999L;

        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());
        when(eventRepository.findById(invalidEventId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            usersService.applyToEvent(invalidUserId, invalidEventId);
        });
    }
}
