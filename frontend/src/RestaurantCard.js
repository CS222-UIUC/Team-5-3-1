import React from 'react';
import './RestaurantCard.css'; // Create a CSS file for styles

function RestaurantCard({ restaurant }) {
  return (
    <div className="restaurant-card">
      <img src={restaurant.image} alt={restaurant.name} />
      <div className="overlay">
        <h2>{restaurant.name}</h2>
      </div>
    </div>
  );
}

export default RestaurantCard;
