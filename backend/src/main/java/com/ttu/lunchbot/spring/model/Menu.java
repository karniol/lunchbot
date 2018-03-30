package com.ttu.lunchbot.spring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ttu.lunchbot.util.CalendarConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "menu")
    @JsonManagedReference
    @JsonProperty("menu_items")
    private List<MenuItem> menuItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "food_service_id", nullable = false)
    @JsonIgnoreProperties("menus")
    @JsonProperty("food_service")
    private FoodService foodService;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public Menu() {

    }

    public Menu(FoodService foodService) {
        this.foodService = foodService;
    }

    public Menu(List<MenuItem> items) {
        this.menuItems = items;
    }

    public Menu(List<MenuItem> items, FoodService foodService) {
        this.menuItems = items;
        this.foodService = foodService;
    }

    public Menu(Calendar calendar, List<MenuItem> items, FoodService foodService) {
        this.menuItems = items;
        this.foodService = foodService;
        this.date = new CalendarConverter().toLocalDate(calendar);
    }

    public Menu(Calendar calendar, FoodService foodService) {
        this.foodService = foodService;
        this.date = new CalendarConverter().toLocalDate(calendar);
    }

    public Menu(long id, List<MenuItem> menuItems, FoodService foodService, LocalDate date) {
        this.id = id;
        this.menuItems = menuItems;
        this.foodService = foodService;
        this.date = date;
    }

}
