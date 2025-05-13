package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.config.DBConnection;
import com.project.managementsystem.erp.models.Payment;
import com.project.managementsystem.erp.models.PaymentWithCustomer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of PaymentDAO using JDBC.
 */
public class PaymentDAOImpl implements PaymentDAO {

    // SQL Statements
    private static final String INSERT_PAYMENT =
            "INSERT INTO Payment(invoice_id, amount, payment_date, method, status, reference) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_PAYMENT_BY_ID =
            "SELECT * FROM Payment WHERE id = ?";

    private static final String SELECT_ALL_PAYMENTS =
            "SELECT * FROM Payment";

    private static final String SELECT_PAYMENTS_WITH_CUSTOMERS =
            "SELECT " +
                    "p.id AS payment_id, " +
                    "p.invoice_id, " +
                    "c.Name AS customer_name, " +
                    "p.amount, " +
                    "p.payment_date, " +
                    "p.method, " +
                    "p.status, " +
                    "p.reference " +
                    "FROM Payments p " +
                    "JOIN invoices i ON p.invoice_id = i.id " +
                    "JOIN Customers c ON i.customerId = c.id";

    private static final String UPDATE_PAYMENT =
            "UPDATE Payment SET invoice_id = ?, amount = ?, payment_date = ?, method = ?, status = ?, reference = ? WHERE id = ?";

    private static final String DELETE_PAYMENT =
            "DELETE FROM Payment WHERE id = ?";

    /**
     * Inserts a new payment into the database.
     */
    @Override
    public void save(Payment payment) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_PAYMENT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, payment.getInvoiceId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, String.valueOf(payment.getPaymentDate()) ); // ✅ Already java.sql.Date
            stmt.setString(4, payment.getMethod());
            stmt.setString(5, payment.getStatus());
            stmt.setString(6, payment.getReference());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating payment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating payment failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving payment", e);
        }
    }

    /**
     * Retrieves a payment by ID.
     */
    @Override
    public Payment getById(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_PAYMENT_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Payment(
                        rs.getInt("id"),
                        rs.getInt("invoice_id"),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date").toLocalDate(),
                        rs.getString("method"),
                        rs.getString("status"),
                        rs.getString("reference")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching payment by ID: " + id, e);
        }
        return null;
    }

    /**
     * Retrieves all payments from the database.
     */
   @Override
public List<Payment> getAll() {
    List<Payment> payments = new ArrayList<>();
    String query = "SELECT * FROM PaymentView";

    try (Connection connection = DBConnection.getConnection();
         Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            Payment payment = new Payment();
            payment.setId(rs.getInt("id"));
            payment.setInvoiceId(rs.getInt("invoice_id"));
            payment.setAmount(rs.getDouble("amount"));

           

            // If using formatted_date from view:
            LocalDate formattedDate = rs.getDate("formatted_date").toLocalDate(); ;
            payment.setPaymentDate(formattedDate);

            payment.setMethod(rs.getString("method"));
            payment.setStatus(rs.getString("status"));
            payment.setReference(rs.getString("reference"));

            payments.add(payment);
        }

    } catch (SQLException e) {
        throw new RuntimeException("Error fetching payments", e);
    }

    return payments;
}

    /**
     * Retrieves all payments joined with customer names.
     */
    @Override
    public List<PaymentWithCustomer> getAllWithCustomerNames() {
        List<PaymentWithCustomer> payments = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_PAYMENTS_WITH_CUSTOMERS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                payments.add(new PaymentWithCustomer(
                        rs.getInt("payment_id"),
                        rs.getInt("invoice_id"),
                        rs.getString("customer_name"),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date"),
                        rs.getString("method"),
                        rs.getString("status"),
                        rs.getString("reference")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching payments with customer names", e);
        }
        return payments;
    }

    /**
     * Updates an existing payment.
     */
    @Override
    public void update(Payment payment) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_PAYMENT)) {

            stmt.setInt(1, payment.getInvoiceId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setDate(3, payment.getPaymentDate() != null ? Date.valueOf(payment.getPaymentDate()) : null);
            stmt.setString(4, payment.getMethod());
            stmt.setString(5, payment.getStatus());
            stmt.setString(6, payment.getReference());
            stmt.setInt(7, payment.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Updating payment failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating payment", e);
        }
    }

    /**
     * Deletes a payment by ID.
     */
    @Override
    public void delete(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_PAYMENT)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new RuntimeException("Deleting payment failed, no rows deleted.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting payment", e);
        }
    }
}