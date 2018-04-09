package com.ttu.lunchbot.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;


@Entity
@Getter
@Setter
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @JsonProperty("name_et")
    @Column(name = "name_et")
    private String nameET;

    @JsonProperty("name_en")
    @Column(name = "name_en")
    private String nameEN;

    private BigDecimal price;

    private String currency;

    public MenuItem() {

    }

    public MenuItem(String nameET, String nameEN, Menu menu, Currency currency, BigDecimal price) {
        this.nameET = nameET;
        this.nameEN = nameEN;
        this.menu = menu;
        this.currency = currency.toString();
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("MenuItem (ET): ").append(nameET).append("\n");
        sb.append("MenuItem (EN): ").append(nameEN).append("\n");
        sb.append("\nPrice: ").append(price.toString()).append(" ").append(currency);

        return sb.toString();
    }

}
