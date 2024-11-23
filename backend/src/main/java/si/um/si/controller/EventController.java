package si.um.si.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.um.si.model.Event;
import si.um.si.service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Get all events
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // Get event by ID
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        Optional<Event> event = eventService.getEventById(eventId);
        return event.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new event (Admin only)
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event, @RequestParam Long userId) {
        try {
            Event createdEvent = eventService.createEvent(event, userId);
            return ResponseEntity.ok(createdEvent);
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(null); // Forbidden
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Update an event
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
            return ResponseEntity.status(403).body(null); // Forbidden
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Delete an event (Admin only)
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId, @RequestParam Long userId) {
        try {
            eventService.deleteEvent(eventId, userId);
            return ResponseEntity.noContent().build(); // Success, no content
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build(); // Forbidden
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get events by start time
    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> getUpcomingEvents(@RequestParam String startTime) {
        LocalDateTime parsedTime = LocalDateTime.parse(startTime);
        return ResponseEntity.ok(eventService.getEventsByStartTimeAfter(parsedTime));
    }

    // Get events created by a specific user
    @GetMapping("/created-by/{userId}")
    public ResponseEntity<List<Event>> getEventsCreatedByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(eventService.getEventsCreatedByUser(userId));
    }

    // Get events a user is participating in
    @GetMapping("/participating/{userId}")
    public ResponseEntity<List<Event>> getEventsUserIsParticipatingIn(@PathVariable Long userId) {
        return ResponseEntity.ok(eventService.getEventsUserIsParticipatingIn(userId));
    }
}
