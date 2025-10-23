

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

class Delete_event {

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
        mockEvent.setName("Sample Event");
    }

    @AfterEach
    void tearDown() {
        // Reset the mocks after each test
        reset(eventRepository, userRepository);
    }

    @Test
    @DisplayName("Delete Event - Admin User")
    void deleteEvent_shouldDeleteEventWhenUserIsAdmin() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(adminUser));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));

        // Act
        eventService.deleteEvent(1L, 1L);

        // Assert
        verify(userRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).delete(mockEvent);
    }

    @Test
    @DisplayName("Delete Event - Non-Admin User")
    void deleteEvent_shouldThrowExceptionWhenUserIsNotAdmin() {
        // Arrange
        when(userRepository.findById(2L)).thenReturn(Optional.of(regularUser));

        // Act & Assert
        SecurityException exception = assertThrows(SecurityException.class, () -> {
            eventService.deleteEvent(1L, 2L);
        });

        assertEquals("Only admins can delete events.", exception.getMessage());
        verify(userRepository, times(1)).findById(2L);
        verify(eventRepository, never()).findById(anyLong());
        verify(eventRepository, never()).delete(any(Event.class));
    }

    @Test
    @DisplayName("Delete Event - Non-Existent Event")
    void deleteEvent_shouldThrowExceptionWhenEventDoesNotExist() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(adminUser));
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.deleteEvent(1L, 1L);
        });

        assertEquals("Event not found.", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, never()).delete(any(Event.class));
    }
}
