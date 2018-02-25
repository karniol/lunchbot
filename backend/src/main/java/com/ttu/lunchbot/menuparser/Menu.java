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

    void addItem(MenuItem item) {
        this.items.add(item);
    }
}
