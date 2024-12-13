package com.cs222.fivethreeone;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // Importing post method

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // Importing content method


import java.beans.Transient;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@WebMvcTest(GooglePlacesController.class)
public class GooglePlacesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GooglePlacesService googlePlacesService;  // Mock the service layer
    
    @Test
    public void testGetNearbyRestaurantsBadRequest() throws Exception {
    mockMvc.perform(get("/api/restaurants/nearby"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetNearbyRestaurantsNoResults() throws Exception {
        when(googlePlacesService.getPlaces(anyString(), anyString())).thenReturn(Collections.emptyList());
    
        mockMvc.perform(get("/api/restaurants/nearby")
            .param("location", "40.730610,-73.935242")
            .param("radius", "1500"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test 
    public void testGetNearbyRestaurantsSuccess() throws Exception {
        List<Restaurant> restaurants = Arrays.asList(
            new Restaurant("Taco Bell", "500m", "321 Green St"),
            new Restaurant("Canes", "1200m", "658 E Healey St")
        );
        when(googlePlacesService.getPlaces(anyString(), anyString())).thenReturn(restaurants);
        mockMvc.perform(get("/api/restaurants/nearby")
                .param("location", "40.730610,-73.935242")
                .param("radius", "1500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Taco Bell"))
                .andExpect(jsonPath("$[0].distance").value("500m"))
                .andExpect(jsonPath("$[0].vicinity").value("321 Green St"))
                .andExpect(jsonPath("$[1].name").value("Canes"))
                .andExpect(jsonPath("$[1].distance").value("1200m"))
                .andExpect(jsonPath("$[1].vicinity").value("658 E Healey St"));
    }

    @Test
    public void testSelectOneRestaurantBadInput() throws Exception {
        List<Restaurant> badInput = List.of(
            new Restaurant("Taco Bell", "500m", "321 Green St"),
            new Restaurant("Canes", "1200m", "658 E Healey St")
        );
        mockMvc.perform(post("/api/restaurants/select")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(badInput)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("At least 3 restaurants must be selected by user"));    
    }
    

    @Test
    public void testSelectOneRestaurant() throws Exception {
        List<Restaurant> restaurants = Arrays.asList(
            new Restaurant("Taco Bell", "500m", "321 Green St"),
            new Restaurant("Canes", "1200m", "658 E Healey St"),
            new Restaurant("McDonalds", "500m", "616 E Green St")
        );
         mockMvc.perform(post("/api/restaurants/select")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(restaurants)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").isNotEmpty());  // Expect a valid restaurant in response
    }

    @Test
    public void testGetFilteredRestaurants() throws Exception {
        List<Restaurant> filteredRestaurants = Arrays.asList(
            new Restaurant("Taco Bell", "500m", "321 Green St"),
            new Restaurant("Canes", "1200m", "658 E Healey St")
        );
        when(googlePlacesService.getRandomRestaurants(anyString(), anyString(), eq(2), anyString(), anyInt()))
            .thenReturn(filteredRestaurants);

        mockMvc.perform(get("/api/restaurants/random")
            .param("location", "40.730610,-73.935242")
            .param("radius", "1500")
            .param("numRestaurants", "2")  
            .param("priceLevel", "2")      
            .param("cuisine", "Mexican"))  
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].name").value("Taco Bell"))
            .andExpect(jsonPath("$[0].distance").value("500m"))
            .andExpect(jsonPath("$[0].vicinity").value("321 Green St"))
            .andExpect(jsonPath("$[1].name").value("Canes"))
            .andExpect(jsonPath("$[1].distance").value("1200m"))
            .andExpect(jsonPath("$[1].vicinity").value("658 E Healey St"));
    }





    
}
