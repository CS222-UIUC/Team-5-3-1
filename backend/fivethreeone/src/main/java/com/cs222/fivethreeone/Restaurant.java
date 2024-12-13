package com.cs222.fivethreeone;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true) // Ignore any fields not in this class
public class Restaurant {
    private String name;

    private String distance;

    @JsonProperty("vicinity") // Map JSON field "vicinity" to "address"
    private String address;

    @JsonProperty("price_level") 
    private Integer priceLevel;

    private String cuisine;

    private Double rating; 

    @JsonProperty("user_ratings_total")
    private Integer numRatings;

    @JsonProperty("opening_hours") 
    private OpeningHours openingHours;

    @JsonProperty("icon") 
    private String iconUrl;

    @JsonProperty("place_id")
    private String id;

    @JsonProperty("editorial_overview")
    private String overview;

    private String website;

    @JsonProperty("formatted_phone_number")
    private String phone;

    public static class OpeningHours {
        @JsonProperty("open_now")
        private Boolean openNow;

        public Boolean getOpenNow() {
            return openNow;
        }

        public void setOpenNow(Boolean openNow) {
            this.openNow = openNow;
        }
    }




    public Restaurant(String name, String distance, String address, Integer priceLevel, String cuisine) { //String businessStatus) {
        this.name = name;
        this.distance = distance;
        this.address = address;
        this.priceLevel = priceLevel;
        this.cuisine = cuisine;
        
    }

    public Restaurant(String name, String distance, String address) { 
        this.name = name;
        this.distance = distance;
        this.address = address;
        this.priceLevel = null;
        this.cuisine = null;
        
    }

    public Restaurant() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPriceLevel() {
        return priceLevel;
    }
    public void setPriceLevel(Integer priceLevel) {
        this.priceLevel = priceLevel;
    }


    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer numRatings() {
        return numRatings;
    }

    public void numRatings(Integer numRatings) {
        this.numRatings = numRatings;
    }


    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    // Assuming you need to access openNow:
    public Boolean isOpenNow() {
        return this.openingHours != null ? openingHours.getOpenNow() : null;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    } 

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

}