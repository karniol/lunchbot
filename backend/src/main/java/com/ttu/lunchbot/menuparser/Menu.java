package com.ttu.lunchbot.menuparser;

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
    private final String name;

    /**
     * Every menu is associated with a date for which the stored MenuItems are relevant.
     */
    private final Calendar date;

    /**
     * A collection of MenuItems.
     */
    private final ArrayList<MenuItem> items;

    /**
     * Create a new Menu for storing MenuItems.
     * @param name Name of the Menu or the name of the restaurant this Menu is associated with.
     * @param date Date associated with this Menu. Every menu is associated with a date
     *             for which the stored MenuItems are relevant.
     */
    Menu(String name, Calendar date) {
        this.items = new ArrayList<>();
        this.name = name;
        this.date = date;
    }

    /**
     * @return Name of this Menu.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return Calendar instance including the date of the menu.
     */
    public Calendar getDate() {
        return this.date;
    }

    /**
     * @return Collection of all MenuItems in the Menu.
     */
    public ArrayList<MenuItem> getItems() {
        return this.items;
    }

    /**
     * Add a MenuItem to the Menu.
     * @param item MenuItem object
     * @see MenuItem
     */
    void addItem(MenuItem item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getName());
        sb.append(" ");
        sb.append(this.getDate().getTime());
        sb.append("\n");

        for (MenuItem item : this.getItems()) {
            sb.append(item.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
