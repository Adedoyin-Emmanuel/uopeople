package com.uopeople.weather.service;

import com.uopeople.weather.model.WeatherData;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.prefs.Preferences;

@Slf4j
public class WeatherService {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY_PREF = "openweather_api_key";
    private final WeatherApi weatherApi;
    private final Preferences prefs;

    public WeatherService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherApi.class);
        prefs = Preferences.userNodeForPackage(WeatherService.class);
    }

    public void setApiKey(String apiKey) {
        prefs.put(API_KEY_PREF, apiKey);
    }

    public String getApiKey() {
        return prefs.get(API_KEY_PREF, null);
    }

    public WeatherData getWeatherForCity(String city) throws IOException {
        String apiKey = getApiKey();
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IOException("API key not set. Please set your OpenWeatherMap API key first.");
        }

        Call<Map<String, Object>> call = weatherApi.getCurrentWeather(city, apiKey, "metric");
        Response<Map<String, Object>> response = call.execute();

        if (!response.isSuccessful()) {
            String errorMsg = "Error: " + response.code();
            if (response.code() == 401) {
                errorMsg = "Invalid API key. Please check your OpenWeatherMap API key.";
            } else if (response.code() == 404) {
                errorMsg = "City not found. Please check the city name.";
            } else if (response.code() == 429) {
                errorMsg = "API call limit exceeded. Please try again later.";
            }
            throw new IOException(errorMsg);
        }

        return parseWeatherData(response.body(), city);
    }

    @SuppressWarnings("unchecked")
    private WeatherData parseWeatherData(Map<String, Object> data, String city) {
        WeatherData weatherData = new WeatherData();
        weatherData.setCityName(city);
        weatherData.setTimestamp(LocalDateTime.now());

        Map<String, Object> main = (Map<String, Object>) data.get("main");
        weatherData.setTemperature(((Number) main.get("temp")).doubleValue());
        weatherData.setHumidity(((Number) main.get("humidity")).doubleValue());

        Map<String, Object> wind = (Map<String, Object>) data.get("wind");
        weatherData.setWindSpeed(((Number) wind.get("speed")).doubleValue());

        java.util.List<Map<String, Object>> weather = (java.util.List<Map<String, Object>>) data.get("weather");
        if (!weather.isEmpty()) {
            Map<String, Object> weatherInfo = weather.get(0);
            weatherData.setConditions((String) weatherInfo.get("description"));
            weatherData.setIconCode((String) weatherInfo.get("icon"));
        }

        return weatherData;
    }

    private interface WeatherApi {
        @GET("weather")
        Call<Map<String, Object>> getCurrentWeather(
                @Query("q") String city,
                @Query("appid") String apiKey,
                @Query("units") String units
        );
    }
} 