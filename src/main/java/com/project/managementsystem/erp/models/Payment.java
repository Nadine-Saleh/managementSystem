package com.project.managementsystem.erp.models;


import java.time.LocalDate;

public class Payment {
    private int id;
    private int invoiceId;
    private double amount;
    private LocalDate paymentDate;
    private String method;
    private String status;
    private String reference;
    private String customerName; // New field

    // Default constructor
    public Payment() {}

    // Full constructor
    public Payment(int id, int invoiceId, double amount, LocalDate paymentDate,
                  String method, String status, String reference, String customerName) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
        this.status = status;
        this.reference = reference;
        this.customerName = customerName;
    }

    // Getters and Setters
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", method='" + method + '\'' +
                ", status='" + status + '\'' +
                ", reference='" + reference + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}