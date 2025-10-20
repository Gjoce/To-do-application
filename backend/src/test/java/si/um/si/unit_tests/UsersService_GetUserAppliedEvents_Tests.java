
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import si.um.si.model.Event;
import si.um.si.model.Users;
import si.um.si.repository.EvenRepository;
import si.um.si.repository.TaskRepository;
import si.um.si.repository.UserRepository;
import si.um.si.service.UsersService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersService_GetUserAppliedEvents_Tests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EvenRepository eventRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private UsersService usersService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("GetUserAppliedEvents – success (user exists and has events)")
    void getUserAppliedEvents_success() {
        // Arrange
        Users user = new Users();
        user.setId(1L);

        Event e1 = new Event();
        e1.setName("Workshop");
        Event e2 = new Event();
        e2.setName("Seminar");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findByParticipantsContaining(user)).thenReturn(List.of(e1, e2));

        // Act
        List<Event> result = usersService.getUserAppliedEvents(1L);

        // Assert
        assertEquals(2, result.size(), "User should have 2 events");
        assertEquals("Workshop", result.get(0).getName());
        verify(userRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).findByParticipantsContaining(user);
    }

    @Test
    @DisplayName("GetUserAppliedEvents – failure (user not found)")
    void getUserAppliedEvents_userNotFound() {
        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usersService.getUserAppliedEvents(99L)
        );

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(99L);
        verify(eventRepository, never()).findByParticipantsContaining(any());
    }
}
