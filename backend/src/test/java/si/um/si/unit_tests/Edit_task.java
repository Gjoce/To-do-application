import org.junit.jupiter.api.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Edit_task {

    // Simulated Task Editing Logic for the tests
    class TaskService {
        boolean editTask(String taskId, String newDescription) {
            // Simulate successful task edit if taskId is valid
            if (taskId != null && !taskId.isEmpty() && newDescription != null && !newDescription.isEmpty()) {
                return true; // Task edited successfully
            }
            return false; // Task edit failed due to invalid input
        }
    }

    private TaskService taskService;

    // Set up the test environment before all test methods run
    @BeforeAll
    static void initAll() {
        System.out.println("Initializing test class... This runs before all tests.");
    }

    // Set up the test environment before each test method
    @BeforeEach
    void setUp() {
        taskService = new TaskService(); // Initialize TaskService before each test
        System.out.println("Setting up the TaskService for each test.");
    }

    // Clean up after each test method
    @AfterEach
    void tearDown() {
        System.out.println("Cleaning up after test.");
        // Reset any test-specific resources if necessary
    }

    // Positive Scenario: Test for successful task edit
    @Test
    @DisplayName("Test successful task edit with valid taskId and description")
    @Tag("edit")
    void testEditTask_Success() {
        // Arrange: Set up valid inputs for task editing
        String taskId = "task123";
        String newDescription = "Updated task description";

        // Act: Call the method to edit the task
        boolean result = taskService.editTask(taskId, newDescription);

        // Assert: Verify that the task edit is successful
        assertTrue(result, "Task should be edited successfully with valid inputs.");
    }

    // Negative Scenario: Test for failed task edit due to invalid input
    @Test
    @DisplayName("Test failed task edit due to invalid taskId")
    @Tag("failure")
    void testEditTask_Failure() {
        // Arrange: Set up invalid inputs for task editing
        String taskId = ""; // Invalid task ID
        String newDescription = "Updated task description";

        // Act: Call the method to edit the task with invalid inputs
        boolean result = taskService.editTask(taskId, newDescription);

        // Assert: Verify that the task edit fails due to invalid task ID
        assertFalse(result, "Task edit should fail when task ID is invalid.");
    }

    // Another Negative Scenario: Test for task edit failure with null description
    @Test
    @DisplayName("Test failed task edit due to null description")
    @Tag("failure")
    void testEditTask_Failure_InvalidDescription() {
        // Arrange: Set up invalid input for the description
        String taskId = "task123";
        String newDescription = null; // Invalid description

        // Act: Call the method to edit the task with invalid description
        boolean result = taskService.editTask(taskId, newDescription);

        // Assert: Verify that the task edit fails due to invalid description
        assertFalse(result, "Task edit should fail when the description is null.");
    }

    // Test Factory: Generate multiple task edit tests dynamically
    @TestFactory
    @DisplayName("Test task edit multiple times with dynamic generation")
    Stream<org.junit.jupiter.api.DynamicTest> dynamicTestForTaskEdit() {
        String taskId = "task123";
        String newDescription = "Repeated test description";

        return Stream.of(
                org.junit.jupiter.api.DynamicTest.dynamicTest("Test 1 - Task edit",
                        () -> assertTrue(taskService.editTask(taskId, newDescription), "Task should be edited successfully.")),
                org.junit.jupiter.api.DynamicTest.dynamicTest("Test 2 - Task edit",
                        () -> assertTrue(taskService.editTask(taskId, newDescription), "Task should be edited successfully.")),
                org.junit.jupiter.api.DynamicTest.dynamicTest("Test 3 - Task edit",
                        () -> assertTrue(taskService.editTask(taskId, newDescription), "Task should be edited successfully."))
        );
    }

    // Timeout Test: This test will fail if it takes more than 1 second to execute
    @Test
    @Timeout(value = 1)  // Timeout value is 1 second
    @DisplayName("Test for task edit with timeout")
    void testEditTask_Timeout() {
        String taskId = "task123";
        String newDescription = "Timeout test description";

        // Simulate a task edit that is guaranteed to finish in less than 1 second
        boolean result = taskService.editTask(taskId, newDescription);
        assertTrue(result, "Task should be edited successfully before the timeout.");
    }
}
