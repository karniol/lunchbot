package com.ttu.lunchbot.menuparser;

import java.util.ArrayList;

/**
 * Interface for menu parsing strategies.
 */
public interface MenuParserStrategy {

    /**
     * @return Menu object
     */
    Menu parse(ArrayList<String> text);
}
