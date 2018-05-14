package com.ttu.lunchbot.spring.controller.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttu.lunchbot.spring.controller.FoodServiceController;
import com.ttu.lunchbot.spring.controller.MenuController;
import com.ttu.lunchbot.spring.exception.LanguageNotFoundException;
import com.ttu.lunchbot.spring.model.FoodService;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.model.MenuItem;
import com.ttu.lunchbot.spring.model.OpeningTime;
import com.ttu.lunchbot.spring.model.Parser;
import com.ttu.lunchbot.spring.repository.FoodServiceRepository;
import com.ttu.lunchbot.spring.repository.MenuItemRepository;
import com.ttu.lunchbot.spring.repository.MenuRepository;
import com.ttu.lunchbot.spring.repository.OpeningTimeRepository;
import com.ttu.lunchbot.spring.repository.ParserRepository;
import com.ttu.lunchbot.spring.service.FoodServiceService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StructureIntegrationTest {

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
    private FoodServiceService foodServiceService;

    @Autowired
    private MenuController menuController;

    private FoodService foodService;

    private Menu menu;

    private MenuItem menuItem;

    private Parser parser;

    private ObjectMapper mapper = new ObjectMapper();

    private Map<String, String> requestParams;

    @Before
    public void setupEntriesInDatabase() {
        requestParams = new HashMap<>();

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
            openingTime.setOpen(true);
            openingTime.setOpenTime(LocalTime.of(1, 2, 3));
            openingTime.setCloseTime(LocalTime.of(4, 5, 6));
            if (i == 7) openingTime.setCloseTime(LocalTime.of(5, 6, 7));
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
    public void testFoodServicesEndpointJsonStructure() throws JsonProcessingException, JSONException, LanguageNotFoundException {
        List<String> requiredFields = Arrays.asList("id", "website", "lon", "lat", "open_today", "open_time",
                "close_time", "name", "menu_url");

        requestParams.put("lang", "et");
        String str = mapper.writeValueAsString(foodServiceController.getAllFoodServices(requestParams));

        JSONObject json = new JSONArray(str).getJSONObject(0);

        for (String field : requiredFields) {
            if (!json.has(field)) fail("Field " + field + " not in endpoint!");
        }
    }

    @Test
    @Transactional
    public void testGetFoodservicesTodayMenuJsonStructure() throws JsonProcessingException, JSONException, LanguageNotFoundException {
        List<String> requiredFields = Arrays.asList("id", "date", "food_service", "menu_items");
        List<String> requiredMenuItemFields = Arrays.asList("id", "name", "price", "currency");
        List<String> requiredFoodServiceFields = Arrays.asList("id", "name", "open_today", "open_time",
                "close_time", "website", "lon", "lat");

        menu.setDate(LocalDate.now());
        requestParams.put("lang", "et");
        String str = mapper.writeValueAsString(
                foodServiceController.getTodayMenuByFoodServiceId(foodService.getId(), requestParams));

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
    public void testMenusFilterJsonStructure() throws JsonProcessingException, JSONException, LanguageNotFoundException {
        List<String> requiredFields = Arrays.asList("id", "date", "food_service", "menu_items");
        List<String> requiredMenuItemFields = Arrays.asList("id", "name", "price", "currency");
        List<String> requiredFoodServiceFields = Arrays.asList("id", "name", "open_today", "open_time",
                "close_time", "website", "lon", "lat");

        int price = 5;
        int maxPrice = 6;

        menu.setDate(LocalDate.now());
        menuItem.setPrice(new BigDecimal(price));

        requestParams.put("lang", "et");
        String str = mapper.writeValueAsString(
                menuController.getTodayMenusFilteredByPrice(Integer.toString(maxPrice), requestParams));

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

    @Test
    @Transactional
    public void testGetsBackSameOpeningTimesFromDatabase() {
        requestParams.put("lang", "et");
        FoodService foodServiceFromDB = foodServiceService.getFoodServiceById(foodService.getId());
        Assert.assertEquals(7, foodServiceFromDB.getOpeningTimes().size());
        for (OpeningTime openingTime : foodServiceFromDB.getOpeningTimes()) {
            Assert.assertTrue(openingTime.isOpen());
            Assert.assertEquals(LocalTime.of(1, 2, 3), openingTime.getOpenTime());
            if (openingTime.getWeekDay() != (byte) 7) {
                Assert.assertEquals(LocalTime.of(4, 5, 6), openingTime.getCloseTime());
            } else {
                Assert.assertEquals(LocalTime.of(5, 6, 7), openingTime.getCloseTime());
            }
        }
    }

}