package com.ttu.lunchbot.parser.menu.strategy.baltic;

import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.model.MenuItem;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BalticRestaurantMenuParserStrategyWithLongMenuTextTest {

    private ArrayList<Menu> menus;

    private final static String LONG_MENU_TEXT = "" +
            "Lõunarestoran" + "\n" +
            "Ehitajate tee 5, Tallinn" + "\n" +

            "Esmaspäev    9.   aprill" + "\n" +
            "Porgandisupp 4.20" + "\n" +
            "" + "\n" +
            "Carrot soup with pesto" + "\n" +

            "Teisipäev  10.    aprill" + "\n" +
            "  Lillkapsa-brokolipüreesupp    4.20" + "\n" +
            "Cauliflower-broccoli puree soup" + "\n" +
            "Kana - juustukaste (päevapraad) 4.20" + "\n" +
            "Chicken - cheese sauce" + "\n" +

            "Kolmapäev  11. aprill" + "\n" +
            "Pastinaagi-köögiviljapüreesupp 4.20" + "\n" +
            "Parsnip - vegetable puree soup" + "\n" +
            "Sealihastrooganov (päevapraad) 4.20" + "\n" +
            "Pork stroganoff    " + "\n" +
            " Veiseliha röstitud paprika ja suvikõrvitsaga   4.20  " + "\n" +
            "   Beef with roasted peppers and zucchini" + "\n" +

            "Neljapäev 12. aprill" + "\n" +
            "Restoran on suletud" + "\n" +

            "Menüüs võib esineda muutusi";

    @Before
    public void before() {
        BalticRestaurantMenuParserStrategy strategy = new BalticRestaurantMenuParserStrategy();
        menus = strategy.parse(LONG_MENU_TEXT);
    }

    @Test
    public void testLongMenuTextMenusListNotNull() {
        assertNotNull(menus);
    }

    @Test
    public void testLongMenuTextMenusListSize() {
        assertEquals(3, menus.size());
    }

    @Test
    public void testLongMenuTextMenusNotNull() {
        for (Menu menu : menus) {
            assertNotNull(menu);
        }
    }

    @Test
    public void testLongMenuTextMenuItemsListNotNull() {
        for (Menu menu : menus) {
            assertNotNull(menu.getMenuItems());
        }
    }

    @Test
    public void testLongMenuTextMenuItemsListSize() {
        for (int i = 0; i <= 2; i++) {
            assertEquals(i + 1, menus.get(i).getMenuItems().size());
        }
    }

    @Test
    public void testLongMenuTextMenuItemsNotNull() {
        for (Menu menu : menus) {
            for (MenuItem menuItem : menu.getMenuItems()) {
                assertNotNull(menuItem);
            }
        }
    }

    @Test
    public void testLongMenuTextMenuItemsPrice() {
        for (Menu menu : menus) {
            for (MenuItem menuItem : menu.getMenuItems()) {
                assertEquals(0, BigDecimal.valueOf(4.20).compareTo(menuItem.getPrice()));
            }
        }
    }

}
