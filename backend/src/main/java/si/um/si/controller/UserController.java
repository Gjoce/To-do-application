package si.um.si.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.um.si.model.Event;
import si.um.si.model.Task;
import si.um.si.model.Users;
import si.um.si.service.UsersService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UsersService userService;

    @Autowired
    public UserController(UsersService userService) {
        this.userService = userService;
    }

    // Register user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users userRequest) {
        try {
            Users user = userService.registerUser(userRequest.getUsername(),
                    userRequest.getEmail(),
                    userRequest.getPassword(),
                    userRequest.getRole());
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<Users> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<Users> user = userService.loginUser(email, password);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.status(401).build()); // 401 Unauthorized
    }

    // Get user tasks
    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> getUserTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserTasks(userId));
    }

    // Get user applied events
    @GetMapping("/{userId}/applied-events")
    public ResponseEntity<List<Event>> getUserAppliedEvents(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserAppliedEvents(userId));
    }

    // Apply to event
    @PostMapping("/{userId}/apply/{eventId}")
    public ResponseEntity<Event> applyToEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        try {
            Event event = userService.applyToEvent(userId, eventId);
            return ResponseEntity.ok(event); // Return updated event with user participation
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request if user or event not found
        }
    }
}
