package com.ttu.lunchbot.spring.services;

import com.ttu.lunchbot.menuparser.BalticRestaurantMenuParserStrategy;
import com.ttu.lunchbot.spring.models.MenuItem;
import com.ttu.lunchbot.spring.models.Menu;
import com.ttu.lunchbot.menuparser.MenuParser;
import org.springframework.stereotype.Service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParseService {

    private MenuService menuService;

    private MenuItemService menuItemService;

    public ParseService(MenuService menuService, MenuItemService menuItemService) {
        this.menuService = menuService;
        this.menuItemService = menuItemService;
    }

    public List<Menu> parseAll() {
        MenuParser menuParser;
        URL url;
        List<Menu> menus = new ArrayList<>();
        try {
            menuParser = new MenuParser(new BalticRestaurantMenuParserStrategy());


            String fromFile = "http://www.daily.lv/download/?f=dn_daily_nadalamenuu_ttu_peahoone.pdf";
            String[] fromFileSplit = fromFile.split("/");
            String toFile = fromFileSplit[fromFileSplit.length - 1];
            toFile = "peahooooone.pdf";

            File newFile = new File(toFile);
            try {
                //connectionTimeout, readTimeout = 10 seconds
                FileUtils.copyURLToFile(new URL(fromFile), newFile, 10000, 10000);

            } catch (IOException e) {
                e.printStackTrace();
            }


            //url = new URL("www.daily.lv/download/?f=dn_daily_nadalamenuu_ttu_peahoone.pdf");
            //File file = Paths.get(url.toURI()).toFile();
            File file = newFile;
            ArrayList<com.ttu.lunchbot.menuparser.Menu> menuList = menuParser.parseMenus(file);

            for (com.ttu.lunchbot.menuparser.Menu parsedMenu : menuList) {
                Menu menu = menuService.addMenu(new Menu(parsedMenu.getName()));
                for (com.ttu.lunchbot.menuparser.MenuItem parsedItem : parsedMenu.getItems()) {
                    MenuItem item = new MenuItem(parsedItem.getNames().values().stream().findAny().get(), menu);
                    menuItemService.addMenuItem(item);
                }
                menus.add(menuService.getMenuById(menu.getId()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return menus;
    }
}
