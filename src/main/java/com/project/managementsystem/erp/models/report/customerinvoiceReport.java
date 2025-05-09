package com.project.managementsystem.erp.models.report;

import java.sql.Date;

/**
 * Represents a report entry showing customer invoice details.
 * Used to display aggregated or filtered invoice data per customer.
 */
public class customerinvoiceReport {
    private int customerId;
    private String customerName;
    private int invoiceId;
    private Date invoiceDate;
    private double totalAmount;

    // Default constructor (required by frameworks like JavaFX and ORMs)
    public customerinvoiceReport() {}

    // Full constructor
    public customerinvoiceReport(int customerId, String customerName, int invoiceId, Date invoiceDate, double totalAmount) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.invoiceId = invoiceId;
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
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
        return "CustomerInvoiceReport{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", invoiceId=" + invoiceId +
                ", invoiceDate=" + invoiceDate +
                ", totalAmount=" + totalAmount +
                '}';
    }
}