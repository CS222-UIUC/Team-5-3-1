// src/api/api.js
import axios from 'axios';

export const fetchData = async () => {
  try {
    const response = await axios.get('/api/restaurants'); // No need for `http://localhost:5000` due to the proxy
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};
