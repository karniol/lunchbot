package com.ttu.lunchbot.spring.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ttu.lunchbot.spring.model.FoodService;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.service.FoodServiceService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class FoodServiceController {

    private FoodServiceService foodServiceService;

    public FoodServiceController(FoodServiceService foodServiceService) {
        this.foodServiceService = foodServiceService;
    }

    @GetMapping(value="/foodservices")
    @JsonView(Views.NoFoodServiceMenus.class)
    public List<FoodService> getAllCafes() {
        return foodServiceService.getAllFoodServices();
    }

    // Shows /cafes properties plus menus
    @GetMapping(value="/foodservices/allinfo")
    public List<FoodService> getAllCafesAllInfo() {
        return foodServiceService.getAllFoodServices();
    }

    @GetMapping(value = "/foodservices/{id}")
    public FoodService getCafe(@PathVariable("id") long cafeId) {
        return foodServiceService.getFoodServiceById(cafeId);
    }

    // TODO: Configure CORS so Aurelia can access data
    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping(value = "/foodservices/{cafe-id}/menus/today")
    public Menu getTodayMenuByCafeId(@PathVariable("cafe-id") long cafeId) {
        return foodServiceService.getMenuOfToday(cafeId);
    }

    @PostMapping(value="/foodservices", consumes = "application/json")
    public FoodService addCafe(@RequestBody FoodService foodService) {
        return foodServiceService.addFoodService(foodService);
    }

}
