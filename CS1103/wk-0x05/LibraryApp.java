import java.util.*;

class LibraryItem<T> {
    private String title;
    private String author;
    private T itemID;

    public LibraryItem(String title, String author, T itemID) {
        this.title = title;
        this.author = author;
        this.itemID = itemID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public T getItemID() {
        return itemID;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ID: " + itemID;
    }
}

// Generic Catalog class
class GenericCatalog<T> {
    private List<T> catalog;

    public GenericCatalog() {
        this.catalog = new ArrayList<>();
    }

    public void addItem(T item) {
        catalog.add(item);
        System.out.println("Item added successfully.");
    }

    public boolean removeItem(T item) {
        if (catalog.contains(item)) {
            catalog.remove(item);
            System.out.println("Item removed successfully.");
            return true;
        } else {
            System.out.println("Item not found.");
            return false;
        }
    }

    public void displayCatalog() {
        if (catalog.isEmpty()) {
            System.out.println("The catalog is empty.");
        } else {
            for (T item : catalog) {
                System.out.println(item);
            }
        }
    }
}

// Command-line interface for user interaction
public class LibraryApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GenericCatalog<LibraryItem<String>> libraryCatalog = new GenericCatalog<>();

        while (true) {
            System.out.println("\nLibrary Catalog System");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. View Catalog");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Item ID: ");
                    String itemID = scanner.nextLine();
                    LibraryItem<String> newItem = new LibraryItem<>(title, author, itemID);
                    libraryCatalog.addItem(newItem);
                    break;
                case 2:
                    System.out.print("Enter Item ID to remove: ");
                    String removeID = scanner.nextLine();
                    LibraryItem<String> itemToRemove = new LibraryItem<>("", "", removeID);
                    libraryCatalog.removeItem(itemToRemove);
                    break;
                case 3:
                    libraryCatalog.displayCatalog();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
