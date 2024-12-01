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

    // Apply to an event
    public Event applyToEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found."));
        Users user = getUserById(userId);

        if (event.getParticipants().size() >= event.getMaxParticipants()) {
            throw new IllegalStateException("Event is fully booked.");
        }

        event.getParticipants().add(user); // Add the user to the participants
        return eventRepository.save(event);
    }

    // Create a new event (Admin only)
    public Event createEvent(Event event, Long userId) {
        Users user = getUserById(userId);

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new SecurityException("Only admins can create events.");
        }

        event.setCreatedBy(user);
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

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found."));

        eventRepository.delete(event);
    }

    // Get participants of an event (Admin only)
    public List<Users> getParticipants(Long eventId, Long userId) {
        Users user = getUserById(userId);

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new SecurityException("Only admins can view participants.");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found."));

        return event.getParticipants();
    }

    // Helper method to get user by ID
    private Users getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    public List<Event> getUserAppliedEvents(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        return eventRepository.findByParticipantsContaining(user); // Assuming a ManyToMany relationship
    }

}
