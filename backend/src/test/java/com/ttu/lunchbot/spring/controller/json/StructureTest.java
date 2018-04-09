package com.ttu.lunchbot.spring.controller.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttu.lunchbot.spring.controller.FoodServiceController;
import com.ttu.lunchbot.spring.controller.MenuController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ttu.lunchbot.spring.model.*;
import com.ttu.lunchbot.spring.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StructureTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private FoodServiceRepository foodServiceRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private ParserRepository parserRepository;

    @Autowired
    private FoodServiceController foodServiceController;

    @Autowired
    private OpeningTimeRepository openingTimeRepository;

    @Autowired
    private MenuController menuController;

    private FoodService foodService;

    private Menu menu;

    private MenuItem menuItem;

    private Parser parser;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setupBasicDatabase() {
        parser = new Parser();
        foodService = new FoodService();
        menu = new Menu();
        menuItem = new MenuItem();

        parser.setFoodServices(Arrays.asList(foodService));
        parser = parserRepository.saveAndFlush(parser);
        foodService.setParser(parser);

        foodService.setMenus(Arrays.asList(menu));
        foodService = foodServiceRepository.saveAndFlush(foodService);

        List<OpeningTime> openingTimeList = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            OpeningTime openingTime = new OpeningTime();
            openingTime.setOpen(false);
            openingTime.setWeekDay((byte) i);
            openingTime.setFoodService(foodService);
            openingTime = openingTimeRepository.saveAndFlush(openingTime);
            openingTimeList.add(openingTime);
        }
        foodService.setOpeningTimes(openingTimeList);

        menu.setFoodService(foodService);

        menu.addItem(menuItem);
        menu = menuRepository.saveAndFlush(menu);
        menuItem.setMenu(menu);

        menuItem = menuItemRepository.saveAndFlush(menuItem);
    }

    @Test
    @Transactional
    public void testFoodServicesEndpointJsonStructure() throws JsonProcessingException, JSONException {
        List<String> requiredFields = Arrays.asList("id", "website", "lon", "lat", "open_today", "open_time",
                "close_time", "name_en", "menu_url");

        String str = mapper.writeValueAsString(foodServiceController.getAllFoodServices());

        JSONObject json = new JSONArray(str).getJSONObject(0);

        for (String field : requiredFields) {
            if (!json.has(field)) fail("Field " + field + " not in endpoint!");
        }
    }

    @Test
    @Transactional
    public void testGetFoodservicesTodayMenuJsonStructure() throws JsonProcessingException, JSONException {
        List<String> requiredFields = Arrays.asList("id", "date", "food_service", "menu_items");
        List<String> requiredMenuItemFields = Arrays.asList("id", "name_et", "name_en", "price", "currency");
        List<String> requiredFoodServiceFields = Arrays.asList("id", "name_et", "name_en", "open_today", "open_time",
                "close_time", "website", "lon", "lat");

        menu.setDate(LocalDate.now());
        String str = mapper.writeValueAsString(foodServiceController.getTodayMenuByFoodServiceId(foodService.getId()));

        System.out.println(str);
        JSONObject json = new JSONObject(str);

        for (String field : requiredFields) {
            if (!json.has(field)) fail("Field " + field + " not in endpoint!");
        }

        JSONObject menuItemJson = json.getJSONArray("menu_items").getJSONObject(0);
        for (String field : requiredMenuItemFields) {
            if (!menuItemJson.has(field)) fail("Field menu_items: " + field + " not in endpoint!");
        }

        JSONObject foodServiceJson = json.getJSONObject("food_service");
        for (String field : requiredFoodServiceFields) {
            if (!foodServiceJson.has(field)) fail("Field food_service: " + field + " not in endpoint!");
        }


    }

    @Test
    @Transactional
    public void testMenusFilterJsonStructure() throws JsonProcessingException, JSONException {
        List<String> requiredFields = Arrays.asList("id", "date", "food_service", "menu_items");
        List<String> requiredMenuItemFields = Arrays.asList("id", "name_et", "name_en", "price", "currency");
        List<String> requiredFoodServiceFields = Arrays.asList("id", "name_et", "name_en", "open_today", "open_time",
                "close_time", "website", "lon", "lat");

        int price = 5;
        int maxPrice = 6;

        menu.setDate(LocalDate.now());
        menuItem.setPrice(new BigDecimal(price));

        String str = mapper.writeValueAsString(menuController.getTodayMenusFilteredByPrice(Integer.toString(maxPrice)));

        System.out.println(str);
        JSONObject json = new JSONArray(str).getJSONObject(0);

        for (String field : requiredFields) {
            if (!json.has(field)) fail("Field " + field + " not in endpoint!");
        }

        JSONObject menuItemJson = json.getJSONArray("menu_items").getJSONObject(0);
        for (String field : requiredMenuItemFields) {
            if (!menuItemJson.has(field)) fail("Field menu_items: " + field + " not in endpoint!");
        }

        JSONObject foodServiceJson = json.getJSONObject("food_service");
        for (String field : requiredFoodServiceFields) {
            if (!foodServiceJson.has(field)) fail("Field food_service: " + field + " not in endpoint!");
        }
    }
}