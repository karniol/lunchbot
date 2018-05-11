package com.ttu.lunchbot.spring.controller;

import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.service.ParseService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: delete before release, endpoints are just for troubleshooting.
@RestController
@EnableAutoConfiguration
public class ParseController {

    private ParseService parseService;

    ParseController(ParseService parseService) {
        this.parseService = parseService;
    }

    @PostMapping(value= "/parse/{foodservice-id}")
    public List<Menu> parseMenu(@PathVariable("foodservice-id") long cafeId) {
        return parseService.parseFoodServiceMenu(cafeId);
    }

    @PostMapping(value="/parse/all")
    public List<Menu> parseAll() {
        return parseService.parseAllFoodServiceMenus();
    }

}
