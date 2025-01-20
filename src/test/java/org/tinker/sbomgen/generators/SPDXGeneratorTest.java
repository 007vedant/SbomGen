package org.tinker.sbomgen.generators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.tinker.sbomgen.core.CommandExecutor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_SPDX;
import static org.tinker.sbomgen.entities.Constants.JSON_FORMAT;
import static org.tinker.sbomgen.entities.Constants.SYFT_EXEC_COMMAND;

/**
 * Class to test {@link org.tinker.sbomgen.generators.SPDXGenerator}.
 */
class SPDXGeneratorTest {
    private SPDXGenerator spdxGenerator;

    @BeforeEach
    void setUp() {
        spdxGenerator = new SPDXGenerator();
    }

    @Test
    void testGenerateSBOM() {
        String source = "alpine";
        String expectedFormat = SBOM_FORMAT_SPDX + "-" + JSON_FORMAT;
        String expectedCommand = String.format(SYFT_EXEC_COMMAND, source, expectedFormat);
        String expectedOutput = "{\"sbom\": \"example spdx json content\"}";

        try (MockedStatic<CommandExecutor> mockedExecutor = Mockito.mockStatic(CommandExecutor.class)) {
            mockedExecutor.when(() -> CommandExecutor.execute(expectedCommand)).thenReturn(expectedOutput);

            String actualOutput = spdxGenerator.generateSBOM(source);

            // Assert
            assertNotNull(actualOutput, "Generated SBOM output should not be null");
            assertEquals(expectedOutput, actualOutput, "Generated SBOM output should match the expected output");

            mockedExecutor.verify(() -> CommandExecutor.execute(expectedCommand), times(1));
        }
    }
}

