import java.util.ArrayList;
import java.util.Arrays;

public class StockPriceAnalysis {

    // Method to calculate the average stock price
    public static float calculateAveragePrice(float[] prices) {
        float sum = 0;
        for (float price : prices) {
            sum += price;
        }
        return sum / prices.length;
    }

    // Method to find the maximum stock price
    public static float findMaximumPrice(float[] prices) {
        float maxPrice = prices[0];
        for (float price : prices) {
            if (price > maxPrice) {
                maxPrice = price;
            }
        }
        return maxPrice;
    }

    // Method to count occurrences of a specific price
    public static int countOccurrences(float[] prices, float targetPrice) {
        int count = 0;
        for (float price : prices) {
            if (price == targetPrice) {
                count++;
            }
        }
        return count;
    }

    // Method to compute the cumulative sum of stock prices
    public static ArrayList<Float> computeCumulativeSum(ArrayList<Float> prices) {
        ArrayList<Float> cumulativeSumList = new ArrayList<>();
        float sum = 0;
        for (float price : prices) {
            sum += price;
            cumulativeSumList.add(sum);
        }
        return cumulativeSumList;
    }

    // The Main method to test the implemented methods
    public static void main(String[] args) {
        // Array of stock prices
        float[] stockPricesArray = {100.5f, 102.3f, 98.4f, 110.0f, 105.2f, 107.3f, 95.5f, 108.7f, 112.0f, 101.8f};

        // ArrayList of stock prices (same values as array)
        ArrayList<Float> stockPricesList = new ArrayList<>(Arrays.asList(100.5f, 102.3f, 98.4f, 110.0f, 105.2f, 107.3f, 95.5f, 108.7f, 112.0f, 101.8f));

        // Calculate average price
        float averagePrice = calculateAveragePrice(stockPricesArray);
        System.out.println("Average Stock Price: " + averagePrice);

        // Find the maximum price
        float maxPrice = findMaximumPrice(stockPricesArray);
        System.out.println("Maximum Stock Price: " + maxPrice);

        // Count occurrences of a specific price (e.g., 105.2f)
        int occurrences = countOccurrences(stockPricesArray, 105.2f);
        System.out.println("Occurrences of 105.2: " + occurrences);

        // Compute the cumulative sum of stock prices
        ArrayList<Float> cumulativeSum = computeCumulativeSum(stockPricesList);
        System.out.println("Cumulative Sum of Stock Prices: " + cumulativeSum);
    }
}
