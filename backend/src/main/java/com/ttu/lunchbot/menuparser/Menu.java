package com.ttu.lunchbot.menuparser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * todo
 */
class Menu {

    private final String locationName;
    private final Calendar date;

    /**
     *
     */
    private final ArrayList<MenuItem> items;

    Menu(String locationName, Calendar date) {
        this.locationName = locationName;
        this.date = date;
        this.items = new ArrayList<>();
    }

    String getLocationName() {
        return this.locationName;
    }

    Calendar getDate() {
        return this.date;
    }

    ArrayList<MenuItem> getItems() {
        return this.items;
    }

    void addItem(MenuItem item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getLocationName());
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
