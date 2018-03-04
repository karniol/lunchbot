package com.ttu.lunchbot.spring.controllers;

import com.ttu.lunchbot.spring.models.Menu;
import com.ttu.lunchbot.spring.services.ParseService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class ParseController {

    private ParseService parseService;

    ParseController(ParseService parseService) {
        this.parseService = parseService;
    }

    @RequestMapping(value="/parse/{cafe-id}")
    public List<Menu> parseMenu(@PathVariable("cafe-id") long cafeId) {
        return parseService.parseCafeMenu(cafeId);
    }

    @RequestMapping(value="/parse/all")
    public List<Menu> parseAll() {
        return parseService.parseAllCafeMenus();
    }
}
