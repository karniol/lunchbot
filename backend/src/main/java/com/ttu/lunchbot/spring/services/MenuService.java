package com.ttu.lunchbot.spring.services;

import com.ttu.lunchbot.spring.models.Menu;
import com.ttu.lunchbot.spring.repositories.MenuItemRepository;
import com.ttu.lunchbot.spring.repositories.MenuRepository;
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

    public Menu addMenu(Menu menu) {
        Menu menuAfter = menuRepository.save(menu);
        menuItemRepository.save(menu.getMenuItems());
        return menuAfter;
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(long menuId) {
        return menuRepository.findOne(menuId);
    }

}
