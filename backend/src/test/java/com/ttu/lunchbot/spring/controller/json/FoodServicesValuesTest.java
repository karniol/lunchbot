package com.ttu.lunchbot.spring.controller.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttu.lunchbot.spring.model.FoodService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class FoodServicesValuesTest {

    private FoodService foodService;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        foodService = new FoodService();
    }

    @Test
    public void testOpenTimeCorrectFormat() throws JsonProcessingException, JSONException {
        foodService.setOpenTime(LocalTime.of(5, 5));

        String str = mapper.writeValueAsString(foodService);

        JSONObject json = new JSONObject(str);

        System.out.println(json.getString("open_time"));
        //TODO integration test for open_time format
        //assertTrue(json.getString("open_time").matches("[0-9]{2}:[0-9]{2}"));
    }

    @Test
    public void testOpenTimeFormatIs24Hr() throws JsonProcessingException, JSONException {
        foodService.setOpenTime(LocalTime.of(17, 5));

        String str = mapper.writeValueAsString(foodService);

        JSONObject json = new JSONObject(str);

        System.out.println(json.getString("open_time"));
        //TODO integration test for open_time format
        assertEquals(json.getJSONObject("open_time").getInt("hour"), 17);
    }

    @Test
    public void testCloseTimeCorrectFormat() throws JsonProcessingException, JSONException {
        foodService.setCloseTime(LocalTime.of(5, 5));

        String str = mapper.writeValueAsString(foodService);

        JSONObject json = new JSONObject(str);

        System.out.println(json.getString("close_time"));
        //TODO integration test for close_time format
        //assertTrue(json.getString("close_time").matches("[0-9]{2}:[0-9]{2}"));
    }

    @Test
    public void testCloseTimeFormatIs24Hr() throws JsonProcessingException, JSONException {
        foodService.setCloseTime(LocalTime.of(17, 5));

        String str = mapper.writeValueAsString(foodService);

        JSONObject json = new JSONObject(str);

        System.out.println(json.getString("close_time"));
        //TODO integration test for close_time format
        assertEquals(json.getJSONObject("close_time").getInt("hour"), 17);
    }

}
