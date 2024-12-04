package com.cs222.fivethreeone;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/api/restaurants")
public class GooglePlacesController {
    private final GooglePlacesService googlePlacesService;

    public GooglePlacesController(GooglePlacesService googlePlacesService) {
        this.googlePlacesService = googlePlacesService;
    }

    @GetMapping("/nearby")
    public List<Restaurant> getPlaces(@RequestParam String location, @RequestParam String radius) throws JsonMappingException, JsonProcessingException {
        return googlePlacesService.getPlaces(location, radius);
    }

//http://localhost:8081/api/restaurants/random?location=40.10996616819772, -88.23113574620692&radius=1000&numRestaurants=5&priceLevel=2&cuisine=italian
    @GetMapping("/random")
    public List<Restaurant> getRandomRestaurants(
        @RequestParam(defaultValue = "40.1106,-88.2073") String location, // Default to latitude,longitude
        @RequestParam(defaultValue = "1500") String radius,
        @RequestParam(defaultValue = "5") int numRestaurants,
        @RequestParam(required = false) Integer priceLevel,
        @RequestParam(required = false) String cuisine) 
        throws JsonMappingException, JsonProcessingException {
        return googlePlacesService.getRandomRestaurants(location, radius, priceLevel, cuisine, numRestaurants);
    }


    //Receives POST request from client in the form of JSON of the 3 selected restuarants
    @PostMapping("/select")
    public Restaurant selectOneRestaurant(@RequestBody List<Restaurant> selectedRestaurants) {
        if (selectedRestaurants == null || selectedRestaurants.size() < 3) {
            throw new IllegalArgumentException("At least 3 restaurants must be selected by user");
        }
        Random random = new Random();
        int index = random.nextInt(selectedRestaurants.size());
        return selectedRestaurants.get(index);
    }

    // Exception handler for IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
            return ex.getMessage();
        }
}
