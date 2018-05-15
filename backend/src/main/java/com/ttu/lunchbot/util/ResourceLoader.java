package com.ttu.lunchbot.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Load resources.
 */
public class ResourceLoader {

    /**
     * The absolute path to the backend\\src folder. Direction of slahes doesn't matter as they get replaced by "\".
     * Example:
     * C:\Users\Tanel\IdeaProjects\lunchbot\backend\src
     * /home/john/projects/lunchbot/backend/src
     */
    private static final String backendSrcAbsolutePath = System.getenv().get("LUNCHBOT_BACKEND_SRC_ABS_PATH");

    /**
     * Return the lines of the specified file in the relative location from src folder.
     * @param relativeLocation  The relative location of the text file starting from src folder
     *                          if file is src\\folder\\asd.txt, then relative location should be folder\\asd.txt
     * @return the list of lines in the file as strings
     */
    public List<String> getLines(String relativeLocation) {
        String absLocation = getAbsoluteLocation(relativeLocation);
        try {
            List<String> lines = new ArrayList<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(absLocation)));
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                lines.add(currLine);
            }
            return lines;

        } catch (IOException | NullPointerException e) {
            System.out.println("Error reading file from " + absLocation + "!");
            return null;
        }
    }

    private String getAbsoluteLocation(String locationFromSrcFolder) {
        return backendSrcAbsolutePath + "/"
                + locationFromSrcFolder;
    }
}

