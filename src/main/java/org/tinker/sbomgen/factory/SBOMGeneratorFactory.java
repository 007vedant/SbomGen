package org.tinker.sbomgen.factory;

import org.tinker.sbomgen.generators.CycloneDXGenerator;
import org.tinker.sbomgen.generators.SBOMGenerator;
import org.tinker.sbomgen.generators.SPDXGenerator;

import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_SPDX;
import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_CYCLONEDX;

/**
 * Factory class for creating SBOMGenerator instances based on the specified format.
 * Presently supports generation of SBOMs in formats like SPDX and CycloneDX.
 */
public class SBOMGeneratorFactory {

    /**
     * Returns an instance of SBOMGenerator based on format.
     *
     * @param format the SBOM format to use
     * @return a SBOMGenerator instance
     * @throws IllegalArgumentException if the specified format is unsupported
     */
    public static SBOMGenerator getGenerator(String format) {
        return switch (format.toLowerCase()) {
            case SBOM_FORMAT_SPDX -> new SPDXGenerator();
            case SBOM_FORMAT_CYCLONEDX -> new CycloneDXGenerator();
            default ->
                    throw new IllegalArgumentException("The provided formatted is not supported currently: " + format);
        };
    }
}
