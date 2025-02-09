package com.ecommerce;

import java.util.*;

public class Customer {
    private int customerID;
    private String name;
    private Map<Product, Integer> shoppingCart;

    public Customer(int customerID, String name) {
        this.customerID = customerID;
        this.name = name;
        this.shoppingCart = new HashMap<>();
    }

    public void addToCart(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (product.getStockQuantity() < quantity) {
            throw new IllegalStateException("Not enough stock available");
        }
        shoppingCart.merge(product, quantity, Integer::sum);
    }

    public void removeFromCart(Product product, int quantity) {
        if (!shoppingCart.containsKey(product)) {
            throw new IllegalStateException("Product not in cart");
        }
        int currentQuantity = shoppingCart.get(product);
        if (quantity >= currentQuantity) {
            shoppingCart.remove(product);
        } else {
            shoppingCart.put(product, currentQuantity - quantity);
        }
    }

    public double calculateCartTotal() {
        return shoppingCart.entrySet().stream()
            .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
            .sum();
    }

    public Map<Product, Integer> getCart() {
        return new HashMap<>(shoppingCart);
    }

    public void clearCart() {
        shoppingCart.clear();
    }

    // Getters
    public int getCustomerID() { return customerID; }
    public String getName() { return name; }
}
