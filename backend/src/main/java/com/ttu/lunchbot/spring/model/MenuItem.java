package com.ttu.lunchbot.spring.model;

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
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    private String name_et;

    private String name_en;

    @JsonProperty("price_big_decimal")
    private BigDecimal priceBigDecimal;

    private String currency;

    public MenuItem() {

    }

    public MenuItem(Menu menu, Currency currency, BigDecimal priceBigDecimal) {
        this.menu = menu;
        this.currency = currency.toString();
        this.priceBigDecimal = priceBigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

}
