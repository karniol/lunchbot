package com.ttu.lunchbot.spring.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ttu.lunchbot.spring.exception.LanguageNotFoundException;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.service.LocalizationService;
import com.ttu.lunchbot.spring.service.PriceFilterService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class MenuController {

    private PriceFilterService priceFilterService;

    private LocalizationService localizationService;

    public MenuController(PriceFilterService priceFilterService, LocalizationService localizationService) {
        this.priceFilterService = priceFilterService;
        this.localizationService = localizationService;
    }

    @GetMapping(value = "/menus/filter/{max-price}")
    public List<Menu> getTodayMenusFilteredByPrice(
            @PathVariable("max-price") String maxPrice,
            @RequestParam Map<String, String> requestParams
    ) throws LanguageNotFoundException {
        Locale locale = localizationService.getLocaleFromParams(requestParams);

        maxPrice = maxPrice.replace(",", ".");
        List<Menu> menus = priceFilterService.getTodaysFilteredMenus(new BigDecimal(maxPrice));
        localizationService.localizeMenusRecursively(menus, locale);
        return menus;
    }

    @GetMapping(value = "/menus/vege")
    public List<Menu> getTodayMenusVegeOnly(@RequestParam Map<String, String> requestParams) throws LanguageNotFoundException {
        Locale locale = localizationService.getLocaleFromParams(requestParams);

        List<Menu> menus = priceFilterService.getTodaysVegetarianMenus();
        localizationService.localizeMenusRecursively(menus, locale);
        return menus;
    }

}
