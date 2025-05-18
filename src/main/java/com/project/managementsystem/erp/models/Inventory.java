package com.project.managementsystem.erp.models;

/**
 * Represents inventory information for a product.
 */
public class Inventory {
    private int id;
    private int productId;
    private int quantityChange; // Positive or negative (e.g., +50 for incoming, -5 for sale)
    private int currentStock;   // Resulting stock after change
    private String changeType;  // "IN", "OUT", "ADJUSTMENT"
    private int referenceId;    // ID of related transaction/order
    private String timestamp;
    private String referenceType;


    public Inventory(int id, int productId, int quantityChange, int currentStock, String changeType, int referenceId, String timestamp,String referenceType) {
        this.id = id;
        this.productId = productId;
        this.quantityChange = quantityChange;
        this.currentStock = currentStock;
        this.changeType = changeType;
        this.referenceId = referenceId;
        this.timestamp = timestamp;
        this.referenceType = referenceType;
    }
    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
