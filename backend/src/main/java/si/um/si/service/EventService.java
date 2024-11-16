package si.um.si.service;

import si.um.si.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> getAllEvents();
    List<Event> getEventsByType(String type); // Filter events by type (optional)
    Optional<Event> getEventById(Long id);
    Event createEvent(Event event);
    Optional<Event> updateEvent(Long id, Event updatedEvent);
    void deleteEvent(Long id);
}
