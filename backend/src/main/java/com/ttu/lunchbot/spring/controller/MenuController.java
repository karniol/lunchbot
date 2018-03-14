package com.ttu.lunchbot.spring.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.service.MenuService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping(value="/menus")
    @JsonView(Views.NoMenuItems.class)
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    // Shows /menus properties and menu items
    @GetMapping(value="/menus/allinfo")
    public List<Menu> getAllMenusAllInfo() {
        return menuService.getAllMenus();
    }

    @GetMapping(value = "/menus/{menu-id}")
    public Menu getMenuByMenuId(@PathVariable("menu-id") long menuId) {
        return menuService.getMenuById(menuId);
    }

    @PostMapping(value="/menus/add", consumes = "application/json")
    public Menu addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

}
