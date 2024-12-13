import GooglePlacesAutocomplete from "react-google-places-autocomplete";

const LocationSearch = ({ location, setLocation }) => {
  // Function to handle when a location is selected from the dropdown
  const handleLocationSelect = (value) => {
    if (value && value.description) {
      setLocation(value.description); // Update location state with the selected description
    }
  };

  return (
    <div className="location-search">
      <div>
        <GooglePlacesAutocomplete
          apiKey={process.env.REACT_APP_GOOGLE_MAPS_API_KEY} // Make sure this is properly set in your .env file
          selectProps={{
            value: { string: location }, // Set value of the input field to the location
            onChange: (value) => handleLocationSelect(value), // Update location when a user selects an option
            placeholder: "Enter Location", // Set placeholder text
          }}
        />
      </div>
    </div>
  )
};

export default LocationSearch;


