package com.ttu.lunchbot.parser.menu;

import com.ttu.lunchbot.model.Menu;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;


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
