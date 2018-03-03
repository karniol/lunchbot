package com.ttu.lunchbot.spring.services;

import com.ttu.lunchbot.menuparser.BalticRestaurantMenuParserStrategy;
import com.ttu.lunchbot.spring.models.Cafe;
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

    private CafeService cafeService;

    public ParseService(MenuService menuService, MenuItemService menuItemService, CafeService cafeService) {
        this.menuService = menuService;
        this.menuItemService = menuItemService;
        this.cafeService = cafeService;
    }

    public List<Menu> parseCafeMenu(long cafeId) {
        MenuParser menuParser;
        try {
            menuParser = new MenuParser(new BalticRestaurantMenuParserStrategy());
            Cafe cafe = cafeService.getCafeById(cafeId);
            String destination = "Parsefiles/" + cafe.getName() + ".pdf";

            File newFile = new File(destination);
            FileUtils.copyURLToFile(new URL(cafe.getMenuURL()), newFile, 10000, 10000);

            ArrayList<com.ttu.lunchbot.menuparser.Menu> menuList = menuParser.parseMenus(newFile);

            return getMenus(cafe, menuList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Menu> getMenus(Cafe cafe, ArrayList<com.ttu.lunchbot.menuparser.Menu> menuList) {
        List<Menu> menus = new ArrayList<>();

        for (com.ttu.lunchbot.menuparser.Menu parsedMenu : menuList) {
            Menu menu = menuService.addMenu(new Menu(parsedMenu.getName(), cafe));
            for (com.ttu.lunchbot.menuparser.MenuItem parsedItem : parsedMenu.getItems()) {
                MenuItem item = new MenuItem(parsedItem.getNames().values().stream().findAny().get(), menu);
                menuItemService.addMenuItem(item);
            }
            menus.add(menuService.getMenuById(menu.getId()));
        }
        return menus;
    }

}
