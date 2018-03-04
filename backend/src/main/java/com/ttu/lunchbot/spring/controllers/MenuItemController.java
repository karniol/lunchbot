package com.ttu.lunchbot.spring.controllers;

import com.ttu.lunchbot.spring.models.MenuItem;
import com.ttu.lunchbot.spring.services.MenuItemService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value="/menuitems", method=RequestMethod.GET)
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @RequestMapping(value = "/menuitems/{id}", method=RequestMethod.GET)
    public MenuItem getMenuItems(@PathVariable("id") long menuItemId) {
        return menuItemService.getMenuItemById(menuItemId);
    }

    @RequestMapping(value="/menuitems/add", method=RequestMethod.POST,
            consumes = "application/json")
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem) {
        return menuItemService.addMenuItem(menuItem);
    }

    // For checking if intellij is running the correct version of this
    // spring application, as it seems to bug on my laptop
    // TODO remove function when laptop is fixed
    @RequestMapping(value="/check1")
    public String check() {
        return "Check one!";
    }

}