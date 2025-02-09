import com.ecommerce.Product;
import com.ecommerce.Customer;
import com.ecommerce.orders.Order;
import com.ecommerce.orders.OrderStatus;

public class Main {
    public static void main(String[] args) {
        try {
            // Create products
            Product laptop = new Product(1, "Laptop", 999.99, "High-performance laptop", 10);
            Product phone = new Product(2, "Smartphone", 599.99, "Latest smartphone", 20);
            Product headphones = new Product(3, "Headphones", 149.99, "Wireless headphones", 30);

            // Create customer
            Customer customer = new Customer(1, "John Doe");

            // Add products to cart
            System.out.println("Adding products to cart...");
            customer.addToCart(laptop, 1);
            customer.addToCart(phone, 2);
            customer.addToCart(headphones, 1);

            // Display cart total
            System.out.println("Cart Total: $" + customer.calculateCartTotal());

            // Create order
            Order order = new Order(1, customer, customer.getCart());
            
            // Display order summary
            System.out.println("\nOrder Summary:");
            System.out.println(order.generateOrderSummary());

            // Update order status
            order.updateStatus(OrderStatus.CONFIRMED);
            System.out.println("\nUpdated order status to: " + order.getStatus());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}