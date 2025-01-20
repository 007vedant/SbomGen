package org.tinker.sbomgen.factory;

import org.junit.jupiter.api.Test;
import org.tinker.sbomgen.generators.CycloneDXGenerator;
import org.tinker.sbomgen.generators.SBOMGenerator;
import org.tinker.sbomgen.generators.SPDXGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_SPDX;
import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_CYCLONEDX;

/**
 * Class to test {@link org.tinker.sbomgen.factory.SBOMGeneratorFactory}.
 */
class SBOMGeneratorFactoryTest {

    @Test
    void testGetSPDXGenerator() {
        SBOMGenerator generator = SBOMGeneratorFactory.getGenerator(SBOM_FORMAT_SPDX);

        // Assert
        assertNotNull(generator, "Generator should not be null for SPDX format");
        assertTrue(generator instanceof SPDXGenerator, "Generator should be an instance of SPDXGenerator");
    }

    @Test
    void testGetCycloneDXGenerator() {
        SBOMGenerator generator = SBOMGeneratorFactory.getGenerator(SBOM_FORMAT_CYCLONEDX);

        // Assert
        assertNotNull(generator, "Generator should not be null for CycloneDX format");
        assertTrue(generator instanceof CycloneDXGenerator, "Generator should be an instance of CycloneDXGenerator");
    }

    @Test
    void testGetGeneratorUnsupportedFormat() {
        String unsupportedFormat = "invalid_format";

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        SBOMGeneratorFactory.getGenerator(unsupportedFormat),
                "Expected an IllegalArgumentException for unsupported formats");

        assertEquals("The provided formatted is not supported currently: " + unsupportedFormat,
                exception.getMessage(), "Exception message should match the expected format");
    }

    @Test
    void testGetGeneratorCaseInsensitiveFormat() {
        SBOMGenerator generatorSPDX = SBOMGeneratorFactory.getGenerator(SBOM_FORMAT_SPDX.toUpperCase());
        SBOMGenerator generatorCycloneDX = SBOMGeneratorFactory.getGenerator(SBOM_FORMAT_CYCLONEDX.toUpperCase());

        // Assert
        assertTrue(generatorSPDX instanceof SPDXGenerator, "SPDX format should be case-insensitive");
        assertTrue(generatorCycloneDX instanceof CycloneDXGenerator, "CycloneDX format should be case-insensitive");
    }
}

