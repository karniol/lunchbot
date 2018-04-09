package com.ttu.lunchbot.parser.menu;

import com.ttu.lunchbot.parser.menu.strategy.MenuParserStrategy;
import com.ttu.lunchbot.spring.model.Menu;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * StringMenuParser takes a file containing menu texts, extracts the file
 * into a collection of lines of text and uses the given parsing strategy to parse
 * the texts into MenuItems and Menus that can later be used for easy data retrieval.
 * @see MenuParser
 */
public class StringMenuParser extends MenuParser {

    /**
     * Create and initialize a new string menu parser.
     */
    public StringMenuParser(MenuParserStrategy strategy) {
        super(strategy);
    }

    /**
     * @param file File object representing a menu document.
     * @return Collection of parsed Menus.
     * @see Menu
     */
    @Override
    public ArrayList<Menu> parseMenus(File file) {
        try {
            String text = FileUtils.readFileToString(file, "UTF-8");
            return this.getStrategy().parse(text);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
