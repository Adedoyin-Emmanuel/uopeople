package com.uopeople.weather;

import java.util.List;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Optional;
import java.io.IOException;
import javafx.geometry.Pos;
import java.time.LocalTime;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import java.util.concurrent.Executors;
import javafx.application.Application;
import java.util.concurrent.ExecutorService;
import com.uopeople.weather.model.WeatherData;
import com.uopeople.weather.model.SearchHistory;
import com.uopeople.weather.service.WeatherService;

public class WeatherApp extends Application {
    private TextField cityInput;
    private Label temperatureLabel;
    private Label humidityLabel;
    private Label windSpeedLabel;
    private Label conditionsLabel;
    private ImageView weatherIcon;
    private VBox forecastBox;
    private ToggleGroup unitToggle;
    private VBox historyBox;
    private Preferences prefs;
    private static final String TEMP_UNIT_KEY = "temperature_unit";
    
    private final WeatherService weatherService;
    private final SearchHistory searchHistory;
    private final ExecutorService executorService;
    private WeatherData currentWeather;

    public WeatherApp() {
        weatherService = new WeatherService();
        searchHistory = new SearchHistory();
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void start(Stage primaryStage) {
        prefs = Preferences.userNodeForPackage(WeatherApp.class);
        
        // Check if API key is set
        if (weatherService.getApiKey() == null) {
            showApiKeyDialog();
        }
        
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        MenuBar menuBar = createMenuBar();
        
        HBox searchBox = createSearchSection();
        VBox weatherDisplay = createWeatherDisplay();
        HBox unitSection = createUnitSection();
        
        forecastBox = new VBox(10);
        forecastBox.setAlignment(Pos.CENTER);
        Label forecastLabel = new Label("5-Day Forecast");
        forecastLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        forecastBox.getChildren().add(forecastLabel);
        
        historyBox = new VBox(10);
        historyBox.setAlignment(Pos.CENTER);
        Label historyLabel = new Label("Search History");
        historyLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        historyBox.getChildren().add(historyLabel);
        updateHistoryDisplay();

        mainLayout.getChildren().addAll(
            menuBar,
            searchBox,
            weatherDisplay,
            unitSection,
            forecastBox,
            new Separator(),
            historyBox
        );

        updateBackgroundBasedOnTime(mainLayout);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setTitle("Weather Information App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: white;");
        
        Menu settingsMenu = new Menu("Settings");
        settingsMenu.setStyle("-fx-text-fill: #2C3E50;");
        
        MenuItem apiKeyItem = new MenuItem("Set API Key");
        apiKeyItem.setStyle("-fx-text-fill: #2C3E50;");
        apiKeyItem.setOnAction(e -> showApiKeyDialog());
        
        settingsMenu.getItems().add(apiKeyItem);
        menuBar.getMenus().add(settingsMenu);
        return menuBar;
    }

    private void showApiKeyDialog() {
        TextInputDialog dialog = new TextInputDialog(weatherService.getApiKey());
        dialog.setTitle("API Key Configuration");
        dialog.setHeaderText("Enter your OpenWeatherMap API Key");
        dialog.setContentText("API Key:");

        // Style the dialog
        dialog.getDialogPane().setStyle("-fx-background-color: white;");
        dialog.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-background-color: #3498DB; -fx-text-fill: white;");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setStyle("-fx-background-color: #ECF0F1; -fx-text-fill: #2C3E50;");
        
        // Make header text more visible
        Label headerLabel = (Label) dialog.getDialogPane().lookup(".header-panel .label");
        if (headerLabel != null) {
            headerLabel.setStyle("-fx-text-fill: #2C3E50; -fx-font-size: 14px; -fx-font-weight: bold;");
        }
        
        // Style the content text
        Label contentLabel = (Label) dialog.getDialogPane().lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setStyle("-fx-text-fill: #2C3E50; -fx-font-size: 12px;");
        }

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(apiKey -> {
            if (!apiKey.trim().isEmpty()) {
                weatherService.setApiKey(apiKey.trim());
            }
        });
    }

    private HBox createSearchSection() {
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        
        cityInput = new TextField();
        cityInput.setPromptText("Enter city name");
        cityInput.setStyle("-fx-background-color: #ECF0F1; -fx-text-fill: #2C3E50;");
        
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white;");
        searchButton.setOnAction(e -> searchWeather());
        
        searchBox.getChildren().addAll(cityInput, searchButton);
        return searchBox;
    }

