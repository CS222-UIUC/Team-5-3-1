import React, { useState, useCallback } from "react";
import './CurrentLocationButton.css';

const CurrentLocationButton = ({ setLocation }) => {
  const [isSelected, setIsSelected] = useState(false); // Track the button's selected state
  
  const handleLocationClick = useCallback((e) => {
    if (isSelected) {
      // If already selected, deselect the button and reset location
      setIsSelected(false);
      setLocation(null);  // Clear the location (or set it to a default value)
    } else {
      // If not selected, get the current location
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const lat = position.coords.latitude;
            const lon = position.coords.longitude;
            const location = `${lat}, ${lon}`;
            setLocation(location);
            setIsSelected(true); // Mark the button as selected
          },
          (error) => {
            console.error("Error getting location:", error);
          }
        );
      } else {
        alert("Geolocation is not supported by this browser.");
      }
    }
  }, [isSelected, setLocation]);

  return (
    <button
      type="button" 
      className={`current-location-button ${isSelected ? "selected" : ""}`}
      onClick={handleLocationClick}
    >
      {isSelected ? "Current Location Selected" : "Use My Current Location"}
    </button>
  );
};

export default CurrentLocationButton;
