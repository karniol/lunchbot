package com.ttu.lunchbot.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.ttu.lunchbot.spring.controller.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

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

    @JsonProperty(value = "name_en", access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "name_en")
    private String nameEn;

    @JsonProperty(value = "name_et", access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "name_et")
    private String nameEt;

    @Transient
    @JsonView(Views.FoodServiceDetails.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;

    private String currency;

    private BigDecimal price;

    private boolean vegetarian;

    public MenuItem() {
        if (price != null) price = price.setScale(2, RoundingMode.HALF_UP);
    }

    public void setPrice(BigDecimal bigDecimal) {
        price = bigDecimal.setScale(2, RoundingMode.HALF_UP);
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
