package com.project.managementsystem.erp.models;

import java.util.Date;

public class PaymentWithCustomer {
    private int paymentId;
    private int invoiceId;
    private String customerName;
    private double amount;
    private Date paymentDate;
    private String method;
    private String status;
    private String reference;

    public PaymentWithCustomer(int paymentId, int invoiceId, String customerName,
                              double amount, Date paymentDate, String method,
                              String status, String reference) {
        this.paymentId = paymentId;
        this.invoiceId = invoiceId;
        this.customerName = customerName;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
        this.status = status;
        this.reference = reference;
    }

    // Getters
    public int getPaymentId() { return paymentId; }
    public int getInvoiceId() { return invoiceId; }
    public String getCustomerName() { return customerName; }
    public double getAmount() { return amount; }
    public Date getPaymentDate() { return paymentDate; }
    public String getMethod() { return method; }
    public String getStatus() { return status; }
    public String getReference() { return reference; }

    @Override
    public String toString() {
        return "PaymentWithCustomer{" +
                "paymentId=" + paymentId +
                ", invoiceId=" + invoiceId +
                ", customerName='" + customerName + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", method='" + method + '\'' +
                ", status='" + status + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}