import React, { useState, useCallback } from "react";
import './CurrentLocationButton.css';

const CurrentLocationButton = ({ setCurrLocation, setUseCurrLocation }) => {
  const [isSelected, setIsSelected] = useState(false); // Track the button's selected state
  
  const handleLocationClick = useCallback((e) => {
    if (isSelected) {
      // If already selected, deselect the button and reset location
      setIsSelected(false);
      setCurrLocation(null);  // Clear the location (or set it to a default value)
      setUseCurrLocation(false); // Flip using current location flag
    } else {
      // If not selected, get the current location
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const lat = position.coords.latitude;
            const lon = position.coords.longitude;
            const location = `${lat}, ${lon}`;
            setCurrLocation(location);
            setIsSelected(true); // Mark the button as selected
            setUseCurrLocation(true); //Flip using current location flag to true
          },
          (error) => {
            console.error("Error getting location:", error);
          }
        );
      } else {
        alert("Geolocation is not supported by this browser.");
      }
    }
  }, [isSelected, setCurrLocation]);

  return (
    <button
      type="button" 
      className={`current-location-button ${isSelected ? "selected" : ""}`}
      onClick={handleLocationClick}
    >
      {isSelected ? "Using Current Location" : "Use My Current Location"}
    </button>
  );
};

export default CurrentLocationButton;
