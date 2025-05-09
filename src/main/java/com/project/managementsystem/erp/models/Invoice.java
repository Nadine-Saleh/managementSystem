package com.project.managementsystem.erp.models;

import java.sql.Date;

/**
 * Represents an Invoice in the ERP system.
 */
public class Invoice {

    private int id;
    private String invoiceNumber;
    private String issueDate;
    private String dueDate;
    private String paymentStatus;
    private int customerId;
    private Date createdAt;
    private double totalAmount;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }



    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }



    // Default constructor (required by frameworks like JavaFX and ORMs)
    public Invoice() {}

    // Full constructor
    public Invoice(int id, int customerId, Date createdAt, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
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
                ", customerId=" + customerId +
                ", invoiceDate=" + createdAt +
                ", totalAmount=" + totalAmount +
                ", invoice number=" + invoiceNumber+
                ", issue date " +issueDate +
                ", due date " +dueDate+
                ", payment status " +paymentStatus +

                '}';
    }
}