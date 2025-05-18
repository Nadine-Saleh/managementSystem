package com.project.managementsystem.erp.viewmodel;

public class InventoryViewModel {
    private String productName;
    private String changeType;
    private int quantityChange;
    private int currentStock;
    private String timestamp;
    private String referenceType;

    public InventoryViewModel(String productName, String changeType, int quantityChange, int currentStock, String timestamp, String referenceType) {
        this.productName = productName;
        this.changeType = changeType;
        this.quantityChange = quantityChange;
        this.currentStock = currentStock;
        this.timestamp = timestamp;
        this.referenceType = referenceType;
    }

    public String getProductName() {
        return productName;
    }

    public String getChangeType() {
        return changeType;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getReferenceType() {
        return referenceType;
    }
}
