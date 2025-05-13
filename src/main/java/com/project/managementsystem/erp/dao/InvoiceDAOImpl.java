package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.config.DBConnection;
import com.project.managementsystem.erp.dao.InvoiceDAO;
import com.project.managementsystem.erp.models.Invoice;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of InvoiceDAO using JDBC for SQLite.
 */
public class InvoiceDAOImpl implements InvoiceDAO {

    // SQL Statements
    private static final String INSERT_INVOICE = "INSERT INTO invoices(Customer_id, customer_name, date, total_amount, type) VALUES (?, ?, ?, ?,?)";
    private static final String UPDATE_INVOICE = "UPDATE invoices SET customer_name = ?, date = ?, total_amount = ?, type = ? WHERE id = ?";
    private static final String DELETE_INVOICE = "DELETE FROM invoices WHERE id = ?";
    private static final String SELECT_INVOICE_BY_ID = "SELECT * FROM invoices WHERE id = ?";
    private static final String SELECT_ALL_INVOICES = "SELECT * FROM invoices";
    private static final String SELECT_INVOICES_BY_TYPE = "SELECT * FROM invoices WHERE type = ?";

    /**
     * Adds a new invoice to the database.
     */
    @Override
    public int save(Invoice invoice) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_INVOICE, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, invoice.getCustomerId());
            stmt.setString(2, invoice.getCustomerName());
            stmt.setString(3, String.valueOf(invoice.getCreatedAt()));
            stmt.setDouble(4, invoice.getTotalAmount());
            stmt.setString(5, invoice.getType());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Creating invoice failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    invoice.setId(generatedId);
                    return generatedId;
                } else {
                    throw new RuntimeException("Creating invoice failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving invoice", e);
        }
    }

    /**
     * Updates an existing invoice in the database.
     */
    @Override
    public void update(Invoice invoice) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_INVOICE)) {

            stmt.setString(1, invoice.getCustomerName());
            stmt.setDate(2, invoice.getCreatedAt() != null ? Date.valueOf(invoice.getCreatedAt()) : null);
            stmt.setDouble(3, invoice.getTotalAmount());
            stmt.setString(4, invoice.getType());
            stmt.setInt(5, invoice.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Updating invoice failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating invoice", e);
        }
    }

    /**
     * Deletes an invoice by ID.
     */
    @Override
    public void delete(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_INVOICE)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new RuntimeException("Deleting invoice failed, no rows deleted.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting invoice", e);
        }
    }

    /**
     * Retrieves an invoice by ID.
     */
    @Override
    public Invoice getById(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_INVOICE_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToInvoice(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching invoice by ID: " + id, e);
        }
        return null;
    }

    /**
     * Retrieves all invoices from the database.
     */
    @Override
    public List<Invoice> getAll() {
        List<Invoice> invoices = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_INVOICES)) {

            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all invoices", e);
        }

        return invoices;
    }

    /**
     * Retrieves invoices by type ("SALE" or "PURCHASE").
     */
    @Override
    public List<Invoice> getByType(String type) {
        List<Invoice> invoices = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_INVOICES_BY_TYPE)) {

            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching invoices by type: " + type, e);
        }

        return invoices;
    }

    /**
     * Utility method to map a ResultSet row to an Invoice object.
     */
    private Invoice mapResultSetToInvoice(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId(rs.getInt("id"));
        invoice.setCustomerName(rs.getString("customer_name"));
        invoice.setCreatedAt(LocalDate.parse(rs.getString("date")));
        invoice.setTotalAmount(rs.getDouble("total_amount"));
        invoice.setType(rs.getString("type"));

        // Optional: Load line items here if needed
        // invoice.setItems(lineItemDAO.getLineItemsByInvoiceId(invoice.getId(), invoice.getType()));

        return invoice;
    }

    @Override
    public List<Invoice> searchByCustomerName(String keyword) {
        // TODO Auto-generated method stub
        return new ArrayList<>();
    }
}