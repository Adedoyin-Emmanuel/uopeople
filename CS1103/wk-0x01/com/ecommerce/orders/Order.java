package com.ecommerce.orders;

import com.ecommerce.Customer;
import com.ecommerce.Product;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

public class Order {
    private int orderID;
    private Customer customer;
    private Map<Product, Integer> products;
    private double total;
    private LocalDateTime orderDate;
    private OrderStatus status;

    public Order(int orderID, Customer customer, Map<Product, Integer> products) {
        this.orderID = orderID;
        this.customer = customer;
        this.products = new HashMap<>(products);
        this.total = calculateTotal();
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    private double calculateTotal() {
        return products.entrySet().stream()
            .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
            .sum();
    }

    public String generateOrderSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(String.format("Order ID: %d\n", orderID));
        summary.append(String.format("Customer: %s (ID: %d)\n", 
            customer.getName(), customer.getCustomerID()));
        summary.append(String.format("Order Date: %s\n", orderDate));
        summary.append(String.format("Status: %s\n\n", status));
        summary.append("Products:\n");
        
        products.forEach((product, quantity) -> 
            summary.append(String.format("- %s x%d = $%.2f\n", 
                product.getName(), quantity, product.getPrice() * quantity))
        );
        
        summary.append(String.format("\nTotal: $%.2f", total));
        return summary.toString();
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }

    // Getters
    public int getOrderID() { return orderID; }
    public Customer getCustomer() { return customer; }
    public Map<Product, Integer> getProducts() { return new HashMap<>(products); }
    public double getTotal() { return total; }
    public OrderStatus getStatus() { return status; }
}
