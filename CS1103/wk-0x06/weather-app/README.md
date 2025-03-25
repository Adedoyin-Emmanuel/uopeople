# Weather Information App

A Java-based weather application that provides real-time weather updates with a graphical user interface. The application uses the OpenWeatherMap API to fetch weather data and displays it in a user-friendly format.

## Features

- Real-time weather data display
- Temperature unit conversion (Celsius/Fahrenheit)
- Dynamic background based on time of day
- Search history tracking
- Weather condition icons
- Error handling for invalid inputs and API failures

## Prerequisites

- Java 17 or higher
- Maven
- OpenWeatherMap API key

## Setup

1. Clone the repository
2. Navigate to the project directory
3. Open `src/main/java/com/uopeople/weather/service/WeatherService.java`
4. Replace `YOUR_API_KEY` with your actual OpenWeatherMap API key
5. Build the project:
   ```bash
   mvn clean package
   ```

## Running the Application

Run the application using Maven:

```bash
mvn javafx:run
```

## Using the Application

1. Enter a city name in the search box
2. Click the "Search" button to fetch weather data
3. View current weather information including:
   - Temperature
   - Humidity
   - Wind Speed
   - Weather Conditions
4. Toggle between Celsius and Fahrenheit using the radio buttons
5. View your search history at the bottom of the application

## Implementation Details

The application is built using:

- JavaFX for the graphical user interface
- OpenWeatherMap API for weather data
- Retrofit for API calls
- Gson for JSON parsing
- Lombok for reducing boilerplate code
- Java Preferences API for storing settings and history

## Error Handling

The application includes error handling for:

- Invalid city names
- Network failures
- API errors
- Invalid user input

## Contributing

Feel free to submit issues and enhancement requests.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
