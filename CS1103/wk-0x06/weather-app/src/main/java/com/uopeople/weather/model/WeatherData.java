package com.uopeople.weather.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WeatherData {
    private double temperature;
    private double humidity;
    private double windSpeed;
    private String conditions;
    private String iconCode;
    private LocalDateTime timestamp;
    private String cityName;

    public double getTemperatureInFahrenheit() {
        return (temperature * 9/5) + 32;
    }

    public String getFormattedTemperature(boolean isCelsius) {
        double temp = isCelsius ? temperature : getTemperatureInFahrenheit();
        String unit = isCelsius ? "°C" : "°F";
        return String.format("%.1f%s", temp, unit);
    }

    public String getFormattedWindSpeed() {
        return String.format("%.1f m/s", windSpeed);
    }

    public String getFormattedHumidity() {
        return String.format("%.0f%%", humidity);
    }
} 