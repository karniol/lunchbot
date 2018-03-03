package com.ttu.lunchbot.spring.models;

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
@Setter
@Getter
@Table(name = "cafes")
public class Cafe {

    public Cafe(String name) {
        this.name = name;
    }

    public Cafe() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String name;

    String menuURL;

    @OneToMany(mappedBy = "cafe")
    @JsonManagedReference
    private List<Menu> menus = new ArrayList<>();
}
