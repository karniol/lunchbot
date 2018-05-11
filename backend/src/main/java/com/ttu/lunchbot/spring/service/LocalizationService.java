package com.ttu.lunchbot.spring.service;

import com.ttu.lunchbot.spring.exception.LanguageNotFoundException;
import com.ttu.lunchbot.spring.model.FoodService;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.model.MenuItem;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

@Service
public class LocalizationService {

    /**
     * Language identifiers according to ISO 639-1 (https://www.loc.gov/standards/iso639-2/php/code_list.php).
     */
    private Map<String, java.util.Locale> langLocales = new HashMap<String, Locale>() {{
        put("en", Locale.ENGLISH);
        put("et", com.ttu.lunchbot.util.Locale.ESTONIAN);
    }};

    public java.util.Locale getLocaleFromParams(Map<String, String> requestParams) throws LanguageNotFoundException {
        if (!requestParams.containsKey("lang")) throw new LanguageNotFoundException("Parameter lang not found");

        Locale locale = langLocales.getOrDefault(requestParams.get("lang").toLowerCase(), null);

        if (locale == null) throw new LanguageNotFoundException("Language not found!");

        return locale;
    }

    public void localizeFoodServicesRecursively(List<FoodService> foodServices, Locale locale) {
        for (FoodService foodService : foodServices) {
            localizeFoodServiceRecursively(foodService, locale);
        }
    }

    public void localizeFoodServiceRecursively(FoodService foodService, Locale locale) {
        localizeFoodService(foodService, locale);
        foodService.getMenus().forEach(menu -> localizeMenu(menu, locale));
    }

    public void localizeMenusRecursively(List<Menu> menus, Locale locale) {
        menus.forEach(menu -> localizeMenuRecursively(menu, locale));
    }

    public void localizeMenuRecursively(Menu menu, Locale locale) {
        localizeFoodService(menu.getFoodService(), locale);
        menu.getMenuItems().forEach(menuItem -> localizeMenuItem(menuItem, locale));
    }

    public void localizeFoodServices(List<FoodService> foodServices, Locale locale) {
        foodServices.forEach(foodService -> localizeFoodService(foodService, locale));
    }

    public void localizeFoodService(FoodService foodService, Locale locale) {
        if (locale.equals(langLocales.get("en"))) {
            foodService.setName(foodService.getNameEn());
        } else if (locale.equals(langLocales.get("et"))) {
            foodService.setName(foodService.getNameEt());
        }
    }

    public void localizeMenu(Menu menu, Locale locale) {
        menu.getMenuItems().forEach(menuItem -> localizeMenuItem(menuItem, locale));
    }

    public void localizeMenuItem(MenuItem menuItem, Locale locale) {
        if (locale.equals(langLocales.get("en"))) {
            menuItem.setName(menuItem.getNameEn());
        } else if (locale.equals(langLocales.get("et"))) {
            menuItem.setName(menuItem.getNameEt());
        }
    }
}
