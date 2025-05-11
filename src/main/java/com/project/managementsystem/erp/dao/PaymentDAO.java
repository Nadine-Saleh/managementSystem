package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.models.Payment;
import com.project.managementsystem.erp.models.PaymentWithCustomer;

import java.util.List;

/**
 * Data Access Object (DAO) interface for Payment entities.
 */
public interface PaymentDAO {

    /**
     * Saves a new payment to the database.
     *
     * @param payment The payment to save
     */
    void save(Payment payment);

    /**
     * Retrieves a payment by its ID.
     *
     * @param id The ID of the payment
     * @return The Payment object, or null if not found
     */
    Payment getById(int id);

    /**
     * Retrieves all payments from the database.
     *
     * @return A list of all payments
     */
    List<Payment> getAll();

    /**
     * Retrieves all payments with customer names (via JOIN).
     *
     * @return A list of PaymentWithCustomer objects
     */
    List<PaymentWithCustomer> getAllWithCustomerNames();

    /**
     * Updates an existing payment in the database.
     *
     * @param payment The updated payment data
     */
    void update(Payment payment);

    /**
     * Deletes a payment from the database by ID.
     *
     * @param id The ID of the payment to delete
     */
    void delete(int id);
}