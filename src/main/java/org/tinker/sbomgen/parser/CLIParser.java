package org.tinker.sbomgen.parser;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.tinker.sbomgen.entities.CLIArgs;

import java.util.Objects;

import static org.tinker.sbomgen.entities.Constants.SBOM_GEN_TOOL_CLI_PREFIX;
import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_SPDX;

/**
 * Class to parse command line arguments and model them into {@link org.tinker.sbomgen.entities.CLIArgs}.
 */
public class CLIParser {
    private final String SOURCE = "source";
    private final String FORMAT = "format";
    private final String SEARCH = "search";
    private final String OUTPUT = "output";


    /**
     * Parses command line arguments into internal entity.
     * Shows helper if arguments are incorrect or unexpected.
     *
     * @param args command line arguments
     * @return CLI args
     */
    public CLIArgs parse(String[] args) {
        Options options = new Options();

        options.addOption(SOURCE, true, "Source container image");
        options.addOption(FORMAT, true, "Standard SBOM format (SPDX/CycloneDX)");
        options.addOption(SEARCH, true, "Package name, regex supported.");
        options.addOption(OUTPUT, true, "Output file path (JSON)");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);

            String source = commandLine.getOptionValue(SOURCE);
            String format = commandLine.getOptionValue(FORMAT, SBOM_FORMAT_SPDX.toLowerCase());
            String search = commandLine.getOptionValue(SEARCH);
            String outputFile = commandLine.getOptionValue(OUTPUT);

            if (Objects.isNull(source)) {
                System.err.println("Source must be provided.");
                showHelper(options);
                return null;
            }

            return new CLIArgs(source, format, search, outputFile);
        } catch (ParseException e) {
            System.err.println("Failed to parse arguments: " + e.getMessage());
            showHelper(options);
            return null;
        }
    }

    private void showHelper(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(SBOM_GEN_TOOL_CLI_PREFIX, options);
    }
}

