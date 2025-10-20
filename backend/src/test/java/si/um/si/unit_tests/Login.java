import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import si.um.si.model.Users;
import si.um.si.repository.UserRepository;
import si.um.si.service.UsersService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class Login {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUser_Success() {
        // Mock user data
        Users testUser = new Users("testUser", "test@email.com", "encodedPassword", null);

        // Mock repository and passwordEncoder behavior
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password", testUser.getPassword())).thenReturn(true);

        // Call the service method
        Optional<Users> loggedInUser = usersService.loginUser("test@email.com", "password");

        // Validate results
        assertTrue(loggedInUser.isPresent());
        assertEquals(testUser.getEmail(), loggedInUser.get().getEmail());
    }

    @Test
    void testLoginUser_Failure() {
        // Mock repository behavior for a non-existent user
        when(userRepository.findByEmail("unknown@email.com")).thenReturn(Optional.empty());

        // Call the service method
        Optional<Users> loggedInUser = usersService.loginUser("unknown@email.com", "password");

        // Validate results
        assertFalse(loggedInUser.isPresent());
    }
}