    private VBox createWeatherDisplay() {
        VBox display = new VBox(10);
        display.setAlignment(Pos.CENTER);
        
        weatherIcon = new ImageView();
        weatherIcon.setFitHeight(100);
        weatherIcon.setFitWidth(100);
        
        temperatureLabel = new Label("Temperature: --");
        humidityLabel = new Label("Humidity: --");
        windSpeedLabel = new Label("Wind Speed: --");
        conditionsLabel = new Label("Conditions: --");
        
        display.getChildren().addAll(
            weatherIcon,
            temperatureLabel,
            humidityLabel,
            windSpeedLabel,
            conditionsLabel
        );
        return display;
    }

    private HBox createUnitSection() {
        HBox unitBox = new HBox(10);
        unitBox.setAlignment(Pos.CENTER);
        
        unitToggle = new ToggleGroup();
        
        RadioButton celsiusBtn = new RadioButton("Celsius");
        celsiusBtn.setStyle("-fx-text-fill: white;");
        celsiusBtn.setToggleGroup(unitToggle);
        celsiusBtn.setSelected(prefs.get(TEMP_UNIT_KEY, "C").equals("C"));
        
        RadioButton fahrenheitBtn = new RadioButton("Fahrenheit");
        fahrenheitBtn.setStyle("-fx-text-fill: white;");
        fahrenheitBtn.setToggleGroup(unitToggle);
        fahrenheitBtn.setSelected(prefs.get(TEMP_UNIT_KEY, "C").equals("F"));
        
        unitToggle.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String unit = newVal == celsiusBtn ? "C" : "F";
                prefs.put(TEMP_UNIT_KEY, unit);
                updateTemperatureDisplay();
            }
        });
        
        Label unitLabel = new Label("Temperature Unit:");
        unitLabel.setStyle("-fx-text-fill: white;");
        unitBox.getChildren().addAll(unitLabel, celsiusBtn, fahrenheitBtn);
        return unitBox;
    }

    private void searchWeather() {
        String city = cityInput.getText().trim();
        if (city.isEmpty()) {
            showError("Please enter a city name");
            return;
        }

        if (weatherService.getApiKey() == null) {
            showError("Please set your OpenWeatherMap API key in Settings first");
            showApiKeyDialog();
            return;
        }

        executorService.submit(() -> {
            try {
                WeatherData weatherData = weatherService.getWeatherForCity(city);
                Platform.runLater(() -> {
                    updateWeatherDisplay(weatherData);
                    searchHistory.addSearch(city, weatherData);
                    updateHistoryDisplay();
                });
            } catch (IOException e) {
                Platform.runLater(() -> showError(e.getMessage()));
            }
        });
    }

    private void updateWeatherDisplay(WeatherData weatherData) {
        currentWeather = weatherData;
        boolean isCelsius = prefs.get(TEMP_UNIT_KEY, "C").equals("C");
        
        temperatureLabel.setText("Temperature: " + weatherData.getFormattedTemperature(isCelsius));
        humidityLabel.setText("Humidity: " + weatherData.getFormattedHumidity());
        windSpeedLabel.setText("Wind Speed: " + weatherData.getFormattedWindSpeed());
        conditionsLabel.setText("Conditions: " + weatherData.getConditions());
        
        // Update weather icon
        String iconUrl = String.format("http://openweathermap.org/img/w/%s.png", weatherData.getIconCode());
        weatherIcon.setImage(new Image(iconUrl));
    }

    private void updateTemperatureDisplay() {
        if (currentWeather != null) {
            boolean isCelsius = prefs.get(TEMP_UNIT_KEY, "C").equals("C");
            temperatureLabel.setText("Temperature: " + currentWeather.getFormattedTemperature(isCelsius));
        }
    }

    private void updateHistoryDisplay() {
        List<SearchHistory.HistoryEntry> history = searchHistory.getHistory();
        historyBox.getChildren().clear();
        Label historyTitle = new Label("Search History");
        historyTitle.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        historyBox.getChildren().add(historyTitle);
        
        for (SearchHistory.HistoryEntry entry : history) {
            Label historyLabel = new Label(entry.toString());
            historyLabel.setStyle("-fx-text-fill: white; -fx-padding: 5px;");
            historyBox.getChildren().add(historyLabel);
        }
    }

    private void updateBackgroundBasedOnTime(VBox mainLayout) {
        // Modern, neutral background
        String backgroundStyle = "-fx-background-color: linear-gradient(to bottom, #2C3E50, #3498DB);";
        mainLayout.setStyle(backgroundStyle);
        
        // Style all labels for better visibility
        temperatureLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        humidityLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        windSpeedLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        conditionsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        // Style the dialog
        alert.getDialogPane().setStyle("-fx-background-color: white;");
        alert.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-background-color: #3498DB; -fx-text-fill: white;");
        
        // Style the content text
        Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setStyle("-fx-text-fill: #2C3E50; -fx-font-size: 12px;");
        }
        
        alert.showAndWait();
    }

    @Override
    public void stop() {
        executorService.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 