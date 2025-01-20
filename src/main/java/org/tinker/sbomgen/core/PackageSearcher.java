package org.tinker.sbomgen.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Pattern;

import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_SPDX;
import static org.tinker.sbomgen.entities.Constants.SBOM_FORMAT_CYCLONEDX;

/**
 * Class to parse generated SBOM document and perform search on packages using given regex expression.
 * Currently, supports SPDX and CycloneDX formats.
 */
public class PackageSearcher {
    private final String PACKAGES = "packages";
    private final String COMPONENTS = "components";
    private final String PACKAGE_NAME = "name";

    /**
     * Searches for package names in the SBOM that match the given regex expression.
     *
     * @param sbom       the SBOM content as a string
     * @param expression the regex expression to match package/component names
     * @param format     format of SBOM content
     * @return a string containing the matching packages/components, or a message if none are found
     * @throws IllegalArgumentException if the specified format is unsupported
     */
    public String search(String sbom, String format, String expression) {
        String packageArray = getPackageArray(format);
        Pattern regex = Pattern.compile(expression);
        JSONArray packages = new JSONObject(sbom).getJSONArray(packageArray);
        StringBuilder results = new StringBuilder();

        for (int i = 0; i < packages.length(); i++) {
            String packageName = packages.getJSONObject(i).getString(PACKAGE_NAME);
            if (regex.matcher(packageName).find()) {
                results.append(packages.getJSONObject(i).toString()).append("\n");
            }
        }

        return results.toString();
    }

    private String getPackageArray(String format) {
        return switch (format.toLowerCase()) {
            case SBOM_FORMAT_SPDX -> PACKAGES;
            case SBOM_FORMAT_CYCLONEDX -> COMPONENTS;
            default ->
                    throw new IllegalArgumentException("The provided formatted is not supported currently: " + format);
        };
    }
}

