package com.ttu.lunchbot.parser.menu;

import com.ttu.lunchbot.parser.menu.strategy.MenuParserStrategy;
import com.ttu.lunchbot.spring.model.Menu;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;

/**
 * MenuParser takes a file containing menu texts and uses the given parsing strategy to parse
 * the texts into MenuItems and Menus that can later be used for easy data retrieval.
 */
public abstract class MenuParser {

    /**
     * Strategy for parsing menu texts into MenuItems and Menus.
     */
    @Getter
    private final MenuParserStrategy strategy;

    /**
     * Create and initialize a new menu parser.
     */
    MenuParser(MenuParserStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * @param file File object representing a menu document.
     * @return Collection of parsed Menus.
     * @see Menu
     */
    public abstract ArrayList<Menu> parseMenus(File file);
}
