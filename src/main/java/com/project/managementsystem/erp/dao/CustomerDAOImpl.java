package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.config.DBConnection;
import com.project.managementsystem.erp.dao.CustomerDAO;
import com.project.managementsystem.erp.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CustomerDAO using JDBC for SQLite.
 */
public class CustomerDAOImpl implements CustomerDAO {

    // SQL Statements
    private static final String INSERT_CUSTOMER = "INSERT INTO customers(name, email, phone, balance) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER = "UPDATE customers SET name = ?, email = ?, phone = ?, balance = ? WHERE id = ?";
    private static final String DELETE_CUSTOMER = "DELETE FROM customers WHERE id = ?";
    private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE id = ?";
    private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers";
    private static final String SEARCH_CUSTOMERS = "SELECT * FROM customers WHERE name LIKE ? OR phone LIKE ? OR email LIKE ?";

    @Override
    public void addCustomer(Customer customer) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_CUSTOMER, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhone());
            stmt.setString(3, customer.getBalance());
            stmt.setString(4, customer.getEmail());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Adding customer failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getInt(1));
                } else {
                    throw new RuntimeException("Adding customer failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving customer", e);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_CUSTOMER)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getBalance());
            stmt.setInt(5, customer.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Updating customer failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating customer", e);
        }
    }

    @Override
    public void deleteCustomer(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_CUSTOMER)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new RuntimeException("Deleting customer failed, no rows deleted.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting customer", e);
        }
    }

    @Override
    public Customer getCustomerById(int id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_CUSTOMER_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCustomer(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching customer by ID: " + id, e);
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_CUSTOMERS)) {

            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all customers", e);
        }
        return customers;
    }

    @Override
    public List<Customer> searchCustomers(String keyword) {
        String searchKeyword = "%" + keyword + "%";
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SEARCH_CUSTOMERS)) {

            for (int i = 1; i <= 3; i++) {
                stmt.setString(i, searchKeyword);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching customers", e);
        }

        return customers;
    }

    /**
     * Utility method to map a ResultSet row to a Customer object
     */
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setBalance(rs.getString("balance"));

        return customer;
    }
}