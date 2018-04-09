package com.ttu.lunchbot.spring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.ttu.lunchbot.spring.controller.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalTime;
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

    @OneToMany(mappedBy = "foodService")
    @JsonIgnoreProperties("food_service")
    @JsonProperty("open_times_all")
    private List<OpeningTime> openingTimes = new ArrayList<>();

    @Transient
    @JsonView(Views.FoodServiceDetails.class)
    @JsonProperty("open_today")
    private boolean openToday;

    @Transient
    @JsonView(Views.FoodServiceDetails.class)
    @JsonProperty("open_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;

    @Transient
    @JsonView(Views.FoodServiceDetails.class)
    @JsonProperty("close_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    @JsonView(Views.FoodServiceDetails.class)
    @JsonProperty("name_en")
    @Column(name = "name_en")
    private String nameEn;

    @JsonView(Views.FoodServiceDetails.class)
    @JsonProperty("name_et")
    @Column(name = "name_et")
    private String nameEt;

    @JsonProperty("menu_url")
    @Column(name = "menu_url")
    @JsonView(Views.FoodServiceDetails.class)
    private String menuUrl;

    @ManyToOne
    @JsonIgnoreProperties("food_services")
    @JsonProperty("parser")
    @JoinColumn(name = "parser_id", nullable = false,
            foreignKey = @ForeignKey(name = "parser_fk"))
    private Parser parser;

    @JsonView(Views.FoodServiceDetails.class)
    private String website;

    @JsonView(Views.FoodServiceDetails.class)
    private String lon;

    @JsonView(Views.FoodServiceDetails.class)
    private String lat;

    public FoodService() {}

    public FoodService(String nameEn, String nameEt) {
        this.nameEn = nameEn;
        this.nameEt = nameEt;
    }
}
