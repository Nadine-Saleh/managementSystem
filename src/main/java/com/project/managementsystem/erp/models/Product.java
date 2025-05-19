package com.project.managementsystem.erp.models;

import com.project.managementsystem.erp.observer.Subject;
import com.project.managementsystem.erp.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Product in the ERP system.
 */
public class Product implements Subject {

    private int id;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private String unit;
    private String createdAt;
    private List<LineItem> items;
    private List<Observer> observers = new ArrayList<>();
    private int threshold = 10;
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
    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    // Getters and Setters

    public Product() {
        //TODO Auto-generated constructor stub
    }

    public Product(String trim, double price2, int quantity2, String selectedCategory, String selectedUnit,
            String string) {
        //TODO Auto-generated constructor stub
    }
    // Observer pattern methods
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
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