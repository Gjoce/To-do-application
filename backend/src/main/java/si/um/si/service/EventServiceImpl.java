package si.um.si.service;

import org.springframework.stereotype.Service;
import si.um.si.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final List<Event> events = new ArrayList<>(); // Temporary in-memory storage for events

    @Override
    public List<Event> getAllEvents() {
        return events;
    }

    @Override
    public List<Event> getEventsByType(String type) {
        // Simulate filtering by type
        return events.stream()
                .filter(event -> event.getDescription() != null && event.getDescription().contains(type))
                .toList();
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return events.stream().filter(event -> event.getId().equals(id)).findFirst();
    }

    @Override
    public Event createEvent(Event event) {
        events.add(event);
        return event;
    }

    @Override
    public Optional<Event> updateEvent(Long id, Event updatedEvent) {
        return getEventById(id).map(existingEvent -> {
            existingEvent.setName(updatedEvent.getName());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setStartTime(updatedEvent.getStartTime());
            existingEvent.setEndTime(updatedEvent.getEndTime());
            existingEvent.setLocation(updatedEvent.getLocation());
            existingEvent.setMaxParticipants(updatedEvent.getMaxParticipants());
            return existingEvent;
        });
    }

    @Override
    public void deleteEvent(Long id) {
        events.removeIf(event -> event.getId().equals(id));
    }
}
