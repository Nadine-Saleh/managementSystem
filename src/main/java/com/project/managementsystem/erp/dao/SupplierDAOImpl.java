package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.config.DBConnection;
import com.project.managementsystem.erp.models.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    private static final String INSERT_SUPPLIER = "INSERT INTO suppliers(name, email, phone) VALUES (?, ?, ?)";
    private static final String UPDATE_SUPPLIER = "UPDATE suppliers SET name = ?, email = ?, phone = ? WHERE id = ?";
    private static final String DELETE_SUPPLIER = "DELETE FROM suppliers WHERE id = ?";
    private static final String SELECT_SUPPLIER_BY_ID = "SELECT * FROM suppliers WHERE id = ?";
    private static final String SELECT_ALL_SUPPLIERS = "SELECT * FROM suppliers";
    private static final String SEARCH_SUPPLIERS = "SELECT * FROM suppliers WHERE name LIKE ? OR phone LIKE ? OR email LIKE ?";

    @Override
    public void addSupplier(Supplier supplier) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_SUPPLIER, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getEmail());
            stmt.setString(3, supplier.getPhone());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Adding supplier failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    supplier.setId(generatedKeys.getInt(1));
                } else {
                    throw new RuntimeException("Adding supplier failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving supplier", e);
        }
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_SUPPLIER)) {

            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getEmail());
            stmt.setString(3, supplier.getPhone());
            stmt.setInt(4, supplier.getId());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RuntimeException("Updating supplier failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating supplier", e);
        }
    }

    @Override
    public void deleteSupplier(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_SUPPLIER)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new RuntimeException("Deleting supplier failed, no rows deleted.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting supplier", e);
        }
    }

    @Override
    public Supplier getSupplierById(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_SUPPLIER_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToSupplier(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching supplier by ID: " + id, e);
        }

        return null;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SUPPLIERS)) {

            while (rs.next()) {
                suppliers.add(mapResultSetToSupplier(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all suppliers", e);
        }

        return suppliers;
    }

    @Override
    public List<Supplier> searchSuppliers(String keyword) {
        String searchKeyword = "%" + keyword + "%";
        List<Supplier> suppliers = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SEARCH_SUPPLIERS)) {

            for (int i = 1; i <= 3; i++) {
                stmt.setString(i, searchKeyword);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                suppliers.add(mapResultSetToSupplier(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching suppliers", e);
        }

        return suppliers;
    }

    private Supplier mapResultSetToSupplier(ResultSet rs) throws SQLException {
        return new Supplier(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone")
        );
    }
}