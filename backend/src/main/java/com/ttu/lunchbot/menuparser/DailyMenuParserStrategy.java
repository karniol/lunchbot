package com.ttu.lunchbot.menuparser;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DailyMenuParserStrategy implements MenuParserStrategy {

    private static final String DATE_PATTERN_STRING = ".+\\s[\\d]{1,2}[.]\\s.+";
    private static final String PRICE_PATTERN_STRING = "[\\d]+[.,][\\d]+";
    private static final Pattern PRICE_PATTERN = Pattern.compile(PRICE_PATTERN_STRING);
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
        date.set(date.get(Calendar.YEAR), month, day);
        return date;
    }

    @Override
    public Menu parse(ArrayList<String> text) {
        final String locationName = text.get(1);

        // Remove lines from beginning
        for (int i = 0; i < 2; ++i) {
            text.remove(0);
        }

        // Remove lines from end
        for (int i = 0; i < 1; ++i) {
            text.remove(text.size() - 1);
        }

        Menu menu = null;
        MenuItem menuItem;

        boolean nextItemHasPrice = true;

        for (String line : text) {
            if (line.matches(DATE_PATTERN_STRING)) {
                menu = new Menu(locationName, parseDate(line));
                continue;
            }

            menuItem = new MenuItem();

            if (nextItemHasPrice) {

                ArrayList<String> parts = new ArrayList<>(Arrays.asList(line.split(" ")));
                String name = String.join(" ", parts.subList(0, parts.size() - 1));
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(parts.get(parts.size() - 1)));

                menuItem.addName(Locale.forLanguageTag("et"), name);
                menuItem.addPrice(Currency.getInstance("EUR"), price);

                nextItemHasPrice = false;
            } else {
                menuItem.addName(Locale.ENGLISH, line);
                nextItemHasPrice = true;

                menu.addItem(menuItem);
            }
        }

        return menu;
    }

    public static void main(String[] args) {
        System.out.println(new DailyMenuParserStrategy().parseDate("Esmaspäev 17. veebruar").getTime());
    }
}
