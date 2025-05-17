package com.project.managementsystem.erp.models;

import java.util.List;

/**
 * Represents a Product in the ERP system.
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private String unit;
    private String createdAt;
    private List<LineItem> items;


    /**
     * Full constructor for creating a Product object.
     */
    public Product(int id, String name,
                   double price, int quantity, String category,
                   String unit,
                   String createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity=quantity;
        this.category = category;
        this.unit = unit;
        this.createdAt = createdAt;
    }
    public Product( String name,
                   double price, int quantity, String category,
                   String unit,
                   String createdAt) {
        this.name = name;
        this.price = price;
        this.quantity=quantity;
        this.category = category;
        this.unit = unit;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public Product() {
        //TODO Auto-generated constructor stub
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



   public int getStock() {
    return quantity;
}

    public void setStock(int quantity) {
        this.quantity = quantity;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }





    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }



    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + quantity +
                ", category='" + category + '\'' +
                ", unitOfMeasure='" + unit + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}