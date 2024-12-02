package si.um.si.unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import si.um.si.model.Event;
import si.um.si.service.EventService;
import si.um.si.repository.EvenRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class View_my_events_admin {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testViewAllEventsAdminSuccess() {
        // Mock dependencies
        EvenRepository mockEventRepository = mock(EvenRepository.class);

        // Create mock events
        Event mockEvent1 = mock(Event.class);
        when(mockEvent1.getName()).thenReturn("Event1");

        Event mockEvent2 = mock(Event.class);
        when(mockEvent2.getName()).thenReturn("Event2");

        Event mockEvent3 = mock(Event.class);
        when(mockEvent3.getName()).thenReturn("Event3");

        // Mock repository behavior
        when(mockEventRepository.findAll()).thenReturn(List.of(mockEvent1, mockEvent2, mockEvent3));

        // Create service with mock repository
        EventService eventService = new EventService(mockEventRepository, null);

        // Execute method under test
        List<Event> events = eventService.getAllEvents();

        // Verify results
        assertNotNull(events);
        assertEquals(3, events.size());
        assertEquals("Event1", events.get(0).getName());
        verify(mockEventRepository, times(1)).findAll(); // Verify repository interaction
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testViewAllEventsAdminNoEvents() {
        // Mock dependencies
        EvenRepository mockEventRepository = mock(EvenRepository.class);

        // Mock repository behavior for no events
        when(mockEventRepository.findAll()).thenReturn(List.of());

        // Create service with mock repository
        EventService eventService = new EventService(mockEventRepository, null);

        // Execute method under test
        List<Event> events = eventService.getAllEvents();

        // Verify results
        assertNotNull(events);
        assertTrue(events.isEmpty());
        verify(mockEventRepository, times(1)).findAll(); // Verify repository interaction
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testViewAllEventsAdminError() {
        // Mock dependencies
        EvenRepository mockEventRepository = mock(EvenRepository.class);

        // Simulate an exception in the repository
        when(mockEventRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Create service with mock repository
        EventService eventService = new EventService(mockEventRepository, null);

        // Execute and verify exception
        RuntimeException exception = assertThrows(RuntimeException.class, eventService::getAllEvents);
        assertEquals("Database error", exception.getMessage());
        verify(mockEventRepository, times(1)).findAll(); // Verify repository interaction
    }

}
