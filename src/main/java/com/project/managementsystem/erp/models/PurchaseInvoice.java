package com.project.managementsystem.erp.models;

import java.time.LocalDate;
import java.util.List;

public class PurchaseInvoice {

    private int id;
    private String supplierName;
    private int supplierId;
    private LocalDate createdAt;
    private double totalAmount;
    private String type; // ← Added field
    private List<LineItem> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // ✅ Add these two methods
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PurchaseInvoice{" +
                "id=" + id +
                ", supplierName='" + supplierName + '\'' +
                ", date=" + createdAt +
                ", total=" + totalAmount +
                '}';
    }
}