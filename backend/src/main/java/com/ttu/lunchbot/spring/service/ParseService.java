package com.ttu.lunchbot.spring.service;

import com.ttu.lunchbot.parser.menu.PDFMenuParser;
import com.ttu.lunchbot.spring.model.FoodService;
import com.ttu.lunchbot.spring.property.FacebookGraphProperties;
import com.ttu.lunchbot.util.CalendarConverter;
import com.ttu.lunchbot.parser.menu.strategy.baltic.BalticRestaurantMenuParserStrategy;
import com.ttu.lunchbot.spring.model.MenuItem;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.parser.menu.MenuParser;
import com.ttu.lunchbot.spring.repository.FoodServiceRepository;
import com.ttu.lunchbot.spring.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Service
public class ParseService {

    private String ENG_LANGUAGE_TAG = "en";

    private String EST_LANGUAGE_TAG = "et";

    private MenuService menuService;

    private FoodServiceRepository foodServiceRepository;

    private MenuRepository menuRepository;

    private FacebookGraphProperties facebookGraphProperties;

    @Autowired
    public ParseService(MenuService menuService,
                        FoodServiceRepository foodServiceRepository,
                        MenuRepository menuRepository,
                        FacebookGraphProperties facebookGraphProperties) {
        this.menuService = menuService;
        this.foodServiceRepository = foodServiceRepository;
        this.menuRepository = menuRepository;
        this.facebookGraphProperties = facebookGraphProperties;
    }

    public List<Menu> parseFoodServiceMenu(long cafeId) {
        return parseFoodServiceMenu(foodServiceRepository.findOne(cafeId));
    }

    public List<Menu> parseFoodServiceMenu(FoodService foodService) {
        try {
            if (foodService == null) throw new ResourceNotFoundException("Food service not found");

            // TODO make other restaurants use their specific strategies
            MenuParser menuParser = new PDFMenuParser(new BalticRestaurantMenuParserStrategy());
            String destination = "/tmp/" + foodService.getName() + ".pdf";

            File newFile = new File(destination);
            FileUtils.copyURLToFile(new URL(foodService.getMenuURL()), newFile, 10000, 10000);

            ArrayList<com.ttu.lunchbot.model.Menu> menuList = menuParser.parseMenus(newFile);

            return getMenus(foodService, menuList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Menu> parseAllFoodServiceMenus() {
        List<Menu> parsedMenus = new ArrayList<>();
        for (FoodService foodService : foodServiceRepository.findAll()) {
            parsedMenus.addAll(parseFoodServiceMenu(foodService));
        }
        return parsedMenus;
    }

    private List<Menu> getMenus(FoodService foodService, ArrayList<com.ttu.lunchbot.model.Menu> menuList) {
        List<Menu> menus = new ArrayList<>();
        CalendarConverter calendarConverter = new CalendarConverter();

        List<LocalDate> datesOfSavedMenus = getDatesOfSavedMenusOfFoodService(foodService);

        for (com.ttu.lunchbot.model.Menu parsedMenu : menuList) {
            if (menuWithSameDateExists(datesOfSavedMenus, calendarConverter, parsedMenu)) continue;

            // TODO make it possible to use a different language and a different currency
            Menu menu = new Menu(parsedMenu.getDate(), foodService);
            for (com.ttu.lunchbot.model.MenuItem parsedItem : parsedMenu.getItems()) {
                Currency currency = Currency.getInstance("EUR");
                MenuItem item = new MenuItem(
                        parsedItem.getName(Locale.forLanguageTag(EST_LANGUAGE_TAG)),
                        parsedItem.getName(Locale.forLanguageTag(ENG_LANGUAGE_TAG)),
                        menu,
                        currency,
                        parsedItem.getPrice(currency)
                );
                menu.getMenuItems().add(item);
            }
            menus.add(menuService.addMenu(menu));
        }
        return menus;
    }

    private List<LocalDate> getDatesOfSavedMenusOfFoodService(FoodService foodService) {
        List<LocalDate> datesOfSavedMenus = new ArrayList<>();
        for (Menu repositoryMenu : menuRepository.findByFoodServiceId(foodService.getId())) {
            datesOfSavedMenus.add(repositoryMenu.getDate());
        }
        return datesOfSavedMenus;
    }

    private boolean menuWithSameDateExists(List<LocalDate> savedMenuDates, CalendarConverter calendarConverter,
                                           com.ttu.lunchbot.model.Menu parsedMenu) {
        for (LocalDate savedDate : savedMenuDates) {
            if (calendarConverter.toLocalDate(parsedMenu.getDate())
                    .equals(savedDate)) {
                return true;
            }
        }
        return false;
    }

}
