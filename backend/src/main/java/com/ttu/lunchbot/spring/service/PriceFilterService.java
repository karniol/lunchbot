package com.ttu.lunchbot.spring.service;

import com.ttu.lunchbot.spring.model.FoodService;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.model.MenuItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceFilterService {

    private FoodServiceService foodServiceService;

    public PriceFilterService(FoodServiceService foodServiceService) {
        this.foodServiceService = foodServiceService;
    }

    /**
     * Get the list of today's menus which are filtered according to the max price.
     * Only menu items which are equal to or below the max price are contained in the menus.
     * @param maxPrice The maximum price of a menu item to be displayed.
     * @return         The list of filtered menus.
     */
    public List<Menu> getTodaysFilteredMenus(BigDecimal maxPrice) {
        List<Menu> filteredMenuList = new ArrayList<>();

        for (FoodService foodService : foodServiceService.getAllFoodServices()) {
            Menu todaysMenu = foodServiceService.getMenuOfToday(foodService.getId());

            List<MenuItem> filteredMenuItemList = todaysMenu.getMenuItems().stream()
                    .filter(menuItem -> menuItem.getPrice().compareTo(maxPrice) < 1)
                    .sorted(Comparator.comparing(MenuItem::getPrice))
                    .collect(Collectors.toList());

            if (filteredMenuItemList.size() == 0) continue;

            // As we don't need to show the list of menus of the filtered food service,
            // we don't need to attach the newly created menu to the food service.
            Menu filteredMenu = new Menu(todaysMenu.getId(), filteredMenuItemList, foodService,
                    todaysMenu.getDate());
            filteredMenuList.add(filteredMenu);
        }
        return filteredMenuList;
    }

}
