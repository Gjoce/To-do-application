package si.um.si.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.um.si.model.Event;
import si.um.si.model.Users;
import si.um.si.model.enums.Role;
import si.um.si.repository.EvenRepository;
import si.um.si.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EvenRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventService(EvenRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get event by ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // Create a new event (Admin only)
    public Event createEvent(Event event, Long userId) {
        Users user = getUserById(userId);

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new SecurityException("Only admins can create events.");
        }

        event.setCreatedBy(user); // Set the user as the event creator
        return eventRepository.save(event);
    }

    // Update an event (Admin only)
    public Optional<Event> updateEvent(Long eventId, Event updatedEvent, Long userId) {
        Users user = getUserById(userId);

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new SecurityException("Only admins can update events.");
        }

        return eventRepository.findById(eventId)
                .map(existingEvent -> {
                    existingEvent.setName(updatedEvent.getName());
                    existingEvent.setDescription(updatedEvent.getDescription());
                    existingEvent.setStartTime(updatedEvent.getStartTime());
                    existingEvent.setEndTime(updatedEvent.getEndTime());
                    existingEvent.setLocation(updatedEvent.getLocation());
                    existingEvent.setMaxParticipants(updatedEvent.getMaxParticipants());
                    return eventRepository.save(existingEvent);
                });
    }

    // Delete an event (Admin only)
    public void deleteEvent(Long eventId, Long userId) {
        Users user = getUserById(userId);

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new SecurityException("Only admins can delete events.");
        }

        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new IllegalArgumentException("Event not found.");
        }

        eventRepository.delete(eventOptional.get());
    }

    // Get events created by a user
    public List<Event> getEventsCreatedByUser(Long userId) {
        return eventRepository.findByUserId(userId);
    }

    // Get events a user is participating in
    public List<Event> getEventsUserIsParticipatingIn(Long userId) {
        return eventRepository.findByParticipantsId(userId);
    }

    // Helper method to get user by ID
    private Users getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }
}
