package com.ttu.lunchbot.parser.menu.strategy.baltic;

import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.model.MenuItem;
import com.ttu.lunchbot.util.CalendarConverter;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class BalticRestaurantMenuParserStrategyWithShortMenuTextTest {

    private ArrayList<Menu> menus;
    private Menu menu;
    private List<MenuItem> menuItems;
    private MenuItem menuItem;

    private final static String SHORT_MENU_TEXT = "" +
            "Lõunarestoran" + "\n" +
            "Ehitajate tee 5, Tallinn" + "\n" +

            "Esmaspäev 9. aprill" + "\n" +
            "Porgandisupp 1.20" + "\n" +
            "Carrot soup with pesto" + "\n" +

            "Menüüs võib esineda muutusi";

    @Before
    public void before() {
        BalticRestaurantMenuParserStrategy strategy = new BalticRestaurantMenuParserStrategy();
        menus = strategy.parse(SHORT_MENU_TEXT);
        menu = menus.get(0);
        menuItems = menu.getMenuItems();
        menuItem = menuItems.get(0);
    }

    @Test
    public void testShortMenuTextMenusListNotNull() {
        assertNotNull(menus);
    }

    @Test
    public void testShortMenuTextMenusListSize() {
        assertEquals(1, menus.size());
    }

    @Test
    public void testShortMenuTextMenuNotNull() {
        assertNotNull(menu);
    }

    @Test
    public void testShortMenuTextMenuItemsListNotNull() {
        assertNotNull(menuItems);
    }

    @Test
    public void testShortMenuTextMenuItemsListSize() {
        assertEquals(1, menuItems.size());
    }

    @Test
    public void testShortMenuTextMenuItemNotNull() {
        assertNotNull(menuItem);
    }

    @Test
    public void testShortMenuTextCorrectDate() {
        CalendarConverter converter = new CalendarConverter();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.APRIL, 9);
        assertEquals(converter.toLocalDate(calendar), menu.getDate());
    }

    @Test
    public void testShortMenuTextCorrectMenuItemEstonian() {
        assertEquals("Porgandisupp", menuItem.getNameEt());
    }

    @Test
    public void testShortMenuTextCorrectMenuItemEnglish() {
        assertEquals("Carrot soup with pesto", menuItem.getNameEn());
    }

    @Test
    public void testShortMenuTextCorrectMenuItemPrice() {
        assertEquals(BigDecimal.valueOf(1.20), menuItem.getPrice());
    }


    @Test
    public void testShortMenuTextCorrectMenuItemCurrency() {
        assertEquals("EUR", menuItem.getCurrency());
    }

}
