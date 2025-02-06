import java.util.*;

public class TextAnalyzer {
    private String text;
    private String[] words;
    private Scanner scanner;

    public TextAnalyzer() {
        scanner = new Scanner(System.in);
    }

    public void getUserInput() {
        while (true) {
            System.out.print("Please enter a paragraph or lengthy text: ");
            text = scanner.nextLine().trim();
            if (!text.isEmpty()) {
                // Split text into words and store them
                words = text.toLowerCase().split("\\s+");
                break;
            }
            System.out.println("Error: Text cannot be empty. Please try again.");
        }
    }

    public int countCharacters() {
        return text.length();
    }

    public int countWords() {
        return words.length;
    }

    public Map.Entry<Character, Integer> findMostCommonChar() {
        Map<Character, Integer> charFrequency = new HashMap<>();
        
        // Count frequency of each character (excluding spaces)
        for (char c : text.toLowerCase().toCharArray()) {
            if (!Character.isWhitespace(c)) {
                charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
            }
        }

        return Collections.max(charFrequency.entrySet(), Map.Entry.comparingByValue());
    }

    public int getCharFrequency() {
        while (true) {
            System.out.print("Enter a character to find its frequency: ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.length() == 1) {
                char searchChar = input.charAt(0);
                return (int) text.toLowerCase().chars()
                    .filter(ch -> ch == searchChar)
                    .count();
            }
            System.out.println("Error: Please enter exactly one character.");
        }
    }

    public int getWordFrequency() {
        System.out.print("Enter a word to find its frequency: ");
        String searchWord = scanner.nextLine().trim().toLowerCase();
        
        int count = 0;
        for (String word : words) {
            if (word.equals(searchWord)) {
                count++;
            }
        }
        return count;
    }

    public int countUniqueWords() {
        return (int) Arrays.stream(words)
            .distinct()
            .count();
    }

    public void analyze() {
        System.out.println("\n=== Text Analysis Tool ===");
        getUserInput();

        System.out.println("\n=== Analysis Results ===");
        System.out.println("1. Total characters: " + countCharacters());
        System.out.println("2. Total words: " + countWords());

        Map.Entry<Character, Integer> mostCommon = findMostCommonChar();
        System.out.printf("3. Most common character: '%c' (appears %d times)%n", 
            mostCommon.getKey(), mostCommon.getValue());

        System.out.println("4. Character frequency: " + getCharFrequency());
        System.out.println("5. Word frequency: " + getWordFrequency());
        System.out.println("6. Number of unique words: " + countUniqueWords());
    }

    public static void main(String[] args) {
        TextAnalyzer analyzer = new TextAnalyzer();
        try {
            analyzer.analyze();
        } catch (Exception e) {
            System.out.println("\nAn error occurred: " + e.getMessage());
        } finally {
            analyzer.scanner.close();
        }
    }
}