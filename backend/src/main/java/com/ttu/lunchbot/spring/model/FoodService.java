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
    @JsonProperty("name_en")
    @Column(name = "name_en")
    private String nameEN;

    @JsonView(Views.FoodServiceDetails.class)
    @JsonProperty("name_et")
    @Column(name = "name_et")
    private String nameET;

    @JsonProperty("menu_url")
    @Column(name = "menu_url")
    @JsonView(Views.FoodServiceDetails.class)
    private String menuURL;

    @JsonView(Views.FoodServiceDetails.class)
    private String website;

    @JsonView(Views.FoodServiceDetails.class)
    private String lon;

    @JsonView(Views.FoodServiceDetails.class)
    private String lat;

    public FoodService() {
    }

    public FoodService(String nameEN, String nameET) {
        this.nameEN = nameEN;
        this.nameET = nameET;
    }
}
