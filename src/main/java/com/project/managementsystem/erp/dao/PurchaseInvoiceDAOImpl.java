package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.config.DBConnection;
import com.project.managementsystem.erp.models.PurchaseInvoice;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseInvoiceDAOImpl implements PurchaseInvoiceDAO {

    private static final String INSERT_SQL = "INSERT INTO purchaseInvoice(supplier_name, date, total_amount, type, supplier_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE purchaseInvoice SET supplier_name = ?, date = ?, total_amount = ?, type = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM purchaseInvoice WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM purchaseInvoice WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM purchaseInvoice";

    @Override
    public int save(PurchaseInvoice invoice) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, invoice.getSupplierName());
            stmt.setObject(2, invoice.getCreatedAt());
            stmt.setDouble(3, invoice.getTotalAmount());
            stmt.setString(4, invoice.getType());
            stmt.setInt(5, invoice.getSupplierId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) throw new RuntimeException("Insert failed");

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else throw new RuntimeException("No ID obtained");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving purchase invoice", e);
        }
    }

    @Override
    public void update(PurchaseInvoice invoice) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, invoice.getSupplierName());
            stmt.setObject(2, invoice.getCreatedAt());
            stmt.setDouble(3, invoice.getTotalAmount());
            stmt.setString(4, invoice.getType());
            stmt.setInt(5, invoice.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating purchase invoice", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting purchase invoice", e);
        }
    }

    @Override
    public PurchaseInvoice getById(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                PurchaseInvoice invoice = new PurchaseInvoice();
                invoice.setId(rs.getInt("id"));
                invoice.setSupplierName(rs.getString("supplier_name"));
                invoice.setSupplierId(rs.getInt("supplier_id"));
                invoice.setCreatedAt(LocalDate.parse(rs.getString("date")));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setType(rs.getString("type"));
                return invoice;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching purchase invoice by ID", e);
        }
        return null;
    }

    @Override
    public List<PurchaseInvoice> getAll() {
        List<PurchaseInvoice> invoices = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL)) {

            while (rs.next()) {
                PurchaseInvoice invoice = new PurchaseInvoice();
                invoice.setId(rs.getInt("id"));
                invoice.setSupplierName(rs.getString("supplier_name"));
                invoice.setSupplierId(rs.getInt("supplier_id"));
                invoice.setCreatedAt(LocalDate.parse(rs.getString("date")));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setType(rs.getString("type"));

                invoices.add(invoice);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all purchase invoices", e);
        }
        return invoices;
    }
}