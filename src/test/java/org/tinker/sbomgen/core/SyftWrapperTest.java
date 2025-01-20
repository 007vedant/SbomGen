package org.tinker.sbomgen.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.tinker.sbomgen.entities.CLIArgs;
import org.tinker.sbomgen.factory.SBOMGeneratorFactory;
import org.tinker.sbomgen.generators.SBOMGenerator;
import org.tinker.sbomgen.utils.Utility;

import static org.mockito.Mockito.*;

/**
 * Class to test {@link org.tinker.sbomgen.core.SyftWrapper}.
 */
class SyftWrapperTest {

    private SyftWrapper syftWrapper;
    private PackageSearcher mockPackageSearcher;

    @BeforeEach
    void setUp() {
        mockPackageSearcher = mock(PackageSearcher.class);
        syftWrapper = new SyftWrapper(mockPackageSearcher);
    }

    @Test
    void testProcessWithSearchAndOutputFile() {
        String source = "alpine";
        String format = "spdx";
        String searchExpression = "lib.*";
        String outputFile = "output.json";

        String generatedSBOM = "{\n" +
                "  \"sbom\": \"example spdx content\",\n" +
                "  \"packages\": [\n" +
                "    { \"name\": \"libc\" },\n" +
                "    { \"name\": \"openssl\" }\n" +
                "  ]\n" +
                "}";

        String searchResults = "{\n" +
                "  \"name\": \"libc\"\n" +
                "}";

        CLIArgs cliArgs = new CLIArgs(source, format, searchExpression, outputFile);

        try (MockedStatic<SBOMGeneratorFactory> mockedFactory = Mockito.mockStatic(SBOMGeneratorFactory.class);
             MockedStatic<Utility> mockedUtility = Mockito.mockStatic(Utility.class)) {

            SBOMGenerator mockGenerator = mock(SBOMGenerator.class);
            mockedFactory.when(() -> SBOMGeneratorFactory.getGenerator(format)).thenReturn(mockGenerator);

            when(mockGenerator.generateSBOM(source)).thenReturn(generatedSBOM);
            when(mockPackageSearcher.search(generatedSBOM, format, searchExpression)).thenReturn(searchResults);

            syftWrapper.process(cliArgs);

            // Assert
            verify(mockGenerator, times(1)).generateSBOM(source);
            verify(mockPackageSearcher, times(1)).search(generatedSBOM, format, searchExpression);
            mockedUtility.verify(() -> Utility.writeToFile(outputFile, searchResults), times(1));
        }
    }

    @Test
    void testProcessWithoutSearchButWithOutputFile() {
        String source = "alpine";
        String format = "spdx";
        String outputFile = "output.json";

        String generatedSBOM = "{\n" +
                "  \"sbom\": \"example spdx content\",\n" +
                "  \"packages\": [\n" +
                "    { \"name\": \"libc\" },\n" +
                "    { \"name\": \"openssl\" }\n" +
                "  ]\n" +
                "}";

        CLIArgs cliArgs = new CLIArgs(source, format, null, outputFile);

        try (MockedStatic<SBOMGeneratorFactory> mockedFactory = Mockito.mockStatic(SBOMGeneratorFactory.class);
             MockedStatic<Utility> mockedUtility = Mockito.mockStatic(Utility.class)) {

            SBOMGenerator mockGenerator = mock(SBOMGenerator.class);
            mockedFactory.when(() -> SBOMGeneratorFactory.getGenerator(format)).thenReturn(mockGenerator);

            when(mockGenerator.generateSBOM(source)).thenReturn(generatedSBOM);

            syftWrapper.process(cliArgs);

            // Assert
            verify(mockGenerator, times(1)).generateSBOM(source);
            verify(mockPackageSearcher, never()).search(anyString(), anyString(), anyString());
            mockedUtility.verify(() -> Utility.writeToFile(outputFile, generatedSBOM), times(1));
        }
    }

    @Test
    void testProcessWithNoSearchAndNoOutputFile() {
        String source = "alpine";
        String format = "spdx";

        String generatedSBOM = "{\n" +
                "  \"sbom\": \"example spdx content\",\n" +
                "  \"packages\": [\n" +
                "    { \"name\": \"libc\" },\n" +
                "    { \"name\": \"openssl\" }\n" +
                "  ]\n" +
                "}";

        CLIArgs cliArgs = new CLIArgs(source, format, null, null);

        try (MockedStatic<SBOMGeneratorFactory> mockedFactory = Mockito.mockStatic(SBOMGeneratorFactory.class)) {

            SBOMGenerator mockGenerator = mock(SBOMGenerator.class);
            mockedFactory.when(() -> SBOMGeneratorFactory.getGenerator(format)).thenReturn(mockGenerator);

            when(mockGenerator.generateSBOM(source)).thenReturn(generatedSBOM);

            syftWrapper.process(cliArgs);

            // Assert
            verify(mockGenerator, times(1)).generateSBOM(source);
            verify(mockPackageSearcher, never()).search(anyString(), anyString(), anyString());
        }
    }
}


