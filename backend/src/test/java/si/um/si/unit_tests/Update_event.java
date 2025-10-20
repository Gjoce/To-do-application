import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import si.um.si.model.Event;
import si.um.si.model.Users;
import si.um.si.model.enums.Role;
import si.um.si.repository.EvenRepository;
import si.um.si.repository.UserRepository;
import si.um.si.service.EventService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class Update_event {

    @ParameterizedTest
    @CsvSource({
            "Updated Event, New Description",
            "Another Event, Another Description"
    })
    void testUpdateEventSuccess(String updatedName, String updatedDescription) {
        // Mock repositories
        EvenRepository mockEventRepository = mock(EvenRepository.class);
        UserRepository mockUserRepository = mock(UserRepository.class);

        // Mock user and event
        Users mockAdmin = mock(Users.class);
        Event mockEvent = mock(Event.class);

        // Mock behavior
        when(mockAdmin.getRole()).thenReturn(Role.ADMIN);
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(mockAdmin));
        when(mockEventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));
        when(mockEventRepository.save(any(Event.class))).thenReturn(mockEvent);

        // Create service
        EventService eventService = new EventService(mockEventRepository, mockUserRepository);

        // Create updated event
        Event updatedEvent = new Event();
        updatedEvent.setName(updatedName);
        updatedEvent.setDescription(updatedDescription);

        // Execute method
        Optional<Event> result = eventService.updateEvent(1L, updatedEvent, 1L);

        // Verify
        assertTrue(result.isPresent());
        assertEquals(mockEvent, result.get());
        verify(mockEventRepository, times(1)).save(mockEvent);
    }

    @Test
    void testUpdateEventFailure_UserNotAdmin() {
        // Mock repositories
        EvenRepository mockEventRepository = mock(EvenRepository.class);
        UserRepository mockUserRepository = mock(UserRepository.class);

        // Mock user and event
        Users mockUser = mock(Users.class);
        Event mockEvent = mock(Event.class);

        // Mock behavior for non-admin user
        when(mockUser.getRole()).thenReturn(Role.USER);
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(mockEventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));

        // Create service
        EventService eventService = new EventService(mockEventRepository, mockUserRepository);

        // Create updated event
        Event updatedEvent = new Event();
        updatedEvent.setName("Unauthorized Update");
        updatedEvent.setDescription("This should fail");

        // Execute and verify exception
        SecurityException exception = assertThrows(SecurityException.class, () -> {
            eventService.updateEvent(1L, updatedEvent, 1L);
        });

        assertEquals("Only admins can update events.", exception.getMessage());
        verify(mockEventRepository, times(0)).save(any(Event.class)); // Ensure save is never called
    }

    @Test
    void testUpdateEventFailure_EventNotFound() {
        // Mock repositories
        EvenRepository mockEventRepository = mock(EvenRepository.class);
        UserRepository mockUserRepository = mock(UserRepository.class);

        // Mock admin user
        Users mockAdmin = mock(Users.class);

        // Mock behavior
        when(mockAdmin.getRole()).thenReturn(Role.ADMIN);
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(mockAdmin));
        when(mockEventRepository.findById(1L)).thenReturn(Optional.empty());

        // Create service
        EventService eventService = new EventService(mockEventRepository, mockUserRepository);

        // Create updated event
        Event updatedEvent = new Event();
        updatedEvent.setName("Nonexistent Event");
        updatedEvent.setDescription("This should fail");

        // Execute and verify result
        Optional<Event> result = eventService.updateEvent(1L, updatedEvent, 1L);

        assertFalse(result.isPresent());
        verify(mockEventRepository, times(0)).save(any(Event.class)); // Ensure save is never called
    }
}

