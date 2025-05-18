package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.config.DBConnection;
import com.project.managementsystem.erp.models.Inventory;
import com.project.managementsystem.erp.models.Product;
import com.project.managementsystem.erp.observer.StockNotification;
import com.project.managementsystem.erp.util.NotificationManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of InventoryDAO using JDBC for SQLite.
 */
public class InventoryDAOImpl implements InventoryDAO {

    // SQL Statements
    private static final String INSERT_INVENTORY = "INSERT INTO inventory(productId, quantityChange, currentStock, changeType, referenceId, timestamp, referenceType) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_INVENTORY = "UPDATE inventory SET quantity_change = ?, current_stock = ?, change_type = ?, reference_id = ?, timestamp = ? WHERE id = ?";
    private static final String DELETE_INVENTORY = "DELETE FROM inventory WHERE id = ?";
    private static final String SELECT_INVENTORY_BY_ID = "SELECT * FROM inventory WHERE id = ?";
    private static final String SELECT_ALL_INVENTORY = "SELECT * FROM inventory";
    private static final String SELECT_INVENTORY_BY_PRODUCT = "SELECT * FROM inventory WHERE productId = ?";
    private static final String SELECT_CURRENT_STOCK = "SELECT quantity FROM Product WHERE id = ?";
    private static final String UPDATE_PRODUCT_STOCK = "UPDATE Product SET quantity = ? WHERE id = ?";



    @Override
    public void addInventory(Inventory inventory) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_INVENTORY)) {

            stmt.setInt(1, inventory.getProductId());
            stmt.setInt(2, inventory.getQuantityChange());
            stmt.setInt(3, inventory.getCurrentStock());
            stmt.setString(4, inventory.getChangeType());
            stmt.setInt(5, inventory.getReferenceId());
            stmt.setString(6, inventory.getTimestamp());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving inventory record", e);
        }
    }

    @Override
    public void updateInventory(Inventory inventory) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_INVENTORY)) {

            stmt.setInt(1, inventory.getQuantityChange());
            stmt.setInt(2, inventory.getCurrentStock());
            stmt.setString(3, inventory.getChangeType());
            stmt.setInt(4, inventory.getReferenceId());
            stmt.setString(5, inventory.getTimestamp());
            stmt.setInt(6, inventory.getId());


            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Updating inventory failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating inventory record", e);
        }
    }

    @Override
    public void deleteInventory(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_INVENTORY)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new RuntimeException("Deleting inventory failed, no rows deleted.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting inventory record", e);
        }
    }

    @Override
    public Inventory getInventoryById(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_INVENTORY_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToInventory(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching inventory by ID: " + id, e);
        }
        return null;
    }

    @Override
    public List<Inventory> getAllInventory() {
        List<Inventory> inventoryList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_INVENTORY)) {

            while (rs.next()) {
                inventoryList.add(mapResultSetToInventory(rs));
                System.out.println("✔ Loaded inventory row: ID = " + rs.getInt("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all inventory records", e);
        }
        return inventoryList;
    }
@Override
    public void adjustStock(int productId, int change, String changeType, String referenceType, int referenceId) {
        int currentStock = getCurrentStockByProductId(productId);
        int newStock = currentStock + change;

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_INVENTORY)) {

            stmt.setInt(1, productId);
            stmt.setInt(2, change);
            stmt.setInt(3, newStock);
            stmt.setString(4, changeType);
            stmt.setInt(5, referenceId);
            stmt.setString(6, java.time.LocalDateTime.now().toString());
            stmt.setString(7, referenceType);

            stmt.executeUpdate();

            try (PreparedStatement updateStmt = connection.prepareStatement(UPDATE_PRODUCT_STOCK)) {
                updateStmt.setInt(1, newStock);
                updateStmt.setInt(2, productId);
                updateStmt.executeUpdate();
            }

            // ✅ هنا بقى التفعيل الحقيقي للتنبيه
            ProductDAO productDAO = new ProductDAOImpl();
            Product product = productDAO.getById(productId);

            if (product != null) {
                product.setStock(newStock);
                product.attach(new StockNotification());
                if (newStock <= product.getThreshold()) {
                    product.notifyObservers();
                    NotificationManager.getInstance().addNotification(
                            "⚠️ Low Stock: " + product.getName() + " → " + newStock + " units left"
                    );

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error adjusting stock for product ID: " + productId, e);
        }
    }

    /**
     * Helper method to get current stock from products table
     */
    @Override
    public int getCurrentStockByProductId(int productId) {
        int stock = 0;
        String sql = "SELECT SUM(quantityChange) AS total FROM inventory WHERE productId = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                stock = rs.getInt("total");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error calculating stock for product ID: " + productId, e);
        }

        return stock;
    }

    /**
     * Utility method to map ResultSet to Inventory object
     */
    private Inventory mapResultSetToInventory(ResultSet rs) throws SQLException {
        return new Inventory(
                rs.getInt("id"),
                rs.getInt("productId"),
                rs.getInt("quantityChange"),
                rs.getInt("currentStock"),
                rs.getString("changeType"),
                rs.getInt("referenceId"),
                rs.getString("timestamp"),
                rs.getString("referenceType")
        );

    }
    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM Product";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                productList.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all products", e);
        }

        return productList;
    }

}