package com.ttu.lunchbot.parser.menu.strategy.rahvatoit;

import com.google.gson.Gson;
import com.ttu.lunchbot.model.Menu;
import com.ttu.lunchbot.model.MenuItem;
import com.ttu.lunchbot.parser.menu.MenuParser;
import com.ttu.lunchbot.parser.menu.strategy.MenuParserStrategy;
import com.ttu.lunchbot.parser.menu.StringMenuParser;
import com.ttu.lunchbot.util.Locale;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RahvaToitMenuParserStrategy implements MenuParserStrategy {

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
    private static final String NAME_SEPARATOR_PATTERN = "[/]";

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
    private final Menu currentMenu;

    /**
     * Current MenuItem that is being updated and written into by the parser.
     * MenuItems are added into the currently updated Menu.
     */
    private final MenuItem currentMenuItem;

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
    private String matchFoodServiceName(String line) {
        if (line.matches(FOOD_SERVICE_SOC_PATTERN)) {
            return FOOD_SERVICE_SOC_PRETTY_NAME_ET;
        } else if (line.matches(FOOD_SERVICE_LIB_PATTERN)) {
            return FOOD_SERVICE_LIB_PRETTY_NAME_ET;
        } else {
            return null;
        }
    }

    private void parseMenuItem(String line) {
        List<String> lineParts = new ArrayList<>(Arrays.asList(line.split(NAME_SEPARATOR_PATTERN)));
        lineParts.replaceAll(String::trim);
        this.currentMenuItem.addName(com.ttu.lunchbot.util.Locale.ESTONIAN, lineParts.get(0));
        System.out.println(lineParts);
    }

    @Override
    public ArrayList<Menu> parse(String jsonString) {
        RahvaToitJsonObject json = (new Gson()).fromJson(jsonString, RahvaToitJsonObject.class);

        this.parsedMenus.clear();
        Menu currentMenu;
        for (RahvaToitJsonObject.Post post : json.data) {
            if (post.message == null) continue;

            List<String> lines = new ArrayList<>(Arrays.asList(post.message.split("\n")));
            lines.removeAll(Arrays.asList(null, ""));

            final String foodServiceName = matchFoodServiceName(lines.get(0));
            final Calendar calendar = parseDate(post.created_time);

            lines = lines.subList(1, lines.size() - 2);

            currentMenu = new Menu(foodServiceName, calendar);

            System.out.println(calendar.getTime() + " " + foodServiceName);
            for (String line : lines) {
               parseMenuItem(line);
            }
            System.out.println('\n');

        }

        return this.parsedMenus;
    }

    public static void main(String[] args) {
        MenuParserStrategy strategy = new RahvaToitMenuParserStrategy();
        MenuParser parser = new StringMenuParser(strategy);
        final String pathToExample = "/Users/charlie/Desktop/rahvatoit.json";
        ArrayList<Menu> parsedMenus = parser.parseMenus(new File(pathToExample));
        System.out.println(parsedMenus);
    }
}
