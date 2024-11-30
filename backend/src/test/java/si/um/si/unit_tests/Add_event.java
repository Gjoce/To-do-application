package si.um.si.unit_tests;

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

        // Initialize test data
        adminUser = new Users();
        adminUser.setId(1L);
        adminUser.setRole(Role.ADMIN);

        regularUser = new Users();
        regularUser.setId(2L);
        regularUser.setRole(Role.USER);

        mockEvent = new Event();
        mockEvent.setId(1L);
        mockEvent.setName("Test Event");
        mockEvent.setDescription("A test event");
    }

    @Test
    @Order(1)
    @DisplayName("Create Event - Admin User")
    void createEvent_shouldCreateEventWhenUserIsAdmin() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(adminUser));
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
        when(userRepository.findById(2L)).thenReturn(java.util.Optional.of(regularUser));

        // Act & Assert
        SecurityException exception = assertThrows(SecurityException.class, () -> {
            eventService.createEvent(mockEvent, 2L);
        });

        assertEquals("Only admins can create events.", exception.getMessage(), "The exception message should match.");
        verify(userRepository, times(1)).findById(2L);
        verify(eventRepository, never()).save(any(Event.class));
    }
}
