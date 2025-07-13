// Get DOM elements
const temperatureInput = document.getElementById("temperature");
const conversionType = document.getElementById("conversionType");
const convertBtn = document.getElementById("convertBtn");
const resultDiv = document.getElementById("result");
const errorDiv = document.getElementById("error");

// Conversion functions
function celsiusToFahrenheit(celsius) {
  return (celsius * 9) / 5 + 32;
}

function fahrenheitToCelsius(fahrenheit) {
  return ((fahrenheit - 32) * 5) / 9;
}

// Validate input
function isValidNumber(value) {
  return !isNaN(value) && value !== "";
}

// Format number to 2 decimal places
function formatNumber(num) {
  return Number(num).toFixed(2);
}

// Clear error message
function clearError() {
  errorDiv.textContent = "";
}

// Show error message
function showError(message) {
  errorDiv.textContent = message;
  resultDiv.textContent = "";
}

// Convert temperature
function convertTemperature() {
  clearError();

  const temperature = temperatureInput.value.trim();

  // Validate input
  if (!isValidNumber(temperature)) {
    showError("Please enter a valid number");
    return;
  }

  const temp = parseFloat(temperature);
  const conversion = conversionType.value;

  let result;
  if (conversion === "celsiusToFahrenheit") {
    result = celsiusToFahrenheit(temp);
    resultDiv.textContent = `${temp} °C is ${formatNumber(result)} °F`;
  } else {
    result = fahrenheitToCelsius(temp);
    resultDiv.textContent = `${temp} °F is ${formatNumber(result)} °C`;
  }
}

// Event listeners
convertBtn.addEventListener("click", convertTemperature);

// Allow Enter key to trigger conversion
temperatureInput.addEventListener("keypress", (event) => {
  if (event.key === "Enter") {
    convertTemperature();
  }
});

// Clear error when user starts typing or changes conversion type
temperatureInput.addEventListener("input", clearError);
conversionType.addEventListener("change", clearError);
