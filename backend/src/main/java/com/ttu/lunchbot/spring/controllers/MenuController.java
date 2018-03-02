package com.ttu.lunchbot.spring.controllers;

import java.util.List;

import com.ttu.lunchbot.spring.Menu;
import com.ttu.lunchbot.spring.MenuService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class MenuController {

    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(value="/menus/add", method=RequestMethod.POST,
            consumes = "application/json")
    public Menu addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    @RequestMapping(value="/menus", method=RequestMethod.GET)
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @RequestMapping(value = "/menus/{id}", method=RequestMethod.GET)
    public Menu getMenu(@PathVariable("id") long menuId) {
        return menuService.getMenuById(menuId);
    }
}