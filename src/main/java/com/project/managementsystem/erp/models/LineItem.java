package com.project.managementsystem.erp.models;
public class LineItem {
    private int id;
    private int invoiceId;      // Sales or Purchase ID
    private int productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double total;
    private String type;
    public LineItem() {}

    // For new line items
    public LineItem(int invoiceId, int productId, String productName, int quantity, double unitPrice, String type) {
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.type = type;
        this.total = quantity * unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    // "SALE", "PURCHASE"
}