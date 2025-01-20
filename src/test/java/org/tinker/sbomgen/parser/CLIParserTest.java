package org.tinker.sbomgen.parser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tinker.sbomgen.entities.CLIArgs;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test {@link org.tinker.sbomgen.parser.CLIParser}.
 */
class CLIParserTest {
    private CLIParser cliParser;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        cliParser = new CLIParser();
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() {
        System.setErr(originalErr);
    }

    @Test
    void testParseValidArguments() {
        String[] args = {
                "-source", "alpine",
                "-format", "CycloneDX",
                "-search", "lib.*",
                "-output", "output.json"
        };

        CLIArgs cliArgs = cliParser.parse(args);

        // Assertions
        assertNotNull(cliArgs, "CLIArgs should not be null for valid arguments");
        assertEquals("alpine", cliArgs.getSource(), "Source should match the provided value");
        assertEquals("CycloneDX", cliArgs.getFormat(), "Format should match the provided value");
        assertEquals("lib.*", cliArgs.getSearchExpression(), "Search should match the provided value");
        assertEquals("output.json", cliArgs.getOutputFile(), "Output file should match the provided value");
    }

    @Test
    void testParseMissingSource() {
        String[] args = {
                "-format", "SPDX",
                "-search", "lib.*",
                "-output", "output.json"
        };

        CLIArgs cliArgs = cliParser.parse(args);

        // Assertions
        assertNull(cliArgs, "CLIArgs should be null when source is missing");
        assertTrue(errContent.toString().contains("Source must be provided."),
                "Error message should indicate that source is required");
    }

    @Test
    void testParseInvalidArgument() {
        String[] args = {
                "-invalid", "value",
                "-source", "alpine"
        };

        CLIArgs cliArgs = cliParser.parse(args);

        // Assertions
        assertNull(cliArgs, "CLIArgs should be null for invalid arguments");
        assertTrue(errContent.toString().contains("Failed to parse arguments"),
                "Error message should indicate that argument parsing failed");
    }

    @Test
    void testParseDefaultFormat() {
        String[] args = {
                "-source", "alpine"
        };

        CLIArgs cliArgs = cliParser.parse(args);

        // Assertions
        assertNotNull(cliArgs, "CLIArgs should not be null for valid arguments");
        assertEquals("alpine", cliArgs.getSource(), "Source should match the provided value");
        assertEquals("spdx", cliArgs.getFormat(), "Default format should be SPDX when not provided");
        assertNull(cliArgs.getSearchExpression(), "Search should be null when not provided");
        assertNull(cliArgs.getOutputFile(), "Output file should be null when not provided");
    }
}

