package com.ttu.lunchbot.parser.menu;

import com.ttu.lunchbot.model.Menu;
import com.ttu.lunchbot.parser.menu.strategy.MenuParserStrategy;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;


public abstract class MenuParser {

    /**
     * Strategy for parsing menu texts into MenuItems and Menus.
     */
    @Getter
    private final MenuParserStrategy strategy;

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
