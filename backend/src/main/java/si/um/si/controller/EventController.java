package si.um.si.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.um.si.model.Event;
import si.um.si.model.Users;
import si.um.si.service.EventService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:5173")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Get all events (admin and user)
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents(); //ensure participants are fetched as wel
    }

    // Create a new event (Admin only)
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event, @RequestParam Long userId) {
        try {
            Event createdEvent = eventService.createEvent(event, userId);
            return ResponseEntity.ok(createdEvent);
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(null);
        }
    }

    // Update an event (Admin only)
    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long eventId,
            @RequestBody Event updatedEvent,
            @RequestParam Long userId) {
        try {
            Optional<Event> updated = eventService.updateEvent(eventId, updatedEvent, userId);
            return updated.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Delete an event (Admin only)
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId, @RequestParam Long userId) {
        try {
            eventService.deleteEvent(eventId, userId);
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build();
        }
    }

    // Apply to an event
    @PostMapping("/{eventId}/apply")
    public ResponseEntity<Event> applyToEvent(@PathVariable Long eventId, @RequestParam Long userId) {
        try {
            Event event = eventService.applyToEvent(eventId, userId);
            return ResponseEntity.ok(event);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // View participants of an event (Admin only)
    @GetMapping("/{eventId}/participants")
    public ResponseEntity<List<Users>> getParticipants(@PathVariable Long eventId, @RequestParam Long userId) {
        try {
            List<Users> participants = eventService.getParticipants(eventId, userId);
            return ResponseEntity.ok(participants);
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build();
        }
    }
    @GetMapping("/users/{userId}/events")
    public ResponseEntity<List<Event>> getUserAppliedEvents(@PathVariable Long userId) {
        try {
            List<Event> userEvents = eventService.getUserAppliedEvents(userId);
            return ResponseEntity.ok(userEvents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
