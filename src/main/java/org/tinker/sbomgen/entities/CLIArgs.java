package org.tinker.sbomgen.entities;

/**
 * POJO class to capture command line arguments for SBOM generation tool.
 */
public class CLIArgs {
    private final String source;
    private final String format;
    private final String searchExpression;
    private final String outputFile;


    public CLIArgs(String source, String format, String searchExpression, String outputFile) {
        this.source = source;
        this.format = format;
        this.searchExpression = searchExpression;
        this.outputFile = outputFile;
    }

    public String getSource() {
        return source;
    }

    public String getFormat() {
        return format;
    }

    public String getSearchExpression() {
        return searchExpression;
    }

    public String getOutputFile() {
        return outputFile;
    }
}
