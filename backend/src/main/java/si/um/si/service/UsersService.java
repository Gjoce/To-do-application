package si.um.si.service;

import si.um.si.model.Task;
import si.um.si.model.Users;
import si.um.si.model.Event;
import si.um.si.model.enums.Role;
import si.um.si.repository.TaskRepository;
import si.um.si.repository.UserRepository;
import si.um.si.repository.EvenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private EvenRepository eventRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Users registerUser(String username, String email, String password, Role role) {
        if (usersRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }
        Users user = new Users(username, email, passwordEncoder.encode(password), role);
        return usersRepository.save(user);
    }


    public Optional<Users> loginUser(String email, String password) {
        Optional<Users> user = usersRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }


    public List<Task> getUserTasks(Long userId) {
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            return taskRepository.findByUserId(userId);
        }
        return null;
    }


    public List<Event> getUserAppliedEvents(Long userId) {
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            return eventRepository.findByParticipantsContaining(user.get());
        }
        return null;
    }

    // Apply to event
    public Event applyToEvent(long userId, long eventId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (!event.getParticipants().contains(user)) {
            event.getParticipants().add(user);
            eventRepository.save(event);
        }

        return event;
    }


    public Optional<Users> getUserById(long userId) {
        return usersRepository.findById(userId);
    }
}
