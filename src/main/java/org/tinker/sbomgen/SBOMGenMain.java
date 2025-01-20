package org.tinker.sbomgen;

import org.tinker.sbomgen.core.PackageSearcher;
import org.tinker.sbomgen.core.SyftWrapper;
import org.tinker.sbomgen.entities.CLIArgs;
import org.tinker.sbomgen.parser.CLIParser;

import java.util.Objects;

/**
 * Entry point to SBOMGen.
 */
public class SBOMGenMain {
    public static void main(String[] args) {
        CLIParser cliParser = new CLIParser();
        CLIArgs cliArgs = cliParser.parse(args);

        if (Objects.isNull(cliArgs)) {
            return;
        }

        // Delegate all processing to SyftWrapper
        PackageSearcher packageSearcher = new PackageSearcher();
        SyftWrapper syftWrapper = new SyftWrapper(packageSearcher);
        syftWrapper.process(cliArgs);
    }
}
