package com.cs222.fivethreeone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Collections;


@Service
public class GooglePlacesService {
    @Value("${GOOGLE_API_KEY}") //inject API key
    private String apiKey;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    

    public GooglePlacesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Restaurant> getPlaces(String location, String radius) throws JsonMappingException, JsonProcessingException {
        String url = String.format(
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s&radius=%s&key=%s",
            location, radius, apiKey);
        String response = restTemplate.getForObject(url, String.class); //returns raw JSON response
        JsonNode root = objectMapper.readTree(response);
        JsonNode results = root.path("results");

        return objectMapper.convertValue(results, new TypeReference<List<Restaurant>>() {});
    }

    public List<Restaurant> getRandomRestaurants(String location, String radius, Integer priceLevel, String cuisine, int numRestaurants) throws JsonMappingException, JsonProcessingException {
        
        StringBuilder url = new StringBuilder(
            String.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s&radius=%s&type=restaurant", 
            location, radius)
        );

        if (priceLevel != null) {
            url.append("&minprice=").append(priceLevel);
            url.append("&maxprice=").append(priceLevel);
        }

        if (cuisine != null && !cuisine.isEmpty()) {
            url.append("&keyword=").append(cuisine);
        }

        url.append("&key=").append(apiKey);

        


        String response = restTemplate.getForObject(url.toString(), String.class); //returns raw JSON response
        JsonNode root = objectMapper.readTree(response);
        JsonNode results = root.path("results");

        System.out.println("Requesting Google Places API: " + url); // Log the API URL
        System.out.println("results: " + response);


        List<Restaurant> restaurants = objectMapper.convertValue(results, new TypeReference<List<Restaurant>>() {});
        Collections.shuffle(restaurants);

        if (restaurants.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if no restaurants found
}

        if (restaurants.size() > numRestaurants) {
            restaurants = restaurants.subList(0, numRestaurants);
        } 

        for (Restaurant r : restaurants) {
            getDetails(r);
        }

        return restaurants;

    }

    private void getDetails(Restaurant restaurant) throws JsonProcessingException {
    String detailsUrl = String.format(
        "https://maps.googleapis.com/maps/api/place/details/json?fields=editorial_summary,website,formatted_phone_number&place_id=%s&key=%s", 
        restaurant.getID(), apiKey);

    String detailsResponse = restTemplate.getForObject(detailsUrl, String.class);
    JsonNode detailsRoot = objectMapper.readTree(detailsResponse);
    JsonNode detailsResult = detailsRoot.path("result");

    if (detailsResult.has("editorial_summary")) {
        restaurant.setOverview(detailsResult.path("editorial_summary").path("overview").asText());
    }
    if (detailsResult.has("website")) {
        restaurant.setWebsite(detailsResult.path("website").asText());
    }
    if (detailsResult.has("formatted_phone_number")) {
        restaurant.setPhone(detailsResult.path("formatted_phone_number").asText());
    }
}    

}
