package com.ttu.lunchbot.parser.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ttu.lunchbot.model.Menu;
import com.ttu.lunchbot.model.MenuItem;
import com.ttu.lunchbot.spring.property.FacebookGraphProperties;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@Component
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

    }
}
