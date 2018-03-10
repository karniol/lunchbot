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

    public Menu getMenuById(long menuId) {
        return menuRepository.findOne(menuId);
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu addMenu(Menu menu) {
        Menu menuAfter = menuRepository.save(menu);
        menuItemRepository.save(menu.getMenuItems());
        return menuAfter;
    }

}
