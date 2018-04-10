package com.ttu.lunchbot.spring.service;

import com.ttu.lunchbot.spring.model.FoodService;
import com.ttu.lunchbot.spring.model.OpeningTime;
import com.ttu.lunchbot.util.CalendarConverter;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.repository.FoodServiceRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceService {

    private FoodServiceRepository foodServiceRepository;

    private MenuService menuService;

    private ParseService parseService;

    public FoodServiceService(FoodServiceRepository foodServiceRepository, MenuService menuService, ParseService
            parseService) {
        this.foodServiceRepository = foodServiceRepository;
        this.menuService = menuService;
        this.parseService = parseService;
    }

    public FoodService getFoodServiceById(long foodServiceId) {
        FoodService foodService = foodServiceRepository.findOne(foodServiceId);
        updateFoodServiceOpeningTimes(foodService);
        return foodService;
    }

    public List<FoodService> getAllFoodServices() {
        List<FoodService> foodServices = foodServiceRepository.findAll();
        for (FoodService foodService : foodServices) {
            updateFoodServiceOpeningTimes(foodService);
        }
        return foodServices;
    }

    public FoodService addFoodService(FoodService foodService) {
        return foodServiceRepository.save(foodService);
    }

    public static void updateFoodServiceOpeningTimes(FoodService foodService) {
        List<OpeningTime> openingTimes = foodService.getOpeningTimes();
        for (OpeningTime openingTime : openingTimes) {
            if (LocalDate.now().getDayOfWeek().getValue() == (int) openingTime.getWeekDay()) {
                foodService.setOpenToday(openingTime.isOpen());
                foodService.setOpenTime(openingTime.getOpenTime());
                foodService.setCloseTime(openingTime.getCloseTime());
                return;
            }
        }

        foodService.setOpenToday(false);
        foodService.setOpenTime(null);
        foodService.setCloseTime(null);

        System.out.println("Appropriate week day of opening times for food service " + foodService.getId() + " for week day "
                                         + LocalDate.now().getDayOfWeek().getValue()
                                         + " was not found");
    }

    public Menu getMenuOfToday(long foodServiceId) {
        List<Menu> menuList = menuService.getMenusByFoodServiceId(foodServiceId);

        Optional<Menu> latestMenuOpt = menuList.stream().max(Comparator.comparing(Menu::getDate));
        if (menuList.size() == 0
            || latestMenuOpt.isPresent() && LocalDate.now().isAfter(latestMenuOpt.get().getDate())) {
            menuList.addAll(parseService.parseFoodServiceMenu(foodServiceId));
        }

        if (menuList.size() == 0) throw new ResourceNotFoundException("No food service with id " + foodServiceId);

        CalendarConverter calendarConverter = new CalendarConverter();

        Menu leastDaysDifferentFromNowMenu = null;
        for (Menu menu : menuList) {
            if (menu.getDate() == null) {
                System.out.println("Menu has no date");
                continue;
            }
            if (menu.getDate().equals(LocalDate.now())) {
                updateFoodServiceOpeningTimes(menu.getFoodService());
                return menu;
            }

            // If we can't find a menu for today, then find the menu with the date closest to today.
            if (leastDaysDifferentFromNowMenu == null
                || calendarConverter.daysBetweenNowAndDateOfMenu(leastDaysDifferentFromNowMenu)
                   > calendarConverter.daysBetweenNowAndDateOfMenu(menu)) {
                leastDaysDifferentFromNowMenu = menu;
            }
        }

        if (leastDaysDifferentFromNowMenu != null) {
            updateFoodServiceOpeningTimes(leastDaysDifferentFromNowMenu.getFoodService());
        }
        return leastDaysDifferentFromNowMenu;
    }

}
