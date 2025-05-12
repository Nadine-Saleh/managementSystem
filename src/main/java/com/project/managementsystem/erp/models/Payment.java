package com.project.managementsystem.erp.models;

import java.sql.Date;

public class Payment {
    private int id;
    private int invoiceId;
    private double amount;
    private Date paymentDate;
    private String method;
    private String status;
    private String reference;
    public Payment() {
        // Default constructor
    }

    public Payment(int id, int invoiceId, double amount, Date paymentDate,
                  String method, String status, String reference) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
        this.status = status;
        this.reference = reference;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getInvoiceId() { return invoiceId; }
    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
}