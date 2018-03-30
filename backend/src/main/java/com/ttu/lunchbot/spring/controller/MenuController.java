package com.ttu.lunchbot.spring.controller;

import java.math.BigDecimal;
import java.util.List;

import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.service.MenuService;
import com.ttu.lunchbot.spring.service.PriceFilterService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class MenuController {

    private MenuService menuService;

    private PriceFilterService priceFilterService;

    public MenuController(MenuService menuService, PriceFilterService priceFilterService) {
        this.menuService = menuService;
        this.priceFilterService = priceFilterService;
    }

    @GetMapping(value="/menus")
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

    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping(value = "/menus/filter/{max-price}")
    public List<Menu> getTodayMenusFilteredByPrice(@PathVariable("max-price") String maxPrice) {
        maxPrice = maxPrice.replace(",", ".");
        return priceFilterService.getTodaysFilteredMenus(new BigDecimal(maxPrice));
    }

}
