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

    @JsonProperty("name_en")
    @Column(name = "name_en")
    private String nameEn;

    @JsonProperty("name_et")
    @Column(name = "name_et")
    private String nameEt;

    private String currency;

    private BigDecimal price;

    public MenuItem() {}

    public MenuItem(Menu menu, String nameEn, String nameEt, Currency currency, BigDecimal price) {
        this.nameEn = nameEn;
        this.nameEt = nameEt;
        this.menu = menu;
        this.currency = currency.toString();
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("MenuItem (ET): ").append(nameEt).append("\n");
        sb.append("MenuItem (EN): ").append(nameEn).append("\n");
        sb.append("\nPrice: ").append(price.toString()).append(" ").append(currency);

        return sb.toString();
    }

}
