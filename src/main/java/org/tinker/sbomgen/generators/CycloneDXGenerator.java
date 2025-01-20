package org.tinker.sbomgen.generators;

import org.tinker.sbomgen.core.CommandExecutor;

import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_CYCLONEDX;
import static org.tinker.sbomgen.entities.Constants.JSON_FORMAT;
import static org.tinker.sbomgen.entities.Constants.SYFT_EXEC_COMMAND;

/**
 * SBOM generator for the CycloneDX format.
 * Uses the Syft CLI to generate a SBOM in CycloneDX JSON format for the given container image.
 */
public class CycloneDXGenerator implements SBOMGenerator {
    private final String CYCLONEDX_JSON = SBOM_FORMAT_CYCLONEDX + "-" + JSON_FORMAT;

    @Override
    public String generateSBOM(String source) {
        String syftCommand = String.format(SYFT_EXEC_COMMAND, source, CYCLONEDX_JSON);
        return CommandExecutor.execute(syftCommand);
    }
}

