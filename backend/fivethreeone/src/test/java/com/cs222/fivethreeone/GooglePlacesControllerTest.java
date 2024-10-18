package com.cs222.fivethreeone;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collections;


@WebMvcTest(GooglePlacesController.class)
public class GooglePlacesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GooglePlacesService googlePlacesService;  // Mock the service layer
    
    @Test
    public void testGetNearbyRestaurantsBadRequest() throws Exception {
    mockMvc.perform(get("/api/places"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetNearbyRestaurantsNoResults() throws Exception {
        when(googlePlacesService.getPlaces(anyString(), anyString())).thenReturn(Collections.emptyList());
    
        mockMvc.perform(get("/api/places")
            .param("location", "40.730610,-73.935242")
            .param("radius", "1500"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Testpublic void testGetNearbyRestaurantsSuccess() throws Exception {
        List<Restaurant> restaurants = Arrays.asList(
            new Restaurant("Taco Bell", "500m", "321 Green St"),
            new Restaurant("Canes", "1200m", "658 E Healey St")
        );
        when(googlePlacesService.getPlaces(anyString(), anyString())).thenReturn(restaurants);
        mockMvc.perform(get("/api/places")
                .param("location", "40.730610,-73.935242")
                .param("radius", "1500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Taco Bell"))
                .andExpect(jsonPath("$[0].distance").value("500m"))
                .andExpect(jsonPath("$[0].address").value("321 Green St"))
                .andExpect(jsonPath("$[1].name").value("Canes"))
                .andExpect(jsonPath("$[1].distance").value("1200m"))
                .andExpect(jsonPath("$[1].address").value("658 E Healey St"));
    }

    
}
