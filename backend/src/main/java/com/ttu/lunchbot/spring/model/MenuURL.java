package com.ttu.lunchbot.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "menu_urls")
public class MenuURL {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JsonProperty("food_service")
    @JsonIgnoreProperties("menu_urls")
    @JoinColumn(name = "food_service_id", nullable = false, foreignKey = @ForeignKey(name = "menu_url_fk"))
    private FoodService foodService;

    private String url;
}
