package si.um.si.unit_tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    // Positive Scenario: Test for successful task edit
    @Test
    void testEditTask_Success() {
        // Arrange: Set up valid inputs for task editing
        TaskService taskService = new TaskService();
        String taskId = "task123";
        String newDescription = "Updated task description";

        // Act: Call the method to edit the task
        boolean result = taskService.editTask(taskId, newDescription);

        // Assert: Verify that the task edit is successful
        assertTrue(result, "Task should be edited successfully with valid inputs.");
    }

    // Negative Scenario: Test for failed task edit due to invalid input
    @Test
    void testEditTask_Failure() {
        // Arrange: Set up invalid inputs for task editing
        TaskService taskService = new TaskService();
        String taskId = ""; // Invalid task ID
        String newDescription = "Updated task description";

        // Act: Call the method to edit the task with invalid inputs
        boolean result = taskService.editTask(taskId, newDescription);

        // Assert: Verify that the task edit fails due to invalid task ID
        assertFalse(result, "Task edit should fail when task ID is invalid.");
    }

    // Another Negative Scenario: Test for task edit failure with null description
    @Test
    void testEditTask_Failure_InvalidDescription() {
        // Arrange: Set up invalid input for the description
        TaskService taskService = new TaskService();
        String taskId = "task123";
        String newDescription = null; // Invalid description

        // Act: Call the method to edit the task with invalid description
        boolean result = taskService.editTask(taskId, newDescription);

        // Assert: Verify that the task edit fails due to invalid description
        assertFalse(result, "Task edit should fail when the description is null.");
    }
}
