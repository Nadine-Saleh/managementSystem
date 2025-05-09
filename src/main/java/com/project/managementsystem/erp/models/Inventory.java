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