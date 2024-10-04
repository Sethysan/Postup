import jwt_decode from 'jwt-decode';
import axios from 'axios';

// Create an Axios instance
const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
});

// Function to check if token is expired
function isTokenExpired(token) {
  const decodedToken = jwt_decode(token);
  const currentTime = Date.now() / 1000; // Current time in seconds
  return decodedToken.exp < currentTime; // Returns true if the token is expired
}

// Function to get token expiration details and log them
function getTokenExpirationInfo(token) {
  const decodedToken = jwt_decode(token);

  if (decodedToken.exp) {
    const expirationTime = decodedToken.exp;
    const currentTime = Date.now() / 1000;
    const timeRemaining = expirationTime - currentTime;

    const hoursRemaining = Math.floor(timeRemaining / 3600);
    const minutesRemaining = Math.floor((timeRemaining % 3600) / 60);
    const secondsRemaining = Math.floor(timeRemaining % 60);

    console.log(`Token expires at: ${new Date(expirationTime * 1000).toLocaleString()}`);
    console.log(`Time remaining: ${hoursRemaining} hours, ${minutesRemaining} minutes, and ${secondsRemaining} seconds`);

    return { expirationTime, timeRemaining, hoursRemaining, minutesRemaining, secondsRemaining };
  } else {
    console.log('Token does not have an expiration time.');
    return null;
  }
}

// Axios interceptor for checking token expiration
http.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");

    if (token) {
      // Log token expiration details
      getTokenExpirationInfo(token);

      // Check if the token is expired
      if (isTokenExpired(token)) {
        console.log("Token has expired");
        localStorage.removeItem("token"); 
        window.location.href = "/login"; 
        return Promise.reject(new Error("Token has expired"));
      }

      // If the token is valid, include it in the headers
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default http;
