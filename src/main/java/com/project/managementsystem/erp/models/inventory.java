package com.project.managementsystem.erp.models;

/**
 * Represents inventory information for a product.
 */
public class inventory {
    private int productId;
    private int quantityInStock;

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    private java.sql.Timestamp lastUpdated;

    public String getChangeType() {
        return changeType;
    }

    private String changeType ;

    // Default constructor (required by frameworks like JavaFX and ORMs)
    public inventory() {}

    // Full constructor
    public inventory(int productId, int quantityInStock, java.sql.Timestamp lastUpdated) {
        this.productId = productId;
        this.quantityInStock = quantityInStock;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public java.sql.Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(java.sql.Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // toString method for debugging/logging purposes
    @Override
    public String toString() {
        return "Inventory{" +
                "productId=" + productId +
                ", quantityInStock=" + quantityInStock +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}