package com.ttu.lunchbot.spring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ttu.lunchbot.util.CalendarConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "food_service_id", nullable = false)
    @JsonIgnoreProperties({"menus", "open_times_all"})
    @JsonProperty("food_service")
    private FoodService foodService;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "DATE")
    private LocalDate date;

    @OneToMany(mappedBy = "menu")
    @JsonManagedReference
    @JsonProperty("menu_items")
    private List<MenuItem> menuItems = new ArrayList<>();

    public Menu() {}

    public Menu(Calendar calendar) {
        this.date = new CalendarConverter().toLocalDate(calendar);
    }

    public Menu(long id, FoodService foodService, LocalDate date, List<MenuItem> menuItems) {
        this.id = id;
        this.menuItems = menuItems;
        this.foodService = foodService;
        this.date = date;
    }

    public void addItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Menu: ");
        sb.append(date);
        sb.append('\n');

        for (MenuItem item : menuItems) {
            sb.append(item.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

}
