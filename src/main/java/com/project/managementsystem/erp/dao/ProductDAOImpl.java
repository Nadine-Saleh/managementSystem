package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.config.DBConnection;
import com.project.managementsystem.erp.models.Product;
import com.project.managementsystem.erp.util.TimeUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of ProductDAO using JDBC.
 */
public class ProductDAOImpl implements ProductDAO {

    // SQL Statements
    private static final String INSERT_PRODUCT = "INSERT INTO Product(name, price, quantity, category, unit, createdAt) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM Product WHERE id = ?";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM Product";
    private static final String UPDATE_PRODUCT = "UPDATE Product SET name = ?, price = ?, quantity = ?, category = ?, unit = ?, createdAt = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM Product WHERE id = ?";

    /**
     * Inserts a new product into the database.
     */
    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM Product";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

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

    @Override
    public void save(Product product) {
        if (product.getCreatedAt() == null || product.getCreatedAt().isEmpty()) {
            product.setCreatedAt(TimeUtils.formatCurrentDateTime());
        }
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());
            stmt.setString(4, product.getCategory());
            stmt.setString(5, product.getUnit());
            stmt.setString(6, product.getCreatedAt());
// After saving product

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId((int) generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving product", e);
        }
    }

    /**
     * Retrieves a product by ID.
     */
    @Override
    public Product getById(int id) {
        Product product = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category"),
                        rs.getString("unit"),
                        rs.getString("createdAt")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching product by ID: " + id, e);
        }
        return product;
    }

    /**
     * Retrieves all products from the database.
     */
    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category"),
                        rs.getString("unit"),
                        rs.getString("createdAt")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all products", e);
        }
        return products;
    }

    /**
     * Updates an existing product.
     */
    @Override
    public void update(Product product) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_PRODUCT)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());
            stmt.setString(4, product.getCategory());
            stmt.setString(5, product.getUnit());
            stmt.setString(6, product.getCreatedAt());
            stmt.setInt(7, product.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Updating product failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating product", e);
        }
    }

    /**
     * Deletes a product by ID.
     */
    @Override
    public void delete(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_PRODUCT)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new RuntimeException("Deleting product failed, no rows deleted.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product", e);
        }
    }
}