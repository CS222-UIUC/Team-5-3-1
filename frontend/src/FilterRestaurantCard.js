import React from 'react';
import './FilterRestaurantCard.css';

function FilterRestaurantCard({ restaurant, onSelect, isSelected }) {
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
      <div className="overlay">
        <h2>{restaurant.name}</h2>
        <p className="description">
          {restaurant.rating ? `Rating: ${restaurant.rating}` : 'No rating available'}
          <br/>
          {restaurant.openNow ? '✓ Open Now' : '✗ Closed'}
        </p>
      </div>
    </div>
  );
}

export default FilterRestaurantCard;