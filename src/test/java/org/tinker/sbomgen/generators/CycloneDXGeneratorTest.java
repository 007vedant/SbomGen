package org.tinker.sbomgen.generators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.tinker.sbomgen.core.CommandExecutor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_CYCLONEDX;
import static org.tinker.sbomgen.entities.Constants.JSON_FORMAT;
import static org.tinker.sbomgen.entities.Constants.SYFT_EXEC_COMMAND;

/**
 * Class to test {@link org.tinker.sbomgen.generators.CycloneDXGenerator}.
 */
class CycloneDXGeneratorTest {

    private CycloneDXGenerator cycloneDXGenerator;

    @BeforeEach
    void setUp() {
        cycloneDXGenerator = new CycloneDXGenerator();
    }

    @Test
    void testGenerateSBOM() {
        String source = "alpine";
        String expectedFormat = SBOM_FORMAT_CYCLONEDX + "-" + JSON_FORMAT;
        String expectedCommand = String.format(SYFT_EXEC_COMMAND, source, expectedFormat);
        String expectedOutput = "{\"sbom\": \"example cyclonedx json content\"}";

        try (MockedStatic<CommandExecutor> mockedExecutor = Mockito.mockStatic(CommandExecutor.class)) {
            mockedExecutor.when(() -> CommandExecutor.execute(expectedCommand)).thenReturn(expectedOutput);
            
            String actualOutput = cycloneDXGenerator.generateSBOM(source);

            // Assert
            assertNotNull(actualOutput, "Generated SBOM output should not be null");
            assertEquals(expectedOutput, actualOutput, "Generated SBOM output should match the expected output");
            
            mockedExecutor.verify(() -> CommandExecutor.execute(expectedCommand), times(1));
        }
    }
}

