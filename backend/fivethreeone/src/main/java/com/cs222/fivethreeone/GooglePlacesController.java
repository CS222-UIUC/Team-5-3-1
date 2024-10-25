package com.cs222.fivethreeone;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Random;


@RestController
public class GooglePlacesController {
    private final GooglePlacesService googlePlacesService;

    public GooglePlacesController(GooglePlacesService googlePlacesService) {
        this.googlePlacesService = googlePlacesService;
    }

    @GetMapping("/api/places")
    public List<Restaurant> getPlaces(@RequestParam String location, @RequestParam String radius) throws JsonMappingException, JsonProcessingException {
        return googlePlacesService.getPlaces(location, radius);
    }

    @GetMapping("/api/random-restaurants")
    public List<Restaurant> getRandomRestaurants(@RequestParam String location, @RequestParam String radius, @RequestParam(defaultValue = "5") int numRestaurants,
        @RequestParam(required = false) Integer priceLevel,
        @RequestParam(required = false) String cuisine) 
        throws JsonMappingException, JsonProcessingException {
        return googlePlacesService.getRandomPlaces(location, radius, priceLevel, cuisine, numRestaurants);
    }


    //Receives POST request from client in the form of JSON of the 3 selected restuarants
    @PostMapping("/api/selectThree")
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
