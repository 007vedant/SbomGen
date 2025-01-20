package org.tinker.sbomgen.core;

import org.tinker.sbomgen.entities.CLIArgs;
import org.tinker.sbomgen.factory.SBOMGeneratorFactory;
import org.tinker.sbomgen.generators.SBOMGenerator;
import org.tinker.sbomgen.utils.Utility;

import java.util.Objects;

/**
 * Wrapper class that manages and delegates the workflow of SBOM generation, package search, and the output.
 */
public class SyftWrapper {
    private final PackageSearcher packageSearcher;

    // Constructor
    public SyftWrapper(PackageSearcher packageSearcher) {
        this.packageSearcher = packageSearcher;
    }

    /**
     * Processes the CLI arguments to generate an SBOM, perform search, and output results.
     *
     * @param cliArgs the command line arguments provided by the user
     */
    public void process(CLIArgs cliArgs) {
        SBOMGenerator generator = SBOMGeneratorFactory.getGenerator(cliArgs.getFormat());
        String sbom = generator.generateSBOM(cliArgs.getSource());

        // perform package search if argument is provided
        if (!Objects.isNull(cliArgs.getSearchExpression())) {
            String searchResults = packageSearcher.search(sbom, cliArgs.getFormat(), cliArgs.getSearchExpression());
            System.out.println("Search Results:\n" + searchResults);
            sbom = searchResults;
        } else {
            System.out.println("Generated SBOM:\n" + sbom);
        }

        if (!Objects.isNull(cliArgs.getOutputFile())) {
            Utility.writeToFile(cliArgs.getOutputFile(), sbom);
        }
    }
}

