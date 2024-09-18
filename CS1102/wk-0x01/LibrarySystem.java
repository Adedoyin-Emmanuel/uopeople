import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LibrarySystem {
    // A map to store book records with the title as key and quantity as value
    private static final Map<String, Integer> library = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Library System Menu:");
            System.out.println("1. Add Books");
            System.out.println("2. Borrow Books");
            System.out.println("3. Return Books");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBooks(scanner);
                    break;
                case 2:
                    borrowBooks(scanner);
                    break;
                case 3:
                    returnBooks(scanner);
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (choice != 4);

        scanner.close();
    }

    private static void addBooks(Scanner scanner) {
        System.out.print("Enter the book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author's name: ");
        String author = scanner.nextLine();
        System.out.print("Enter the quantity of books: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        library.put(title, library.getOrDefault(title, 0) + quantity);
        System.out.println("Book added/updated successfully.");
    }

    private static void borrowBooks(Scanner scanner) {
        System.out.print("Enter the book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the number of books to borrow: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); 

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        Integer availableQuantity = library.get(title);
        if (availableQuantity == null) {
            System.out.println("Book not found in the library.");
            return;
        }

        if (availableQuantity < quantity) {
            System.out.println("Not enough books available to borrow.");
        } else {
            library.put(title, availableQuantity - quantity);
            System.out.println("Books borrowed successfully.");
        }
    }

    private static void returnBooks(Scanner scanner) {
        System.out.print("Enter the book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the number of books to return: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); 

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        library.put(title, library.getOrDefault(title, 0) + quantity);
        System.out.println("Books returned successfully.");
    }
}
