package com.ttu.lunchbot.parser.menu.strategy.rahvatoit;

import com.ttu.lunchbot.parser.menu.strategy.MenuParserStrategy;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.model.MenuItem;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class SOCParserStrategyTest {

    private List<Menu> menus;

    private static final String EXAMPLE_BODY =
            "{" +
                    "\"data\":[" +
                        "{" +
                            "\"created_time\":\"2018-04-09T07:56:21+0000\"," +
                            "\"message\":\"TT\\u00dc SOC-maja kohviku men\\u00fc\\u00fc 9.aprill\\n\\n" +
                                "Kodujuustu-murulaugu kreemsupp \\/ Cottage cheese-chives cream soup 1,20\\u20ac\\n" +
                                "V\\u00e4rskekapsa supp sealihaga \\/ Cabbage soup with pork 1,70\\u20ac\\n\\n" +
                                "P\\u00e4evapraad: Veisepihv \\/ Beef cutlet 2,30\\u20ac\\n" +
                                "Lihavaba: Porgandi-kinoa kotlet \\/ Carrot-quinoa cutlet 3,60\\u20ac\\n" +
                                "Kodune kotlet \\/ Homely cutlet 3,70\\u20ac\\n" +
                                "Plov sealihaga \\/ Pilaff with pork 3,75\\u20ac\\n" +
                                "Heigifilee kartulihelbes \\/ Fish fillet coated with potato flakes 3,85\\u20ac\\n" +
                                "Kanafilee paprika-juustu kastmes \\/ Chicken fillet in paprika-cheese sauce 4,50\\u20ac\\n\\n" +
                                "Head isu!\\n" +
                                "NB! Men\\u00fc\\u00fcs v\\u00f5ib esineda muudatusi.\"," +
                            "\"id\":\"357117511056357_1316005351834230\"" +
                        "}," +
                        "{" +
                            "\"created_time\":\"2018-04-08T07:48:31+0000\"," +
                            "\"message\":\"TT\\u00dc Raamatukogu kohviku men\\u00fc\\u00fc 9.aprill\\n\\n" +
                                "Seljanka \\/ Solyanka soup 1,90\\u20ac\\n" +
                                "Kana-nuudli supp \\/ Chicken-noodle soup 1,70\\u20ac\\n\\nL" +
                                "ihavaba: Bakla\\u017eaani gulja\\u0161\\u0161 \\/ Aubergine goulash 3,60\\u20ac\\n" +
                                "Kana-riisinuudli wok \\/ Chicken-ricenoodle wok 3,75\\u20ac\\n" +
                                "Sealiha-pesto kaste \\/ Pork-pesto sauce 3,90\\u20ac\\n" +
                                "Praetud maks koore kastmes \\/ Fried liver in cream sauce 3,95\\u20ac\\n" +
                                "L\\u00f5he sinepi-kurgi glasuuriga \\/ Salmon with mustard-pickle glaze 4,80\\u20ac\\n\\n" +
                                "Head isu!\\nNB! Men\\u00fc\\u00fcs v\\u00f5ib esineda muudatusi.\"," +
                            "\"id\":\"357117511056357_1316000191834746\"" +
                        "}" +
                    "]" +
                "}";

    @Before
    public void setupMenus() {
        MenuParserStrategy strategy = new SOCParserStrategy();
        menus = strategy.parse(EXAMPLE_BODY);
    }

    @Test
    public void testMenusListNotNull() {
        assertNotNull(menus);
    }

    @Test
    public void testMenusListSizeIsCorrect() {
        assertEquals(1, menus.size());
    }

    @Test
    public void testMenusNotNull() {
        for (Menu menu : menus) {
            assertNotNull(menu);
        }
    }

    @Test
    public void testMenuItemsListNotNull() {
        for (Menu menu : menus) {
            assertNotNull(menu.getMenuItems());
        }
    }

    @Test
    public void testMenuItemsListSizeIsCorrect() {
        assertEquals(8, menus.get(0).getMenuItems().size());
    }

    @Test
    public void testMenuItemsNotNull() {
        for (Menu menu : menus) {
            for (MenuItem menuItem : menu.getMenuItems()) {
                assertNotNull(menuItem);
            }
        }
    }

    @Test
    public void testMenuCorrectDateParsed() {
        assertEquals(LocalDate.of(2018, 4, 9), menus.get(0).getDate());
    }

}
