package com.ttu.lunchbot.spring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.ttu.lunchbot.spring.controller.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "cafes")
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.NoCafeMenus.class)
    private long id;

    @OneToMany(mappedBy = "cafe")
    @JsonManagedReference
    private List<Menu> menus = new ArrayList<>();

    @JsonView(Views.NoCafeMenus.class)
    String name;

    String menuURL;

    public Cafe() {
    }

    public Cafe(String name) {
        this.name = name;
    }
}
