package com.ttu.lunchbot.spring.service;

import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.repository.MenuItemRepository;
import com.ttu.lunchbot.spring.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private MenuRepository menuRepository;

    private MenuItemRepository menuItemRepository;

    public MenuService(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public Menu getMenuByMenuId(long menuId) {
        Menu menu = menuRepository.findOne(menuId);
        FoodServiceService.updateFoodServiceOpeningTimes(menu.getFoodService());
        return menu;
    }

    public List<Menu> getMenusByFoodServiceId(long foodServiceId) {
        List<Menu> menus = menuRepository.findByFoodServiceId(foodServiceId);
        menus.stream().map(Menu::getFoodService).forEach(FoodServiceService::updateFoodServiceOpeningTimes);
        return menus;
    }

    public List<Menu> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        menus.stream().map(Menu::getFoodService).forEach(FoodServiceService::updateFoodServiceOpeningTimes);
        return menus;
    }

    public Menu addMenu(Menu menu) {
        Menu menuAfter = menuRepository.save(menu);
        menuItemRepository.save(menu.getMenuItems());
        return menuAfter;
    }

}
