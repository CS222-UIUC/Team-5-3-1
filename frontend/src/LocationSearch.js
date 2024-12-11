import React, { useState } from "react";
import GooglePlacesAutocomplete from "react-google-places-autocomplete";
import "./LocationSearch.css";

const LocationSearch = ({ searchLocation, setSearchLocation }) => {
  // Function to handle when a location is selected from the dropdown
  const handleLocationSelect = async (value) => {
    if (value && value.value) {
      // Get place details using the place_id
      const placeId = value.value.place_id;
      const apiKey = process.env.REACT_APP_GOOGLE_MAPS_API_KEY;

      try {
        const response = await fetch(
          `https://maps.googleapis.com/maps/api/place/details/json?place_id=${placeId}&key=${apiKey}`
        );
        const data = await response.json();
        console.log(data);

        if (data.status === "OK") {
          const { lat, lng } = data.result.geometry.location;
          console.log(lat);
          console.log(lng);
          setSearchLocation(`${lat}, ${lng}`); // Update location in the desired format
        } else {
          console.error("Error fetching place details:", data.status);
        }
      } catch (error) {
        console.error("Error fetching place details:", error);
      }
    } else {
      console.log("did not fetch data");
    }
  };

  return (
    <div className="location-search">
      <div>
        <GooglePlacesAutocomplete
          apiKey={process.env.REACT_APP_GOOGLE_MAPS_API_KEY}
          selectProps={{
            value: searchLocation ? { label: searchLocation, value: searchLocation } : null, // Set value of the input field
            onChange: handleLocationSelect, // Update location when a user selects an option
            placeholder: "Enter Address Or Use Current Location", // Placeholder text
          }}
        />
      </div>
    </div>
  );
};

export default LocationSearch;


