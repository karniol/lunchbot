package com.ttu.lunchbot.parser.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ttu.lunchbot.model.Menu;
import com.ttu.lunchbot.model.MenuItem;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    public static void main(String[] args) throws IOException {
        RahvaToitMenuParserStrategy strategy = new RahvaToitMenuParserStrategy();
        MenuParser parser = new JsonMenuParser(strategy);
        File file = File.createTempFile("tmp", "tmp");
        FileUtils.copyURLToFile(new URL("https://graph.facebook.com/357117511056357/posts?access_token=241668753042443|0eEH1A330pAOlmJR3u-i8MjYLA8"), file);
        parser.parseMenus(file);
    }
}
