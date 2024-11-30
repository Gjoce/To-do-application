package si.um.si.unit_tests; // Package statement
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

class Registration {

    // Using BCryptPasswordEncoder to simulate password encoding
    boolean register(String username, String password, String email) {
        // Simulate password encoding
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        // Here, you can check if the password is encoded, not just the fields
        return username != null && !username.isEmpty() &&
                password != null && !password.isEmpty() &&
                email != null && email.contains("@") && encodedPassword != null;
    }

    @Test
    @DisplayName("User Registration Test with Password Encoding")
    void testUserRegistration() {
        // Arrange: Set up test data and expected outcomes
        String username = "testUser";
        String password = "securePassword";
        String email = "testUser@example.com";

        Registration registration = new Registration();

        // Act: Call the registration method
        boolean registrationResult = registration.register(username, password, email);

        // Assert: Verify the result
        assertTrue(registrationResult, "The registration should succeed with valid inputs.");
    }
}
