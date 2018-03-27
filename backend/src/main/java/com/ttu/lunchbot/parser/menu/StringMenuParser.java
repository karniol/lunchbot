package com.ttu.lunchbot.parser.menu;

import com.ttu.lunchbot.model.Menu;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StringMenuParser extends MenuParser {

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
