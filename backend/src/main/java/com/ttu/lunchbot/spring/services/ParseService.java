package com.ttu.lunchbot.spring.services;

import com.ttu.lunchbot.converter.CalendarConverter;
import com.ttu.lunchbot.menuparser.BalticRestaurantMenuParserStrategy;
import com.ttu.lunchbot.spring.models.Cafe;
import com.ttu.lunchbot.spring.models.MenuItem;
import com.ttu.lunchbot.spring.models.Menu;
import com.ttu.lunchbot.menuparser.MenuParser;
import com.ttu.lunchbot.spring.repositories.CafeRepository;
import com.ttu.lunchbot.spring.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
public class ParseService {

    private MenuService menuService;

    private MenuItemService menuItemService;

    private CafeRepository cafeRepository;

    private MenuRepository menuRepository;

    public ParseService(MenuService menuService, MenuItemService menuItemService, CafeRepository cafeRepository, MenuRepository menuRepository) {
        this.menuService = menuService;
        this.menuItemService = menuItemService;
        this.cafeRepository = cafeRepository;
        this.menuRepository = menuRepository;
    }

    public List<Menu> parseCafeMenu(long cafeId) {
        MenuParser menuParser;
        try {
            // TODO make other restaurants use their specific strategies
            menuParser = new MenuParser(new BalticRestaurantMenuParserStrategy());
            Cafe cafe = cafeRepository.findOne(cafeId);;
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
        CalendarConverter calendarConverter = new CalendarConverter();

        for (com.ttu.lunchbot.menuparser.Menu parsedMenu : menuList) {
            if (menuWithSameDateExists(calendarConverter, parsedMenu, cafe)) continue;
            Menu menu = new Menu(parsedMenu.getName(), parsedMenu.getDate(), cafe);
            for (com.ttu.lunchbot.menuparser.MenuItem parsedItem : parsedMenu.getItems()) {
                Currency currency = parsedItem.getPrices().keySet().stream().findAny().get();
                MenuItem item = new MenuItem(
                        parsedItem.getNames().values().stream().findAny().get(),
                        menu,
                        parsedItem.getPrices().keySet().stream().findAny().get(),
                        parsedItem.getPrice(currency)
                );
                menu.getMenuItems().add(item);
            }
            menus.add(menuService.addMenu(menu));
        }
        return menus;
    }

    private boolean menuWithSameDateExists(CalendarConverter calendarConverter,
                                           com.ttu.lunchbot.menuparser.Menu parsedMenu, Cafe cafe) {
        for (Menu repositoryMenu : menuRepository.findByCafe_Id(cafe.getId())) {
            if (calendarConverter.calendarToLocalDate(parsedMenu.getDate())
                    .equals(repositoryMenu.getDate())) {
                return true;
            }
        }
        return false;
    }

}
