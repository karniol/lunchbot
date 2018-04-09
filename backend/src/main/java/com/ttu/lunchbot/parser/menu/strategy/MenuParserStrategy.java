package com.ttu.lunchbot.parser.menu.strategy;

import com.ttu.lunchbot.spring.model.Menu;

import java.util.ArrayList;

/**
 * Interface for implementing menu parsing strategies.
 */
public interface MenuParserStrategy {

    /**
     * Parse a menu given a string of text.
     * @return List of Menu objects, one object for each date found in the text.
     * @see Menu
     */
    ArrayList<Menu> parse(final String text);

    String getParserName();

    String getParserType();
}
