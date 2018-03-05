package com.ttu.lunchbot.spring.services;

import com.ttu.lunchbot.util.CalendarConverter;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
public class ParseService {

    private MenuService menuService;

    private CafeRepository cafeRepository;

    private MenuRepository menuRepository;

    public ParseService(MenuService menuService, CafeRepository cafeRepository, MenuRepository menuRepository) {
        this.menuService = menuService;
        this.cafeRepository = cafeRepository;
        this.menuRepository = menuRepository;
    }

    public List<Menu> parseCafeMenu(long cafeId) {
        return parseCafeMenu(cafeRepository.findOne(cafeId));
    }

    public List<Menu> parseCafeMenu(Cafe cafe) {
        try {
            // TODO make other restaurants use their specific strategies
            MenuParser menuParser = new MenuParser(new BalticRestaurantMenuParserStrategy());
            String destination = "/tmp/" + cafe.getName() + ".pdf";

            File newFile = new File(destination);
            FileUtils.copyURLToFile(new URL(cafe.getMenuURL()), newFile, 10000, 10000);

            ArrayList<com.ttu.lunchbot.menuparser.Menu> menuList = menuParser.parseMenus(newFile);

            return getMenus(cafe, menuList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Menu> parseAllCafeMenus() {
        List<Menu> parsedMenus = new ArrayList<>();
        for (Cafe cafe : cafeRepository.findAll()) {
            parsedMenus.addAll(parseCafeMenu(cafe));
        }
        return parsedMenus;
    }

    private List<Menu> getMenus(Cafe cafe, ArrayList<com.ttu.lunchbot.menuparser.Menu> menuList) {
        List<Menu> menus = new ArrayList<>();
        CalendarConverter calendarConverter = new CalendarConverter();

        List<LocalDate> datesOfSavedMenus = getDatesOfSavedCafeMenus(cafe);

        for (com.ttu.lunchbot.menuparser.Menu parsedMenu : menuList) {
            if (menuWithSameDateExists(datesOfSavedMenus, calendarConverter, parsedMenu)) continue;

            // TODO make it possible to use a different language and a different currency
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

    private List<LocalDate> getDatesOfSavedCafeMenus(Cafe cafe) {
        List<LocalDate> datesOfSavedMenus = new ArrayList<>();
        for (Menu repositoryMenu : menuRepository.findByCafe_Id(cafe.getId())) {
            datesOfSavedMenus.add(repositoryMenu.getDate());
        }
        return datesOfSavedMenus;
    }

    private boolean menuWithSameDateExists(List<LocalDate> savedMenuDates, CalendarConverter calendarConverter,
                                           com.ttu.lunchbot.menuparser.Menu parsedMenu) {
        for (LocalDate savedDate : savedMenuDates) {
            if (calendarConverter.calendarToLocalDate(parsedMenu.getDate())
                    .equals(savedDate)) {
                return true;
            }
        }
        return false;
    }

}
