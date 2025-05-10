package com.project.managementsystem.erp.services;

import com.project.managementsystem.erp.dao.*;
import com.project.managementsystem.erp.models.Invoice;
import com.project.managementsystem.erp.models.LineItem;

import java.util.List;

/**
 * Service class for managing Invoices and their Line Items.
 * Coordinates with DAOs and Inventory updates.
 */
public class InvoiceService {

    private final InvoiceDAO invoiceDAO;
    private final LineItemDAO lineItemDAO;
    private final InventoryDAO inventoryDAO;

    // Constructor injection (allows flexibility for testing/mock objects)
    public InvoiceService(InvoiceDAO invoiceDAO, LineItemDAO lineItemDAO, InventoryDAO inventoryDAO) {
        this.invoiceDAO = invoiceDAO;
        this.lineItemDAO = lineItemDAO;
        this.inventoryDAO = inventoryDAO;
    }

    /**
     * Creates an invoice and saves its line items.
     *
     * @param invoice The invoice object with line items
     * @return true if successful, false otherwise
     */
    public boolean createInvoiceWithItems(Invoice invoice) {
        try {
            // Step 1: Save main invoice first (gets assigned an ID)
            invoiceDAO.save(invoice);

            // Step 2: Save each line item
            for (LineItem item : invoice.getItems()) {
                item.setInvoiceId(invoice.getId());
                lineItemDAO.addLineItem(item);
            }

            // Step 3: Update inventory
            updateInventoryFromLineItems(invoice.getItems(), invoice.getId(), invoice.getType());

            return true;

        } catch (Exception e) {
            System.err.println("Error creating invoice: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing invoice and its line items.
     *
     * @param invoice Updated invoice object with new line items
     * @return true if successful, false otherwise
     */
    public boolean updateInvoiceWithItems(Invoice invoice) {
        try {
            // Step 1: Delete old line items
            List<LineItem> existingItems = lineItemDAO.getLineItemsByInvoiceId(invoice.getId(), invoice.getType());

            for (LineItem item : existingItems) {
                lineItemDAO.deleteLineItem(item.getId());
            }

            // Step 2: Add updated line items
            for (LineItem item : invoice.getItems()) {
                item.setInvoiceId(invoice.getId());
                lineItemDAO.addLineItem(item);
            }

            // Step 3: Recalculate inventory
            updateInventoryFromLineItems(invoice.getItems(), invoice.getId(), invoice.getType());

            // Step 4: Update main invoice record
            invoiceDAO.update(invoice);

            return true;

        } catch (Exception e) {
            System.err.println("Error updating invoice: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes an invoice and its line items.
     *
     * @param invoiceId ID of the invoice to delete
     * @param invoiceType "SALE" or "PURCHASE"
     * @return true if deleted successfully
     */
    public boolean deleteInvoiceWithItems(int invoiceId, String invoiceType) {
        try {
            // Step 1: Delete all line items
            List<LineItem> items = lineItemDAO.getLineItemsByInvoiceId(invoiceId, invoiceType);
            for (LineItem item : items) {
                lineItemDAO.deleteLineItem(item.getId());
            }

            // Step 2: Delete the invoice itself
            invoiceDAO.delete(invoiceId);

            return true;

        } catch (Exception e) {
            System.err.println("Error deleting invoice: " + e.getMessage());
            return false;
        }
    }

    /**
     * Calculates total amount from line items and sets it in the invoice.
     *
     * @param invoice Invoice to update
     */
    public void calculateAndSetTotalAmount(Invoice invoice) {
        double total = invoice.getItems().stream()
                .mapToDouble(LineItem::getTotal)
                .sum();
        invoice.setTotalAmount(total);
    }

    /**
     * Helper method to update inventory after invoice is saved or updated.
     */
    private void updateInventoryFromLineItems(List<LineItem> items, int invoiceId, String invoiceType) {
        for (LineItem item : items) {
            int productId = item.getProductId();
            int quantity = item.getQuantity();

            if ("SALE".equalsIgnoreCase(invoiceType)) {
                // Sale → reduce stock
                inventoryDAO.adjustStock(productId, -quantity, "OUT", "sales_order", invoiceId);
            } else if ("PURCHASE".equalsIgnoreCase(invoiceType)) {
                // Purchase → increase stock
                inventoryDAO.adjustStock(productId, quantity, "IN", "purchase_order", invoiceId);
            }
        }
    }
}