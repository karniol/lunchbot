package com.ttu.lunchbot.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.ttu.lunchbot.spring.controller.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "food_services")
public class FoodService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.FoodServiceDetails.class)
    private long id;

    @OneToMany(mappedBy = "foodService")
    @JsonIgnoreProperties("food_service")
    private List<Menu> menus = new ArrayList<>();

    @JsonView(Views.FoodServiceDetails.class)
    String name;

    @JsonProperty("menu_url")
    @Column(name = "menu_url")
    @JsonView(Views.FoodServiceDetails.class)
    String menuURL;

    @JsonView(Views.FoodServiceDetails.class)
    String website;

    @JsonView(Views.FoodServiceDetails.class)
    String lon;

    @JsonView(Views.FoodServiceDetails.class)
    String lat;

    public FoodService() {
    }

    /**
     * Create a copy of the foodService except leave the menu list empty.
     * @param foodService
     */
    public FoodService(FoodService foodService) {
        this.id = foodService.id;
        this.name = foodService.name;
        this.menuURL = foodService.menuURL;
        this.website = foodService.website;
        this.lon = foodService.lon;
        this.lat = foodService.lat;
    }

    public FoodService(String name) {
        this.name = name;
    }
}
