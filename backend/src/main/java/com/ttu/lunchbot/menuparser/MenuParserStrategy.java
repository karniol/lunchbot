package com.ttu.lunchbot.menuparser;

import java.util.ArrayList;

/**
 * Interface for implementing menu parsing strategies.
 */
public interface MenuParserStrategy {

    /**
     * Parse a menu given a collection of lines of text.
     * @return List of Menu objects, one object for each date found in the text.
     * @see Menu
     */
    ArrayList<Menu> parse(ArrayList<String> text);
}
