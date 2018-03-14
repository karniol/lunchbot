package com.ttu.lunchbot.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.ttu.lunchbot.util.CalendarConverter;
import com.ttu.lunchbot.spring.controller.Views;
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
    @JsonView(Views.NoMenuItems.class)
    private long id;

    @OneToMany(mappedBy = "menu")
    @JsonManagedReference
    private List<MenuItem> menuItems = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "food_service_id", nullable = false)
    private FoodService foodService;

    //@JsonView(Views.NoMenuItems.class)
    //private String name;

    @JsonView(Views.NoMenuItems.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    public Menu() {

    }

    public Menu(String name, FoodService foodService) {
        this.foodService = foodService;
    }

    public Menu(String name, List<MenuItem> items) {
        this.menuItems = items;
    }

    public Menu(String name, List<MenuItem> items, FoodService foodService) {
        this.menuItems = items;
        this.foodService = foodService;
    }

    public Menu(String name, Calendar calendar, List<MenuItem> items, FoodService foodService) {
        this.menuItems = items;
        this.foodService = foodService;
        this.date = new CalendarConverter().calendarToLocalDate(calendar);
    }

    public Menu(String name, Calendar calendar, FoodService foodService) {
        this.foodService = foodService;
        this.date = new CalendarConverter().calendarToLocalDate(calendar);
    }

}
