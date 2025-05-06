package com.project.managementsystem.erp.models;

/**
 * Represents a Product in the ERP system.
 */
public class product {

    private int id;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private String unit;
    private Integer supplierId; // Nullable
    private String createdAt;

    /**
     * Full constructor for creating a Product object.
     */
    public product(int id, String name,
                   double price, int quantity,  String category,
                   String unit, Integer supplierId,
                   String createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity=quantity;
        this.unit = unit;
        this.supplierId = supplierId;
        this.createdAt = createdAt;
    }

    // Getters and Setters

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



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
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
                ", supplierId=" + supplierId +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}