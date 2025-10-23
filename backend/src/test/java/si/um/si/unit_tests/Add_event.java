

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import si.um.si.model.Event;
import si.um.si.model.Users;
import si.um.si.model.enums.Role;
import si.um.si.repository.EvenRepository;
import si.um.si.repository.UserRepository;
import si.um.si.service.EventService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Add_event {

    @Mock
    private EvenRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EventService eventService;

    private Users adminUser;
    private Users regularUser;
    private Event mockEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        initializeTestData();
    }

    private void initializeTestData() {
        adminUser = createUser(1L, Role.ADMIN);
        regularUser = createUser(2L, Role.USER);
        mockEvent = createEvent(1L, "Test Event", "A test event");
    }

    private Users createUser(Long id, Role role) {
        Users user = new Users();
        user.setId(id);
        user.setRole(role);
        return user;
    }

    private Event createEvent(Long id, String name, String description) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        event.setDescription(description);
        return event;
    }

    @Test
    @Order(1)
    @DisplayName("Create Event - Admin User")
    void createEvent_shouldCreateEventWhenUserIsAdmin() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(adminUser));
        when(eventRepository.save(mockEvent)).thenReturn(mockEvent);

        // Act
        Event createdEvent = eventService.createEvent(mockEvent, 1L);

        // Assert
        assertNotNull(createdEvent, "The created event should not be null.");
        assertEquals(mockEvent.getName(), createdEvent.getName(), "The event name should match.");
        verify(userRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).save(mockEvent);
    }

    @Test
    @Order(2)
    @DisplayName("Create Event - Non-Admin User")
    void createEvent_shouldThrowExceptionWhenUserIsNotAdmin() {
        // Arrange
        when(userRepository.findById(2L)).thenReturn(Optional.of(regularUser));

        // Act & Assert
        SecurityException exception = assertThrows(SecurityException.class, () -> {
            eventService.createEvent(mockEvent, 2L);
        });

        assertEquals("Only admins can create events.", exception.getMessage(), "The exception message should match.");
        verify(userRepository, times(1)).findById(2L);
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    @Order(3)
    @DisplayName("Create Event - User Not Found")
    void createEvent_shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userRepository.findById(3L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.createEvent(mockEvent, 3L);
        });

        assertEquals("User not found.", exception.getMessage(), "The exception message should match.");
        verify(userRepository, times(1)).findById(3L);
        verify(eventRepository, never()).save(any(Event.class));
    }
}
