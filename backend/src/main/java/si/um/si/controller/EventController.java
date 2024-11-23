package si.um.si.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import si.um.si.model.Event;
import si.um.si.service.EventService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class EventController {
    private final EventService eventService;


    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping("api/events")
    public List<Event> getAllEvents(@RequestParam(required = false) String type) {
        if (type != null) {
            try {
                return eventService.getEventsByType(type);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid event type: " + type);
            }
        }
        return eventService.getAllEvents();
    }

    @GetMapping("api/events/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("api/events")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping("api/events/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable long id, @RequestBody Event updatedEvent) {
        return eventService.updateEvent(id, updatedEvent)
                .map(event -> ResponseEntity.ok(event))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("api/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
