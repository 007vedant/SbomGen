package org.tinker.sbomgen.utils;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test {@link org.tinker.sbomgen.utils.Utility}.
 */
class UtilityTest {
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("testFile", ".txt");
    }

    @AfterEach
    void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            assertTrue(tempFile.delete(), "Temporary file should be deleted");
        }
    }

    @Test
    void testWriteToFile() {
        String testContent = "SbomGen!";
        String tempFilePath = tempFile.getAbsolutePath();

        Utility.writeToFile(tempFilePath, testContent);
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
        } catch (IOException e) {
            fail("An IOException occurred while reading the file: " + e.getMessage());
        }

        // Assert that the content matches
        assertEquals(testContent, fileContent.toString(), "File content should match the input content");
    }
}


