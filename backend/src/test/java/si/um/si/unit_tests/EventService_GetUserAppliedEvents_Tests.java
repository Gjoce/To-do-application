
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import si.um.si.model.Event;
import si.um.si.model.Users;
import si.um.si.repository.EvenRepository;
import si.um.si.repository.UserRepository;
import si.um.si.service.EventService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventService_GetUserAppliedEvents_Tests {

    @Mock
    private EvenRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EventService eventService;

    private Users user;
    private Event event1;
    private Event event2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Prepare user and events
        user = new Users();
        user.setId(1L);
        user.setUsername("Ana");
        user.setEmail("ana@example.com");

        event1 = new Event();
        event1.setId(10L);
        event1.setName("Workshop 1");

        event2 = new Event();
        event2.setId(11L);
        event2.setName("Workshop 2");
    }

    // ✅ Positive Scenario
    @Test
    @DisplayName("GetUserAppliedEvents – User exists and has events")
    void getUserAppliedEvents_ok() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findByParticipantsContaining(user)).thenReturn(List.of(event1, event2));

        List<Event> events = eventService.getUserAppliedEvents(1L);

        assertNotNull(events);
        assertEquals(2, events.size());
        assertEquals("Workshop 1", events.get(0).getName());
        assertEquals("Workshop 2", events.get(1).getName());
        verify(userRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).findByParticipantsContaining(user);
    }

    // ❌ Negative Scenario 1: User not found
    @Test
    @DisplayName("GetUserAppliedEvents – User not found throws exception")
    void getUserAppliedEvents_userMissing() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.getUserAppliedEvents(99L);
        });

        assertEquals("User not found.", exception.getMessage());
        verify(userRepository, times(1)).findById(99L);
        verify(eventRepository, never()).findByParticipantsContaining(any());
    }

    // ❌ Negative Scenario 2: User exists but has no events
    @Test
    @DisplayName("GetUserAppliedEvents – User exists but no events found")
    void getUserAppliedEvents_noEvents() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findByParticipantsContaining(user)).thenReturn(List.of());

        List<Event> events = eventService.getUserAppliedEvents(1L);

        assertNotNull(events);
        assertTrue(events.isEmpty());
        verify(userRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).findByParticipantsContaining(user);
    }
}
