import React from 'react';
import RestaurantCard from './RestaurantCard'; 
import canesImage from './canes.jpg';
import bangkokImage from './bangkok.jpg';
import burritoImage from './burrito.png'
import midsummerImage from './midsummer.webp';
import chipotleImage from './chipotle.jpg';
import miaImage from './miaza.jpeg';
import oozuImage from './oozu.png';
import forageImage from './forage.png';
import sigImage from './sig.jpg';

const restaurants = [
  { id: 1, name: 'Bangkok Thai', image: bangkokImage },
  { id: 2, name: 'Burrito King', image: burritoImage },
  { id: 3, name: 'Chipotle', image: chipotleImage},
  { id: 4, name: 'Forage Kitchem', image: forageImage},
  { id: 5, name: 'Mia Zas', image: miaImage},
  { id: 6, name: 'Midsummer Lounge', image: midsummerImage},
  { id: 7, name: 'Oozu Ramen', image: oozuImage},
  { id: 8, name: 'Raising Canes', image: canesImage },
  { id: 9, name: 'Signature Grill', image: sigImage},
];


function Restaurants() {
  return (
    <><div className="restaurant-container">
      <h1>Popular Restaurants at UIUC</h1>
      <div className="restaurant-list">
        {restaurants.map((restaurant) => (
          <RestaurantCard key={restaurant.id} restaurant={restaurant} />
        ))}
      </div>
    </div><p>and more!</p></>
  );
}

export default Restaurants;
