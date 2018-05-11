package com.ttu.lunchbot.spring.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ttu.lunchbot.spring.exception.LanguageNotFoundException;
import com.ttu.lunchbot.spring.model.FoodService;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.service.FoodServiceService;
import com.ttu.lunchbot.spring.service.LocalizationService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class FoodServiceController {

    private FoodServiceService foodServiceService;

    private LocalizationService localizationService;

    public FoodServiceController(FoodServiceService foodServiceService, LocalizationService localizationService) {
        this.foodServiceService = foodServiceService;
        this.localizationService = localizationService;
    }

    @GetMapping(value="/foodservices")
    @JsonView(Views.FoodServiceDetails.class)
    public List<FoodService> getAllFoodServices(@RequestParam Map<String, String> requestParams) throws LanguageNotFoundException {
        Locale locale = localizationService.getLocaleFromParams(requestParams);

        List<FoodService> foodServices = foodServiceService.getAllFoodServices();
        localizationService.localizeFoodServices(foodServices, locale);
        return foodServices;
    }


    /**
     * Shows /foodservices properties plus menus
     */
    //@GetMapping(value="/foodservices/allinfo")
    //public List<FoodService> getAllFoodServicesAllInfo() {
    //    return foodServiceService.getAllFoodServices();
    //}

    //@GetMapping(value = "/foodservices/{id}")
    //public FoodService getFoodService(@PathVariable("id") long foodServiceId) {
    //    return foodServiceService.getFoodServiceById(foodServiceId);
    //}

    @GetMapping(value = "/foodservices/{foodservice-id}/menus/today")
    public Menu getTodayMenuByFoodServiceId(
            @PathVariable("foodservice-id") long foodServiceId,
            @RequestParam Map<String, String> requestParams
    ) throws LanguageNotFoundException {
        Locale locale = localizationService.getLocaleFromParams(requestParams);
        Menu menu = foodServiceService.getMenuOfToday(foodServiceId);
        localizationService.localizeMenuRecursively(menu, locale);
        return menu;
    }

    //@PostMapping(value="/foodservices", consumes = "application/json")
    //public FoodService addFoodService(@RequestBody FoodService foodService) {
    //    return foodServiceService.addFoodService(foodService);
    //}

}
