import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import si.um.si.model.Event;
import si.um.si.model.Users;
import si.um.si.model.enums.Role;
import si.um.si.repository.EvenRepository;
import si.um.si.repository.UserRepository;
import si.um.si.service.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventService_GetParticipants_Tests {

    @Mock
    private EvenRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EventService eventService;

    private Users adminUser;
    private Users regularUser;
    private Event event;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Set up admin and regular user
        adminUser = new Users();
        adminUser.setId(1L);
        adminUser.setRole(Role.ADMIN);

        regularUser = new Users();
        regularUser.setId(2L);
        regularUser.setRole(Role.USER);

        // Set up event with participants
        event = new Event();
        event.setId(10L);
        event.setName("Tech Conference");
        List<Users> participants = new ArrayList<>();
        participants.add(regularUser);
        event.setParticipants(participants);
    }

    // ✅ Positive Scenario
    @Test
    @DisplayName("GetParticipants – Admin can view event participants")
    void getParticipants_admin_ok() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(adminUser));
        when(eventRepository.findById(10L)).thenReturn(Optional.of(event));

        List<Users> result = eventService.getParticipants(10L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(regularUser, result.get(0));
        verify(userRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).findById(10L);
    }

    // ❌ Negative Scenario 1: Non-admin user tries to view participants
    @Test
    @DisplayName("GetParticipants – Non-admin user cannot view participants")
    void getParticipants_nonAdmin_forbidden() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(regularUser));

        SecurityException exception = assertThrows(SecurityException.class, () -> {
            eventService.getParticipants(10L, 2L);
        });

        assertEquals("Only admins can view participants.", exception.getMessage());
        verify(userRepository, times(1)).findById(2L);
        verify(eventRepository, never()).findById(anyLong());
    }

    // ❌ Negative Scenario 2: Event not found
    @Test
    @DisplayName("GetParticipants – Event not found throws exception")
    void getParticipants_eventNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(adminUser));
        when(eventRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.getParticipants(99L, 1L);
        });

        assertEquals("Event not found.", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).findById(99L);
    }
}
