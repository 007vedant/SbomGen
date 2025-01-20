package org.tinker.sbomgen.utils;

import java.io.*;

/**
 * Class to house common utility methods.
 */
public class Utility {

    /**
     * Writes output to the file provided in the command line input.
     * @param path path of output file
     * @param content string output
     * @throws IOException when creating/accessing the output file
     */
    public static void writeToFile(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
