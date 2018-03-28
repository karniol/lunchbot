package com.ttu.lunchbot.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Data object for storing MenuItems.
 * @see MenuItem
 */
public class Menu {

    /**
     * Name of the Menu or the name of the restaurant this Menu is associated with.
     */
    @Getter
    private final String name;

    /**
     * Every menu is associated with a date for which the stored MenuItems are relevant.
     */
    @Getter
    private final Calendar date;

    /**
     * A collection of MenuItems.
     */
    @Getter
    private final ArrayList<MenuItem> items;

    /**
     * Create a new Menu for storing MenuItems.
     * @param name Name of the Menu or the name of the restaurant this Menu is associated with.
     * @param date Date associated with this Menu. Every menu is associated with a date
     *             for which the stored MenuItems are relevant.
     */
    public Menu(String name, Calendar date) {
        this.items = new ArrayList<>();
        this.name = name;
        this.date = date;
    }

    /**
     * Add a MenuItem to the Menu.
     * @param item MenuItem object
     * @see MenuItem
     */
    public void addItem(MenuItem item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Menu: ");
        sb.append(this.getName());
        sb.append(' ');
        sb.append(this.getDate().getTime());
        sb.append('\n');

        for (MenuItem item : this.getItems()) {
            sb.append(item.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
