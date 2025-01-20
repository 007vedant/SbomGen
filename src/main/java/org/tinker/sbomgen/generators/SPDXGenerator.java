package org.tinker.sbomgen.generators;

import org.tinker.sbomgen.core.CommandExecutor;

import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_SPDX;
import static org.tinker.sbomgen.entities.Constants.JSON_FORMAT;
import static org.tinker.sbomgen.entities.Constants.SYFT_EXEC_COMMAND;

/**
 * SBOM generator for the SPDX format.
 * Uses the Syft CLI to generate a SBOM in SPDX JSON format for the given container image.
 */
public class SPDXGenerator implements SBOMGenerator {
    private final String SPDX_JSON = SBOM_FORMAT_SPDX + "-" + JSON_FORMAT;

    @Override
    public String generateSBOM(String source) {
        String syftCommand = String.format(SYFT_EXEC_COMMAND, source, SPDX_JSON);
        return CommandExecutor.execute(syftCommand);
    }
}

