package org.tinker.sbomgen.core;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_CYCLONEDX;
import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_SPDX;

/**
 * Class to test {@link org.tinker.sbomgen.core.PackageSearcher}.
 */
class PackageSearcherTest {
    private PackageSearcher packageSearcher;

    @BeforeEach
    void setUp() {
        packageSearcher = new PackageSearcher();
    }

    @Test
    void testSearchWithValidSPDXFormat() {
        String sbom = new JSONObject()
                .put("packages", new org.json.JSONArray()
                        .put(new JSONObject().put("name", "libc"))
                        .put(new JSONObject().put("name", "openssl"))
                ).toString();

        String regex = "lib.*";

        String result = packageSearcher.search(sbom, SBOM_FORMAT_SPDX, regex);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.contains("libc"), "Result should contain 'libc'");
        assertFalse(result.contains("openssl"), "Result should not contain 'openssl'");
    }

    @Test
    void testSearchWithValidCycloneDXFormat() {
        String sbom = new JSONObject()
                .put("components", new org.json.JSONArray()
                        .put(new JSONObject().put("name", "libxml2"))
                        .put(new JSONObject().put("name", "openssl"))
                ).toString();

        String regex = "lib.*";

        String result = packageSearcher.search(sbom, SBOM_FORMAT_CYCLONEDX, regex);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.contains("libxml2"), "Result should contain 'libxml2'");
        assertFalse(result.contains("openssl"), "Result should not contain 'openssl'");
    }

    @Test
    void testSearchWithNoMatches() {
        String sbom = new JSONObject()
                .put("packages", new org.json.JSONArray()
                        .put(new JSONObject().put("name", "openssl"))
                        .put(new JSONObject().put("name", "zlib"))
                ).toString();

        String regex = "nonexistent.*";

        String result = packageSearcher.search(sbom, SBOM_FORMAT_SPDX, regex);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty if no matches are found");
    }

    @Test
    void testSearchWithUnsupportedFormat() {
        String sbom = "{}";
        String format = "unsupported-format";
        String regex = ".*";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                packageSearcher.search(sbom, format, regex), "Expected an IllegalArgumentException for unsupported formats");

        assertTrue(exception.getMessage().contains("not supported currently"),
                "Error message should indicate unsupported format");
    }

    @Test
    void testSearchWithMalformedSBOM() {
        String malformedSbom = "{invalid_json}";
        String regex = ".*";

        // Act & Assert
        assertThrows(org.json.JSONException.class, () ->
                packageSearcher.search(malformedSbom, SBOM_FORMAT_SPDX, regex), "Expected JSONException for malformed SBOM");
    }
}

