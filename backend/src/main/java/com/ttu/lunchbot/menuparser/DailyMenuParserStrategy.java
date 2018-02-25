package com.ttu.lunchbot.menuparser;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.*;

/**
 * MenuParser strategy for parsing Daily menus.
 */
public class DailyMenuParserStrategy implements MenuParserStrategy {

    private static final String DATE_PATTERN_STRING = ".+\\s[\\d]{1,2}[.]\\s.+";
    private static final String[] MONTH_NAMES_ET = DateFormatSymbols.getInstance(Locale.forLanguageTag("et")).getMonths();
    private static final String TIME_ZONE_ID = "EET";

    private final ArrayList<Menu> parsedMenus;
    private Menu currentMenu;
    private MenuItem currentMenuItem;

    DailyMenuParserStrategy() {
        this.parsedMenus = new ArrayList<>();
        this.currentMenuItem = null;
        this.currentMenu = null;
    }

    private Calendar parseDate(String line) {
        String[] parts = line.split(" ");
        final String monthString = parts[parts.length - 1].toLowerCase();
        final String dayString = parts[parts.length - 2].split("[.,]")[0];

        int month = -1;
        for (int m = 0; m < MONTH_NAMES_ET.length; ++m) {
            if (MONTH_NAMES_ET[m].equals(monthString)) {
                month = m;
                break;
            }
        }

        final int day = Integer.parseInt(dayString);

        Calendar date = Calendar.getInstance();

        // TODO: magic month number
        date.set(date.get(Calendar.YEAR), month, day, 0, 0, 0);
        date.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ID));

        return date;
    }

    private void addLineContainingPriceToCurrentMenuItem(String line) {
        // Split the line into parts to obtain the name of the food item and its price
        // Example: Caesar salad 4.20
        ArrayList<String> parts = new ArrayList<>(Arrays.asList(line.split(" ")));

        // Obtain: Caesar salad
        String name = String.join(" ", parts.subList(0, parts.size() - 1));

        // Obtain: 4.20
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(parts.get(parts.size() - 1)));

        this.currentMenuItem.addName(Locale.forLanguageTag("et"), name);
        this.currentMenuItem.addPrice(Currency.getInstance("EUR"), price);
    }

    // TODO: refactoring
    @Override
    public ArrayList<Menu> parse(ArrayList<String> text) {
        this.parsedMenus.clear();

        final String locationName = text.get(1);

        // Remove lines from beginning
        for (int i = 0; i < 2; ++i) {
            text.remove(0);
        }

        // Remove line from end
        for (int i = 0; i < 1; ++i) {
            text.remove(text.size() - 1);
        }

        // Daily menus list items twice, in Estonian and in English, in this order
        // The first line contains a string representing the price of the item
        boolean nextLineContainsPrice = true;
        for (String line : text) {
            // Encountering a date, the method creates a new menu with the corresponding date
            if (line.matches(DATE_PATTERN_STRING)) {
                this.currentMenu = new Menu(locationName, parseDate(line));
                this.parsedMenus.add(this.currentMenu);
                continue;
            }

            if (nextLineContainsPrice) {
                this.currentMenuItem = new MenuItem();

                // TODO: null is bad style
                if (null != this.currentMenu) {
                    this.currentMenu.addItem(this.currentMenuItem);
                }

                this.addLineContainingPriceToCurrentMenuItem(line);
                nextLineContainsPrice = false;
            } else {
                this.currentMenuItem.addName(Locale.ENGLISH, line);
                nextLineContainsPrice = true;
            }
        }

        return this.parsedMenus;
    }
}
