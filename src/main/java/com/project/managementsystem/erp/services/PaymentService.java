
package com.project.managementsystem.erp.services;

import com.project.managementsystem.erp.dao.*;
import com.project.managementsystem.erp.models.Payment;
import com.project.managementsystem.erp.models.PaymentWithCustomer;
import com.project.managementsystem.erp.services.PaymentService;

import java.util.List;

/**
 * Default implementation of the PaymentService interface.
 */
public class PaymentService  {

    private final PaymentDAO paymentDAO;

    public PaymentService() {
        this.paymentDAO = new PaymentDAOImpl();
    }

    public void addPayment(Payment payment) {
        // You can add more business logic here (e.g., validation)
        if (payment.getAmount() <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero.");
        }

        paymentDAO.save(payment);
    }
public List<Payment> getAllWithFormattedDates() {
    return paymentDAO.getAll(); // Assuming getAll() now uses PaymentView
}
    public Payment getPaymentById(int id) {
        return paymentDAO.getById(id);
    }

    public List<Payment> getAllPayments() {
        return paymentDAO.getAll();
    }

    public List<PaymentWithCustomer> getAllPaymentsWithCustomerNames() {
        return paymentDAO.getAllWithCustomerNames();
    }

    public void updatePayment(Payment payment) {
        // Optional: Add business rules before update
        if (payment.getAmount() < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative.");
        }

        paymentDAO.update(payment);
    }

    public void deletePayment(int id) {
        // Optional: Check if payment is deletable (e.g., status != 'Completed')
        paymentDAO.delete(id);
    }
}