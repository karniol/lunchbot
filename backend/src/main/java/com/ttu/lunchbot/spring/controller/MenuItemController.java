package com.ttu.lunchbot.spring.controller;

import com.ttu.lunchbot.spring.model.MenuItem;
import com.ttu.lunchbot.spring.service.MenuItemService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class MenuItemController {

    private MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping(value="/menuitems")
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @GetMapping(value = "/menuitems/{id}")
    public MenuItem getMenuItems(@PathVariable("id") long menuItemId) {
        return menuItemService.getMenuItemById(menuItemId);
    }

    @PostMapping(value="/menuitems", consumes = "application/json")
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem) {
        return menuItemService.addMenuItem(menuItem);
    }

}
