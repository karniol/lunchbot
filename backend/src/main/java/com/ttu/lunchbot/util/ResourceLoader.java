package com.ttu.lunchbot.util;

import com.ttu.lunchbot.spring.LunchbotApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Load resources.
 */
public class ResourceLoader {

    /**
     * Return the specified map with waves.
     * @param relativeLocation  The relative location of the text file
     * @return the list of lines in the file as strings
     */
    public static List<String> getLines(String relativeLocation) {
        try {
            List<String> lines = new ArrayList<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    ResourceLoader.getFullStream(relativeLocation)));
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                lines.add(currLine);
            }
            return lines;

        } catch (IOException | NullPointerException e) {
            System.out.println("Error reading file from " + relativeLocation + "!");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the InputStream of a text file.
     * @param end The end of the file, e.g. "com/ttu/lunchbot/text.txt".
     * @return The InputStream of the file.
     */
    private static InputStream getFullStream(String end) {
        return LunchbotApplication.class.getResourceAsStream(end
                .replace("\\", "/"));
    }

}

