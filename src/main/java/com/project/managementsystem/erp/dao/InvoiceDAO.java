package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.models.Invoice;
import java.util.List;

/**
 * Data Access Object (DAO) for Invoice entity.
 * Defines standard CRUD operations for invoice records.
 */
public interface InvoiceDAO {

    /**
     * Adds a new invoice to the database.
     *
     * @param invoice The Invoice object to save
     * @return 
     */
    int save(Invoice invoice);

    /**
     * Updates an existing invoice.
     *
     * @param invoice The updated Invoice object
     */
    void update(Invoice invoice);

    /**
     * Deletes an invoice by ID.
     *
     * @param id The ID of the invoice to delete
     */
    void delete(int id);

    /**
     * Retrieves an invoice by its ID.
     *
     * @param id The ID of the invoice
     * @return Invoice object if found, null otherwise
     */
    Invoice getById(int id);

    /**
     * Retrieves all invoices from the database.
     *
     * @return List of Invoices
     */
    List<Invoice> getAll();

    /**
     * Retrieves invoices by type (SALE or PURCHASE).
     *
     * @param type "SALE" or "PURCHASE"
     * @return List of matching invoices
     */
    List<Invoice> getByType(String type);
}