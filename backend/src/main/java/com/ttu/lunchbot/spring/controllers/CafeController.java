package com.ttu.lunchbot.spring.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.ttu.lunchbot.spring.Views;
import com.ttu.lunchbot.spring.models.Cafe;
import com.ttu.lunchbot.spring.services.CafeService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class CafeController {

    private CafeService cafeService;

    public CafeController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @RequestMapping(value="/cafes/add", method= RequestMethod.POST,
            consumes = "application/json")
    public Cafe addCafe(@RequestBody Cafe cafe) {
        return cafeService.addCafe(cafe);
    }

    @RequestMapping(value="/cafes", method=RequestMethod.GET)
    @JsonView(Views.NoCafeMenus.class)
    public List<Cafe> getAllCafes() {
        return cafeService.getAllCafes();
    }

    @RequestMapping(value="/cafes/showall", method=RequestMethod.GET)
    public List<Cafe> getAllCafesAllInfo() {
        return cafeService.getAllCafes();
    }

    @RequestMapping(value = "/cafes/{id}", method=RequestMethod.GET)
    public Cafe getCafe(@PathVariable("id") long cafeId) {
        return cafeService.getCafeById(cafeId);
    }
}
