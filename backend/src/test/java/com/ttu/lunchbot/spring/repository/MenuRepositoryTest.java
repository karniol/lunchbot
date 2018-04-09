package com.ttu.lunchbot.spring.repository;

import com.ttu.lunchbot.spring.model.FoodService;
import com.ttu.lunchbot.spring.model.Menu;
import com.ttu.lunchbot.spring.model.Parser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private FoodServiceRepository foodServiceRepository;

    @Autowired
    private ParserRepository parserRepository;

    @Test
    @Transactional
    public void testFindByFoodServiceIdFindsAllAndOnlyThoseWithFoodServiceID() {
        FoodService foodService1 = new FoodService();
        FoodService foodService2 = new FoodService();
        Parser parser = new Parser();

        parser.setFoodServices(Arrays.asList(foodService1, foodService2));
        parserRepository.save(parser);

        foodService1.setParser(parser);
        foodService2.setParser(parser);

        LocalDate rightDate = LocalDate.of(2000, 3, 20);
        LocalDate wrongDate = LocalDate.of(2030, 2, 23);

        foodService1 = foodServiceRepository.save(foodService1);
        foodService2 = foodServiceRepository.save(foodService2);

        for (int i = 0; i < 13; i++) {
            Menu menu = new Menu();
            foodService1.getMenus().add(menu);
            menu.setFoodService(foodService1);
            menu.setDate(rightDate);
            menuRepository.save(menu);
        }


        for (int i = 0; i < 20; i++) {
            Menu menu = new Menu();
            foodService2.getMenus().add(menu);
            menu.setFoodService(foodService2);
            menu.setDate(wrongDate);
            menuRepository.save(menu);
        }

        List<Menu> rightMenus = menuRepository.findByFoodServiceId(foodService1.getId());
        assertEquals(13, rightMenus.size());

        for (Menu menu : rightMenus) assertEquals(rightDate, menu.getDate());
    }

}
