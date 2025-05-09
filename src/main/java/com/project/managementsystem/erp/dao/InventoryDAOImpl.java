//package com.project.managementsystem.erp.dao;
//
//import com.project.managementsystem.erp.config.DBConnection;
//import com.project.managementsystem.erp.models.Inventory;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Implementation of InventoryDAO using JDBC for SQLite.
// */
//public class InventoryDAOImpl implements InventoryDAO {
//
//    // SQL Statements
//    private static final String INSERT_INVENTORY = "INSERT INTO inventory(product_id, quantity_change, current_stock, change_type, reference_id, timestamp) VALUES (?, ?, ?, ?, ?, ?)";
//    private static final String UPDATE_INVENTORY = "UPDATE inventory SET quantity_change = ?, current_stock = ?, change_type = ?, reference_id = ?, timestamp = ? WHERE id = ?";
//    private static final String DELETE_INVENTORY = "DELETE FROM inventory WHERE id = ?";
//    private static final String SELECT_INVENTORY_BY_ID = "SELECT * FROM inventory WHERE id = ?";
//    private static final String SELECT_ALL_INVENTORY = "SELECT * FROM inventory";
//    private static final String SELECT_INVENTORY_BY_PRODUCT = "SELECT * FROM inventory WHERE product_id = ?";
//    private static final String SELECT_CURRENT_STOCK = "SELECT stock FROM products WHERE id = ?";
//    private static final String UPDATE_PRODUCT_STOCK = "UPDATE products SET stock = ? WHERE id = ?";
//
//    @Override
//    public void addInventory(Inventory inventory) {
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(INSERT_INVENTORY)) {
//
//            stmt.setInt(1, inventory.getProductId());
//            stmt.setInt(2, inventory.getQuantityChange());
//            stmt.setInt(3, inventory.getCurrentStock());
//            stmt.setString(4, inventory.getChangeType());
//            stmt.setInt(5, inventory.getReferenceId());
//            stmt.setString(6, inventory.getTimestamp());
//
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error saving inventory record", e);
//        }
//    }
//
//    @Override
//    public void updateInventory(Inventory inventory) {
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(UPDATE_INVENTORY)) {
//
//            stmt.setInt(1, inventory.getQuantityChange());
//            stmt.setInt(2, inventory.getCurrentStock());
//            stmt.setString(3, inventory.getChangeType());
//            stmt.setInt(4, inventory.getReferenceId());
//            stmt.setString(5, inventory.getTimestamp());
//            stmt.setInt(6, inventory.getId()); // WHERE id = ?
//
//            int rowsUpdated = stmt.executeUpdate();
//            if (rowsUpdated == 0) {
//                throw new RuntimeException("Updating inventory failed, no rows affected.");
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error updating inventory record", e);
//        }
//    }
//
//    @Override
//    public void deleteInventory(int id) {
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(DELETE_INVENTORY)) {
//
//            stmt.setInt(1, id);
//            int rowsDeleted = stmt.executeUpdate();
//
//            if (rowsDeleted == 0) {
//                throw new RuntimeException("Deleting inventory failed, no rows deleted.");
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error deleting inventory record", e);
//        }
//    }
//
//    @Override
//    public Inventory getInventoryById(int id) {
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(SELECT_INVENTORY_BY_ID)) {
//
//            stmt.setInt(1, id);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return mapResultSetToInventory(rs);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error fetching inventory by ID: " + id, e);
//        }
//        return null;
//    }
//
//    @Override
//    public List<Inventory> getAllInventory() {
//        List<Inventory> inventoryList = new ArrayList<>();
//        try (Connection connection = DBConnection.getConnection();
//             Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(SELECT_ALL_INVENTORY)) {
//
//            while (rs.next()) {
//                inventoryList.add(mapResultSetToInventory(rs));
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error fetching all inventory records", e);
//        }
//        return inventoryList;
//    }
//
//    @Override
//    public List<Inventory> getInventoryByProductId(int productId) {
//        List<Inventory> inventoryList = new ArrayList<>();
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(SELECT_INVENTORY_BY_PRODUCT)) {
//
//            stmt.setInt(1, productId);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                inventoryList.add(mapResultSetToInventory(rs));
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error fetching inventory for product ID: " + productId, e);
//        }
//        return inventoryList;
//    }
//
//    @Override
//    public void adjustStock(int productId, int change, String changeType, String referenceType, int referenceId) {
//        int currentStock = getCurrentStock(productId);
//        int newStock = currentStock + change;
//
//        if (newStock < 0) {
//            throw new IllegalArgumentException("Stock cannot be negative");
//        }
//
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(INSERT_INVENTORY)) {
//
//            stmt.setInt(1, productId);
//            stmt.setInt(2, change);
//            stmt.setInt(3, newStock);
//            stmt.setString(4, changeType);
//            stmt.setInt(5, referenceId);
//            stmt.setString(6, java.time.LocalDateTime.now().toString());
//
//            stmt.executeUpdate();
//
//            // Update product stock
//            try (PreparedStatement updateStmt = connection.prepareStatement(UPDATE_PRODUCT_STOCK)) {
//                updateStmt.setInt(1, newStock);
//                updateStmt.setInt(2, productId);
//                updateStmt.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error adjusting stock for product ID: " + productId, e);
//        }
//    }
//
//    /**
//     * Helper method to get current stock from products table
//     */
//    private int getCurrentStock(int productId) {
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(SELECT_CURRENT_STOCK)) {
//
//            stmt.setInt(1, productId);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return rs.getInt("stock");
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error fetching current stock for product ID: " + productId, e);
//        }
//        return 0;
//    }
//
//    /**
//     * Utility method to map ResultSet to Inventory object
//     */
//    private Inventory mapResultSetToInventory(ResultSet rs) throws SQLException {
//        return new Inventory(
//                rs.getInt("id"),
//                rs.getInt("productId"),
//                rs.getInt("quantity_change"),
//                rs.getInt("current_stock"),
//                rs.getString("change_type"),
//                rs.getInt("reference_id"),
//                rs.getString("timestamp")
//        );
//    }
//}