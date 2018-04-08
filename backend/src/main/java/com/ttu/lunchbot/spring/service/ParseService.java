package com.ttu.lunchbot.spring.service;

import com.ttu.lunchbot.parser.menu.PDFMenuParser;
import com.ttu.lunchbot.parser.menu.strategy.rahvatoit.LIBParserStrategy;
import com.ttu.lunchbot.parser.menu.strategy.rahvatoit.SOCParserStrategy;
import com.ttu.lunchbot.spring.model.FoodService;
import com.ttu.lunchbot.util.CalendarConverter;
import com.ttu.lunchbot.parser.menu.strategy.baltic.BalticRestaurantMenuParserStrategy;
import com.ttu.lunchbot.spring.model.MenuItem;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.parser.menu.MenuParser;
import com.ttu.lunchbot.spring.repository.FoodServiceRepository;
import com.ttu.lunchbot.spring.repository.MenuRepository;
import com.ttu.lunchbot.util.FacebookGraphUtility;
import org.apache.commons.io.IOUtils;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
public class ParseService {

    private MenuService menuService;

    private FoodServiceRepository foodServiceRepository;

    private MenuRepository menuRepository;

    public ParseService(MenuService menuService,
                        FoodServiceRepository foodServiceRepository,
                        MenuRepository menuRepository) {
        this.menuService = menuService;
        this.foodServiceRepository = foodServiceRepository;
        this.menuRepository = menuRepository;
    }

    public List<Menu> parseFoodServiceMenu(long foodServiceId) {
        FoodService foodService = foodServiceRepository.findOne(foodServiceId);
        FoodServiceService.updateFoodServiceOpeningTimes(foodService);
        return parseFoodServiceMenu(foodService);
    }

    public List<Menu> parseFoodServiceMenu(FoodService foodService) {
        if (foodService == null) throw new ResourceNotFoundException("Food service not found");

        try {
            ArrayList<com.ttu.lunchbot.model.Menu> menuList;
            if (foodService.getParser().getName().equals("BALTIC")) {
                MenuParser menuParser = new PDFMenuParser(new BalticRestaurantMenuParserStrategy());
                String destination = "/tmp/" + foodService.getNameEN() + ".pdf";

                File newFile = new File(destination);
                FileUtils.copyURLToFile(new URL(foodService.getMenuURL()), newFile, 10000, 10000);

                menuList = menuParser.parseMenus(newFile);

                return getMenus(foodService, menuList);

            } else if (foodService.getParser().getName().startsWith("RAHVATOIT")) {
                URL url = new URL(new FacebookGraphUtility(System.getenv()
                        .get("LUNCHBOT_FACEBOOK_GRAPH_TOKEN")).getPostsUrl
                        ("rahvatoitttu"));

                URLConnection con = url.openConnection();
                InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                encoding = encoding == null ? "UTF-8" : encoding;
                String bodyString = IOUtils.toString(in, encoding);

                if (foodService.getParser().getName().endsWith("LIB")) {
                    menuList = new LIBParserStrategy().parse(bodyString);
                } else if (foodService.getParser().getName().endsWITH("SOC")) {
                    menuList = new SOCParserStrategy().parse(bodyString);
                } else {
                    throw new ResourceNotFoundException(foodService.getParser().getName() + " parser does not exist!");
                }

            } else {
                throw new ResourceNotFoundException("Parser does not exist!");
            }
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
                Currency currency = com.ttu.lunchbot.util.Currency.EURO;
                MenuItem item = new MenuItem(
                        parsedItem.getName(com.ttu.lunchbot.util.Locale.ESTONIAN),
                        parsedItem.getName(java.util.Locale.ENGLISH),
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
