package com.ttu.lunchbot.parser.menu.strategy.baltic;

import com.ttu.lunchbot.parser.menu.MenuParser;
import com.ttu.lunchbot.parser.menu.MenuParserException;
import com.ttu.lunchbot.parser.menu.strategy.MenuParserStrategy;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.model.MenuItem;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.*;

/**
 * MenuParser strategy for parsing Baltic Restaurants' (Daily) menus.
 * @see MenuParser
 */
public class BalticRestaurantMenuParserStrategy implements MenuParserStrategy {

    private static final String PARSER_TYPE = "PDF";

    private static final String PARSER_NAME = "BALTIC";

    /**
     * Regular expression pattern for matching lines in the menu text that contain a date.
     */
    private static final String DATE_PATTERN = ".+\\s[\\d]{1,2}[.]\\s.+";

    /**
     * Array of month names in Estonian, in order, from January (index 0) to December (index 11).
     */
    private static final String[] MONTH_NAMES_ET = DateFormatSymbols.getInstance(java.util.Locale.forLanguageTag("et")).getMonths();

    /**
     * Collection of parsed Menus. A collection is used in case the menu text (input to
     * the parser) contains more than one Menu, since one Menu corresponds to one date.
     * @see Menu
     */
    private final ArrayList<Menu> parsedMenus;

    /**
     * Current Menu that is being updated and written into by the parser.
     */
    private Menu currentMenu;

    /**
     * Current MenuItem that is being updated and written into by the parser.
     * MenuItems are added into the currently updated Menu.
     */
    private MenuItem currentMenuItem;

    /**
     * Create and initialize a new parser strategy.
     */
    public BalticRestaurantMenuParserStrategy() {
        this.parsedMenus = new ArrayList<>();
        this.currentMenuItem = null;
        this.currentMenu = null;
    }

    /**
     * Parse a line containing a date into a Calendar instance.
     * The current year is used, based on the system time.
     * @param line Line containing a date in Estonian: month name and the day of month.
     * @return Calendar instance containing the parsed date.
     */
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
        date.setTimeZone(com.ttu.lunchbot.util.TimeZone.TALLINN);

        return date;
    }

    /**
     * Parse a line containing the name of the menu item in Estonian and its price in Euros into
     * the currently updated MenuItem.
     * @param line Line containing the name of the menu item in Estonian and its price in Euros.
     * @see MenuItem
     */
    private void addLineContainingPriceToCurrentMenuItem(String line) {
        // Split the line into parts to obtain the name of the food item and its price
        // Example: Chicken - peach - cheese chili 4.20
        List<String> parts = new ArrayList<>(Arrays.asList(line.split(" ")));

        // Obtain: Chicken-peach-cheese chili
        String name = String.join(" ", parts.subList(0, parts.size() - 1));
        name = name.replace(" - ", "-");
        this.currentMenuItem.setNameEt(name);

        // Obtain: 4.20
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(parts.get(parts.size() - 1)));
        this.currentMenuItem.setPrice(price);
    }

    @Override
    public ArrayList<Menu> parse(String textString) {
        List<String> lines = new ArrayList<>(Arrays.asList(textString.split("\n")));

        // Remove all strings from the list that are empty after trimming
        lines.replaceAll(String::trim);
        lines.removeAll(Arrays.asList(null, ""));

        if (lines.size() == 0) throw new MenuParserException("Argument list is empty, returning empty menu list");
        this.parsedMenus.clear();

        // The name of the FoodService is on the second line
        final String foodServiceName = lines.get(1);

        // Remove two first lines and last line of the file to begin processing menu texts
        lines = lines.subList(2, lines.size());

        // Baltic Restaurants' menus list items twice, first in Estonian and then in English
        // The first line also contains a string representing the price of the item in Euros
        boolean lineContainsPrice = true;
        // Set to false if there is an error parsing the menu
        // The menu that produced a parsing error will be removed from the list
        boolean menuOk = true;

        for (String line : lines) {
            // When encountering a date, create a new menu with the corresponding date
            if (line.matches(DATE_PATTERN)) {
                this.currentMenu = new Menu(parseDate(line));
                parsedMenus.add(this.currentMenu);
                menuOk = true;
                continue;
            }

            if (!menuOk) continue;

            if (lineContainsPrice) {
                this.currentMenuItem = new MenuItem();
                this.currentMenuItem.setCurrency("EUR");
                this.currentMenu.addItem(this.currentMenuItem);
                this.currentMenuItem.setMenu(currentMenu);

                // Hack to remove Menus for dates that did not contain MenuItems with the correct price format
                // Currently used to detect special cases (e.g. holidays when the FoodService is closed)
                try {
                    addLineContainingPriceToCurrentMenuItem(line);
                } catch (NumberFormatException e) {
                    parsedMenus.remove(this.currentMenu);
                    menuOk = false;
                    continue;
                }

                lineContainsPrice = false;
            } else {
                this.currentMenuItem.setNameEn(line);
                lineContainsPrice = true;
            }
        }

        return parsedMenus;
    }

    @Override
    public String getParserName() {
        return PARSER_NAME;
    }

    @Override
    public String getParserType() {
        return PARSER_TYPE;
    }
}
