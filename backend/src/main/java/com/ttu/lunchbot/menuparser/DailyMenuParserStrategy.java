package com.ttu.lunchbot.menuparser;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.*;

/**
 * MenuParser strategy for parsing Daily menus.
 */
public class DailyMenuParserStrategy implements MenuParserStrategy {

    private static final String DATE_PATTERN_STRING = ".+\\s[\\d]{1,2}[.]\\s.+";
    private static final String[] MONTHS_ET = DateFormatSymbols.getInstance(Locale.forLanguageTag("et")).getMonths();

    private Calendar parseDate(String line) {
        String[] parts = line.split(" ");
        final String monthString = parts[parts.length - 1].toLowerCase();
        final String dayString = parts[parts.length - 2].split("[.,]")[0];

        int month = -1;
        for (int m = 0; m < MONTHS_ET.length; ++m) {
            if (MONTHS_ET[m].equals(monthString)) {
                month = m;
                break;
            }
        }

        final int day = Integer.parseInt(dayString);

        Calendar date = Calendar.getInstance();
        // TODO: magic month number
        date.set(date.get(Calendar.YEAR), month, day);
        return date;
    }

    @Override
    public ArrayList<Menu> parse(ArrayList<String> text) {
        // TODO: refactoring
        final String locationName = text.get(1);

        // Remove lines from beginning
        for (int i = 0; i < 2; ++i) {
            text.remove(0);
        }

        // Remove line from end
        for (int i = 0; i < 1; ++i) {
            text.remove(text.size() - 1);
        }

        final ArrayList<Menu> menus = new ArrayList<>();

        Menu menu = null;
        MenuItem menuItem = null;

        // Daily menus list items twice, in Estonian and in English, in this order
        // The first line contains a string representing the price of the item
        boolean nextLineContainsPrice = true;

        for (String line : text) {
            // Encountering a date, the method creates a new menu with the corresponding date
            if (line.matches(DATE_PATTERN_STRING)) {
                menu = new Menu(locationName, parseDate(line));
                menus.add(menu);
                continue;
            }

            if (nextLineContainsPrice) {
                menuItem = new MenuItem();

                // TODO: null is bad style
                if (null != menu) {
                    menu.addItem(menuItem);
                }

                // Split the line into parts to obtain the name of the food item and its price
                // Example: Caesar salad 4.20
                ArrayList<String> parts = new ArrayList<>(Arrays.asList(line.split(" ")));

                // Obtain: Caesar salad
                String name = String.join(" ", parts.subList(0, parts.size() - 1));

                // Obtain: 4.20
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(parts.get(parts.size() - 1)));

                menuItem.addName(Locale.forLanguageTag("et"), name);
                menuItem.addPrice(Currency.getInstance("EUR"), price);

                nextLineContainsPrice = false;
            } else {
                menuItem.addName(Locale.ENGLISH, line);
                nextLineContainsPrice = true;
            }
        }

        return menus;
    }
}
