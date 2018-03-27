package com.ttu.lunchbot.parser.menu;

import com.google.gson.Gson;
import com.ttu.lunchbot.model.Menu;
import com.ttu.lunchbot.model.MenuItem;

import java.io.File;
import java.util.ArrayList;

public class RahvaToitMenuParserStrategy implements MenuParserStrategy {

    private final ArrayList<Menu> parsedMenus;
    private final Menu currentMenu;
    private final MenuItem currentMenuItem;

    public RahvaToitMenuParserStrategy() {
        this.parsedMenus = new ArrayList<>();
        this.currentMenu = null;
        this.currentMenuItem = null;
    }

    @Override
    public ArrayList<Menu> parse(String jsonString) {
        RahvaToitJsonObject json = (new Gson()).fromJson(jsonString, RahvaToitJsonObject.class);
        return this.parsedMenus;
    }

    public static void main(String[] args) {
        MenuParserStrategy strategy = new RahvaToitMenuParserStrategy();
        MenuParser parser = new StringMenuParser(strategy);
        final String pathToExample = "src/main/resources/rahvatoit-posts-example.json";
        ArrayList<Menu> parsedMenus = parser.parseMenus(new File(pathToExample));
        System.out.println(parsedMenus);
    }
}
