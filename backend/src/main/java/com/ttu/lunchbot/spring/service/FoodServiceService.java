package com.ttu.lunchbot.spring.service;

import com.ttu.lunchbot.spring.model.FoodService;
import com.ttu.lunchbot.util.CalendarConverter;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.repository.FoodServiceRepository;
import com.ttu.lunchbot.spring.repository.MenuRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FoodServiceService {

    private FoodServiceRepository foodServiceRepository;

    private MenuRepository menuRepository;

    private ParseService parseService;

    public FoodServiceService(FoodServiceRepository foodServiceRepository, MenuRepository menuRepository, ParseService parseService) {
        this.foodServiceRepository = foodServiceRepository;
        this.menuRepository = menuRepository;
        this.parseService = parseService;
    }

    public FoodService getFoodServiceById(long foodServiceId) {
        return foodServiceRepository.findOne(foodServiceId);
    }

    public List<FoodService> getAllFoodServices() {
        return foodServiceRepository.findAll();
    }

    public FoodService addFoodService(FoodService foodService) {
        return foodServiceRepository.save(foodService);
    }

    public Menu getMenuOfToday(long foodServiceId) {
        List<Menu> menuList = menuRepository.findByFoodServiceId(foodServiceId);
        if (menuList.size() == 0
            || menuList.get(menuList.size() - 1).getDate().isBefore(LocalDate.now())) {
            menuList.addAll(parseService.parseFoodServiceMenu(foodServiceId));
        }
        if (menuList.size() == 0) throw new ResourceNotFoundException("No food service with the ID exists.");

        CalendarConverter calendarConverter = new CalendarConverter();

        Menu leastDaysDifferentFromNowMenu = null;
        for (Menu menu : menuList) {
            if (menu.getDate() == null) {
                System.out.println("Menu has no date!");
                continue;
            }
            if (menu.getDate().equals(LocalDate.now())) return menu;

            // If we can't find a menu for today, then find the menu with the date closest to today.
            if (leastDaysDifferentFromNowMenu == null
                || calendarConverter.getAbsDaysFromNow(leastDaysDifferentFromNowMenu)
                   > calendarConverter.getAbsDaysFromNow(menu)) {
                leastDaysDifferentFromNowMenu = menu;
            }
        }

        return leastDaysDifferentFromNowMenu;
    }

}
