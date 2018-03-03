package com.ttu.lunchbot.spring;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Getter
@Setter
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    public Menu(String name) {
        this.name = name;
    }

    public Menu() {

    }

    //Calendar date;

    @OneToMany(mappedBy = "menu")
    @JsonManagedReference
    private List<MenuItem> menuItems = new ArrayList<>();

}
