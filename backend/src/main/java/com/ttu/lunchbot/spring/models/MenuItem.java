package com.ttu.lunchbot.spring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Currency;


@Entity
@Getter
@Setter
@Table(name = "menuitems")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public MenuItem(String name, Menu menu) {
        this.name = name;
        this.menu = menu;
    }

    public MenuItem(String name, Menu menu, Currency currency) {
        this.name = name;
        this.menu = menu;
        this.price = currency.toString();
    }

    public MenuItem(String name) {
        this.name = name;
    }

    public MenuItem() {

    }

    private String name;

    private String price;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

}
