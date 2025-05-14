package com.project.managementsystem.erp.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents an Invoice in the ERP system.
 */
public class Invoice {

    private int id;
    private String customerName;
    private LocalDate createdAt;
    private double totalAmount;

    private String Type;
    private int customerId;
    // private String invoiceNumber;
    // private String dueDate;
    // private String paymentStatus;
    private List<LineItem> items;
    private Customer customer;

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    // public String getInvoiceNumber() {
    //     return invoiceNumber;
    // }

    // public void setInvoiceNumber(String invoiceNumber) {
    //     this.invoiceNumber = invoiceNumber;
    // }

    // public LocalDate getIssueDate() {
    //     return issueDate;
    // }

    // public void setIssueDate(LocalDate issueDate) {
    //     this.issueDate = issueDate;
    // }

    // public String getDueDate() {
    //     return dueDate;
    // }

    // public void setDueDate(String dueDate) {
    //     this.dueDate = dueDate;
    // }

    // public String getPaymentStatus() {
    //     return paymentStatus;
    // }

    // public void setPaymentStatus(String paymentStatus) {
    //     this.paymentStatus = paymentStatus;
    // }

    // Default constructor (required by frameworks like JavaFX and ORMs)
    public Invoice() {
    }

    // Full constructor
    public Invoice(int id, int customerId, LocalDate createdAt, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
    }

    public Invoice(int id, int customerId, String customerName, LocalDate createdAt, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
    }
    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // toString method for debugging/logging purposes
    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", customerName=" + customerName +
                ", customerId=" + customerId +
                ", invoiceDate=" + createdAt +
                ", totalAmount=" + totalAmount +
                ", type=" + Type +
                // ", invoice number=" + invoiceNumber +
                // ", issue date " + issueDate +
                // ", due date " + dueDate +
                // ", payment status " + paymentStatus +

                '}';
    }
}