package si.um.si.unit_tests; // Package statement
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Registration {

    // Simplified method to handle user registration
    boolean register(String username, String password, String email) {
        // Simplified return logic using a single expression
        return username != null && !username.isEmpty() &&
                password != null && !password.isEmpty() &&
                email != null && email.contains("@");
    }
        @Test
    @DisplayName("User Registration Test")
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
