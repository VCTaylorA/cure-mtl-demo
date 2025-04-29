//------------------------------------------------------------
// Assignment 3
// Written by: Taylor Adams 40275281
//---------------------------------------------------------

// This file defines a Product class that represents a product with attributes such as name, country, category, and price.


public class Product implements Comparable<Product> {
    private String productName;
    private String country;
    private String category;
    private double price;
    
    // Constructor
    public Product(String productName, String country, String category, double price) {
        this.productName = productName;
        this.country = country;
        this.category = category;
        this.price = price;
    }
    
    // Getters and setters
    public String getProductName() {
        return productName;
    }
    
    public String getCountry() {
        return country;
    }
    
    public String getCategory() {
        return category;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    // For sorting products alphabetically by name
    @Override
    public int compareTo(Product other) {
        return this.productName.compareTo(other.productName);
    }
    
    @Override
    public String toString() {
        return productName + "," + country + "," + category + "," + price;
    }
}
