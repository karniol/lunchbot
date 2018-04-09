package com.ttu.lunchbot.parser.menu.strategy.rahvatoit;

import com.google.gson.Gson;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.model.MenuItem;
import com.ttu.lunchbot.util.Locale;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RahvaToitMenuParserStrategy {

    static final int PARSE_SOC = 0;

    static final int PARSE_LIB = 1;

    /**
     * Date format used in the Facebook Graph API. Used for parsing dates into a Calendar object.
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ESTONIAN);

    /**
     * Case insensitive regular expression patterns for matching
     * two FoodService names found in Rahva Toit Facebook posts.
     */
    private static final String FOOD_SERVICE_SOC_PATTERN = ".*(?i)(soc|sots).*";
    private static final String FOOD_SERVICE_LIB_PATTERN = ".*(?i)(raamat).*";
    private static final String MENUITEM_NAME_SEPARATOR_PATTERN = "[/]";

    // TODO: Not be the best solution keeping FoodService names like this
    private static final String FOOD_SERVICE_SOC_PRETTY_NAME_ET = "Raamatukogu kohvik";
    private static final String FOOD_SERVICE_LIB_PRETTY_NAME_ET = "Sotsiaalteaduste maja söökla";

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
    public RahvaToitMenuParserStrategy() {
        this.parsedMenus = new ArrayList<>();
        this.currentMenu = null;
        this.currentMenuItem = null;
    }

    /**
     * Parse a line containing a date into a Calendar instance.
     * @param dateString String containing a date in some specified format.
     * @return Calendar instance containing the parsed date.
     */
    private Calendar parseDate(String dateString) {
        Calendar date = Calendar.getInstance();

        try {
            date.setTime(DATE_FORMAT.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * Match a FoodService and return the pretty-formatted string of the matched FoodService.
     * @param line Line containing the name of the FoodService.
     * @return Pretty-formatted string of matched FoodService.
     */
    private String matchFoodServiceName(String line, int parseType) {
        if (line.matches(FOOD_SERVICE_SOC_PATTERN) && parseType == PARSE_SOC) {
            return FOOD_SERVICE_SOC_PRETTY_NAME_ET;
        } else if (line.matches(FOOD_SERVICE_LIB_PATTERN) && parseType == PARSE_LIB) {
            return FOOD_SERVICE_LIB_PRETTY_NAME_ET;
        } else {
            return null;
        }
    }

    private BigDecimal priceStringToBigDecimal(String priceString) {
        priceString = priceString.replace("€", "");
        priceString = priceString.replace(".-", "");
        priceString = priceString.replace(",", ".");
        return BigDecimal.valueOf(Double.parseDouble(priceString));
    }

    private void parseMenuItem(String line) {
        //Remove all non-numbers from the end of the line
        int lastDigitIndex = getLastDigitIndex(line);
        if (lastDigitIndex == 0) return;  // Line doesn't contain any numbers so it can't have a menu item.
        line = line.substring(0, getLastDigitIndex(line) + 1);

        // Create and add new MenuItem
        this.currentMenuItem = new MenuItem();
        this.currentMenuItem.setCurrency("EUR");
        this.currentMenu.addItem(this.currentMenuItem);
        this.currentMenuItem.setMenu(currentMenu);

        final String[] linePartsArray = line.split(MENUITEM_NAME_SEPARATOR_PATTERN);
        List<String> lineParts = new ArrayList<>(Arrays.asList(linePartsArray));
        lineParts.replaceAll(String::trim);

        final String menuItemNameEstonian = lineParts.get(0);
        this.currentMenuItem.setNameEt(menuItemNameEstonian);

        String priceString;
        if (lineParts.size() == 1) {
            // If there is no english translation, put the estonian name as the english name
            this.currentMenuItem.setNameEn(menuItemNameEstonian);
            List<String> lineParts0 = Arrays.asList(lineParts.get(0).split(" "));
            priceString = lineParts0.get(lineParts0.size() - 1);
        } else {
            List<String> lineParts1 = Arrays.asList(lineParts.get(1).split(" "));
            final String menuItemNameEnglish = String.join(" ", lineParts1.subList(0, lineParts1.size() - 1));
            this.currentMenuItem.setNameEn(menuItemNameEnglish);
            priceString = lineParts1.get(lineParts1.size() - 1);
        }

        BigDecimal price = priceStringToBigDecimal(priceString);
        this.currentMenuItem.setPrice(price);
    }

    private int getLastDigitIndex(String line) {
        int lastDigitIndex = 0;
        for (int i = line.length() - 1; i > 0; i--) {
            if (Character.isDigit(line.charAt(i))) {
                lastDigitIndex = i;
                break;
            }
        }
        return lastDigitIndex;
    }

    public ArrayList<Menu> parseAndSelect(String jsonString, int parseType) {
        RahvaToitJsonObject json = (new Gson()).fromJson(jsonString, RahvaToitJsonObject.class);

        this.parsedMenus.clear();
        for (RahvaToitJsonObject.Post post : json.data) {
            if (post.message == null) continue;

            // Trim all strings and remove empty lines after trimming
            List<String> lines = new ArrayList<>(Arrays.asList(post.message.split("\n")));
            lines.replaceAll(String::trim);
            lines.removeAll(Arrays.asList(null, ""));

            // Assign name and date of food service
            final String foodServiceName = matchFoodServiceName(lines.get(0),
                    parseType);
            if (foodServiceName == null) continue;

            final Calendar calendar = parseDate(post.created_time);

            // Remove first and two last lines
            lines = lines.subList(1, lines.size() - 2);

            // Create new menu with the matched FoodService name and add it
            // to the collection of parsed menus
            this.currentMenu = new Menu(calendar);
            this.parsedMenus.add(this.currentMenu);

            for (String line : lines) {
                parseMenuItem(line);
            }
        }

        return this.parsedMenus;
    }

}
