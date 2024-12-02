package si.um.si.unit_tests;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import si.um.si.model.Event;
import si.um.si.repository.EvenRepository;
import si.um.si.service.EventService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class View_events_user {
    @Nested
    class ViewEvent {
        @Test
        void testViewEventSuccess() {
            // Mock repository
            EvenRepository mockEventRepository = mock(EvenRepository.class);

            // Mock event
            Event mockEvent = mock(Event.class);
            when(mockEvent.getName()).thenReturn("Test Event");

            // Mock repository behavior
            when(mockEventRepository.findAll()).thenReturn(List.of(mockEvent));

            // Create service
            EventService eventService = new EventService(mockEventRepository, null);

            // Execute method
            List<Event> events = eventService.getAllEvents();

            // Verify
            assertNotNull(events);
            assertEquals(1, events.size());
            assertEquals("Test Event", events.get(0).getName());
        }

        @Test
        void testViewEventFailure() {
            // Mock repository
            EvenRepository mockEventRepository = mock(EvenRepository.class);

            // Mock repository behavior to throw an exception
            when(mockEventRepository.findAll()).thenThrow(new RuntimeException("Test Exception"));

            // Create service
            EventService eventService = new EventService(mockEventRepository, null);

            // Verify that an exception is thrown
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                eventService.getAllEvents();
            });

            // Assert exception message
            assertEquals("Test Exception", exception.getMessage());
        }
    }
}
