package com.ttu.lunchbot.spring.service;

import com.ttu.lunchbot.parser.menu.PDFMenuParser;
import com.ttu.lunchbot.parser.menu.strategy.rahvatoit.LIBParserStrategy;
import com.ttu.lunchbot.parser.menu.strategy.rahvatoit.SOCParserStrategy;
import com.ttu.lunchbot.spring.model.FoodService;
import com.ttu.lunchbot.parser.menu.strategy.baltic.BalticRestaurantMenuParserStrategy;
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
            ArrayList<Menu> menuList;
            if (foodService.getParser().getName().equals("BALTIC")) {
                MenuParser menuParser = new PDFMenuParser(new BalticRestaurantMenuParserStrategy());
                String destination = "/tmp/" + foodService.getNameEn() + ".pdf";

                File newFile = new File(destination);
                FileUtils.copyURLToFile(new URL(foodService.getMenuUrl()), newFile, 10000, 10000);

                menuList = menuParser.parseMenus(newFile);

                return getNewMenus(foodService, menuList);

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
                } else if (foodService.getParser().getName().endsWith("SOC")) {
                    menuList = new SOCParserStrategy().parse(bodyString);
                } else {
                    throw new ResourceNotFoundException(foodService.getParser().getName() + " parser does not exist!");
                }

            } else {
                throw new ResourceNotFoundException("Parser does not exist!");
            }
            return getNewMenus(foodService, menuList);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Menu> parseAllFoodServiceMenus() {
        List<Menu> parsedMenus = new ArrayList<>();
        for (FoodService foodService : foodServiceRepository.findAll()) {
            parsedMenus.addAll(parseFoodServiceMenu(foodService));
        }
        return parsedMenus;
    }

    private List<Menu> getNewMenus(FoodService foodService, List<Menu> menuList) {
        List<Menu> newMenus = new ArrayList<>();

        List<LocalDate> datesOfSavedMenus = getDatesOfSavedMenusOfFoodService(foodService);

        for (Menu parsedMenu : menuList) {
            if (menuWithSameDateExists(datesOfSavedMenus, parsedMenu)) continue;

            parsedMenu.setFoodService(foodService);

            newMenus.add(menuService.addMenu(parsedMenu));
        }
        return newMenus;
    }

    private List<LocalDate> getDatesOfSavedMenusOfFoodService(FoodService foodService) {
        List<LocalDate> datesOfSavedMenus = new ArrayList<>();
        for (Menu repositoryMenu : menuRepository.findByFoodServiceId(foodService.getId())) {
            datesOfSavedMenus.add(repositoryMenu.getDate());
        }
        return datesOfSavedMenus;
    }

    private boolean menuWithSameDateExists(List<LocalDate> savedMenuDates, Menu parsedMenu) {
        for (LocalDate savedDate : savedMenuDates) {
            if (parsedMenu.getDate().equals(savedDate)) {
                return true;
            }
        }
        return false;
    }

}
