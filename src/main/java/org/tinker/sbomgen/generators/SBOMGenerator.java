package org.tinker.sbomgen.generators;

/**
 * Interface to represent a SBOM generator.
 * The implementations are based on formats (SPDX, CycloneDX)
 */
public interface SBOMGenerator {

    /**
     * Generates a SBOM for the container image.
     *
     * @param source the container image to analyze
     * @return a stringified SBOM
     */
    String generateSBOM(String source);
}


