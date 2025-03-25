package com.uopeople.weather.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SearchHistory {
    private static final String HISTORY_KEY = "search_history";
    private static final int MAX_HISTORY_SIZE = 10;
    private final Preferences prefs;
    private final Gson gson;

    public SearchHistory() {
        prefs = Preferences.userNodeForPackage(SearchHistory.class);
        gson = new Gson();
    }

    public void addSearch(String city, WeatherData weatherData) {
        List<HistoryEntry> history = getHistory();
        
        // Add new entry at the beginning
        history.add(0, new HistoryEntry(city, weatherData));
        
        // Keep only the last MAX_HISTORY_SIZE entries
        if (history.size() > MAX_HISTORY_SIZE) {
            history = history.subList(0, MAX_HISTORY_SIZE);
        }
        
        // Save updated history
        saveHistory(history);
    }

    public List<HistoryEntry> getHistory() {
        String json = prefs.get(HISTORY_KEY, "[]");
        return gson.fromJson(json, new TypeToken<List<HistoryEntry>>(){}.getType());
    }

    private void saveHistory(List<HistoryEntry> history) {
        String json = gson.toJson(history);
        prefs.put(HISTORY_KEY, json);
    }

    public static class HistoryEntry {
        private final String city;
        private final String temperature;
        private final String conditions;
        private final LocalDateTime timestamp;

        public HistoryEntry(String city, WeatherData weatherData) {
            this.city = city;
            this.temperature = weatherData.getFormattedTemperature(true); // Store in Celsius
            this.conditions = weatherData.getConditions();
            this.timestamp = weatherData.getTimestamp();
        }

        public String getCity() {
            return city;
        }

        public String getTemperature() {
            return temperature;
        }

        public String getConditions() {
            return conditions;
        }

        public String getFormattedTimestamp() {
            return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }

        @Override
        public String toString() {
            return String.format("%s - %s, %s (%s)",
                getFormattedTimestamp(),
                city,
                temperature,
                conditions);
        }
    }
} 