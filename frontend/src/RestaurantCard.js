import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './RestaurantCard.css';
//import fallback from './fallback-image-url.png';

function RestaurantCard({ restaurant, onSelect, isSelected }) {
  const [restaurantImage, setRestaurantImage] = useState(restaurant.image);

  useEffect(() => {
    const fetchRestaurantImage = async () => {
      try {
        // Only fetch image if no image is already present
        if (!restaurant.image) {
          const response = await axios.get('http://localhost:8081/api/restaurant-images', {
            params: {
              restaurantName: restaurant.name,
              location: restaurant.location // Ensure you pass location from backend
            }
          });

          // If images are found, use the first one
          if (response.data && response.data.length > 0) {
            setRestaurantImage(response.data[0]);
          }
        }
      } catch (error) {
        console.error('Error fetching restaurant image:', error);
        // Fallback to default image if fetch fails
        //setRestaurantImage(fallback);
      }
    };

    fetchRestaurantImage();
  }, [restaurant]);

  const handleCardClick = () => {
    if (onSelect) {
      onSelect(restaurant.id);
    }
  };

  return (
    <div 
      className={`restaurant-card ${isSelected ? 'selected' : ''}`}
      onClick={handleCardClick}
    >
      <img
        src={restaurantImage}
        alt={restaurant.name || 'Restaurant'}
        //onError={() => setRestaurantImage(fallback)} // Additional error handling
      />
      <div className="overlay">
        <h2>{restaurant.name}</h2>
        <p className="description">
          {restaurant.openNow ? '✓ Open Now' : '✗ Closed'}
        </p>
      </div>
    </div>
  );
}

export default RestaurantCard;