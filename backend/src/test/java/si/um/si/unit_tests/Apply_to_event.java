import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import si.um.si.model.Event;
import si.um.si.model.Users;
import si.um.si.repository.EvenRepository;
import si.um.si.repository.UserRepository;
import si.um.si.service.EventService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class Apply_to_event {

    @Test
    @Tag("filter")
    void testApplyToEventSuccess() {
        // Mock dependencies
        EvenRepository mockEventRepository = mock(EvenRepository.class);
        UserRepository mockUserRepository = mock(UserRepository.class);

        // Mock user and event
        Users mockUser = new Users();
        mockUser.setId(1L);

        Event mockEvent = new Event();
        mockEvent.setId(1L);
        mockEvent.setMaxParticipants(10);
        mockEvent.setParticipants(new ArrayList<>()); // Use mutable ArrayList

        // Mock repository behavior
        when(mockUserRepository.findById(1L)).thenReturn(java.util.Optional.of(mockUser));
        when(mockEventRepository.findById(1L)).thenReturn(java.util.Optional.of(mockEvent));
        when(mockEventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Create service
        EventService eventService = new EventService(mockEventRepository, mockUserRepository);

        // Execute method
        Event updatedEvent = eventService.applyToEvent(1L, 1L);

        // Verify
        assertNotNull(updatedEvent);
        assertEquals(1, updatedEvent.getParticipants().size());
        assertTrue(updatedEvent.getParticipants().contains(mockUser));
        verify(mockEventRepository, times(1)).save(mockEvent);
    }

    @Test
    @Tag("filter")
    void testApplyToEventFullyBooked() {
        // Mock dependencies
        EvenRepository mockEventRepository = mock(EvenRepository.class);
        UserRepository mockUserRepository = mock(UserRepository.class);

        // Mock user and event
        Users mockUser = new Users();
        mockUser.setId(1L);

        Event mockEvent = new Event();
        mockEvent.setId(1L);
        mockEvent.setMaxParticipants(2);
        List<Users> participants = new ArrayList<>();
        participants.add(mockUser);
        participants.add(mockUser); // Already full
        mockEvent.setParticipants(participants);

        // Mock repository behavior
        when(mockUserRepository.findById(1L)).thenReturn(java.util.Optional.of(mockUser));
        when(mockEventRepository.findById(1L)).thenReturn(java.util.Optional.of(mockEvent));

        // Create service
        EventService eventService = new EventService(mockEventRepository, mockUserRepository);

        // Execute and verify exception
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> eventService.applyToEvent(1L, 1L));
        assertEquals("Event is fully booked.", exception.getMessage());
    }
};
