package com.ttu.lunchbot.spring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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


@Entity
@Getter
@Setter
@Table(name = "menuitems")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    private String name;

    private String currency;

    @JsonProperty("price_big_decimal")
    private BigDecimal priceBigDecimal;

    @JsonProperty("price_string")
    private String priceString;


    public MenuItem() {

    }

    public MenuItem(String name, Menu menu, Currency currency, BigDecimal priceBigDecimal) {
        this.name = name;
        this.menu = menu;
        this.currency = currency.toString();
        this.priceBigDecimal = priceBigDecimal.setScale(2, RoundingMode.HALF_UP);
        this.priceString = "€" + this.priceBigDecimal.toString();
    }

}
