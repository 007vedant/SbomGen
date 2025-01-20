package org.tinker.sbomgen.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Class to execute Syft CLI command on shell and capture it's output.
 * The output is sent to {@link org.tinker.sbomgen.core.PackageSearcher} or directly written to console/file.
 */
public class CommandExecutor {

    /**
     * Executes `syft` command and captures it's output as string.
     *
     * @param command the shell command to execute
     * @return the output of the executed command as string
     * @throws RuntimeException if the command fails or an error occurs
     */
    public static String execute(String command) {
        StringBuilder output = new StringBuilder();
        try {
            List<String> commandParts = List.of(command.split(" "));
            ProcessBuilder processBuilder = new ProcessBuilder(commandParts);

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Command failed with exit code: " + exitCode + "\nOutput: " + output);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error executing command: " + command, e);
        }

        return output.toString().trim();
    }
}


