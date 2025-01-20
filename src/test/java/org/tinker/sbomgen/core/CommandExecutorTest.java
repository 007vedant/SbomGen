package org.tinker.sbomgen.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test {@link org.tinker.sbomgen.core.CommandExecutor}.
 */
class CommandExecutorTest {

    @Test
    void testExecuteValidCommand() {
        String command = "echo Hello, World!";
        String output = CommandExecutor.execute(command);

        // Assert
        assertNotNull(output, "Output should not be null");
        assertEquals("Hello, World!", output.trim(), "Output should match the expected string");
    }

    @Test
    void testExecuteCommandWithError() {
        String invalidCommand = "invalidCommandThatDoesNotExist";

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                CommandExecutor.execute(invalidCommand), "Expected a RuntimeException for invalid commands");

        assertTrue(exception.getMessage().contains("Error executing command"),
                "Error message should indicate command execution failure");
    }

    @Test
    void testExecuteCommandWithExitCodeNonZero() {
        String command = "ls a";

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                CommandExecutor.execute(command), "Expected a RuntimeException for commands with non-zero exit code");

        System.out.println(exception.getMessage());
        assertTrue(exception.getMessage().contains("Error executing command"),
                "Error message should indicate non-zero exit code");
    }
}

