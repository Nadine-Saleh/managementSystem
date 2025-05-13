package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.config.DBConnection;
import com.project.managementsystem.erp.models.LineItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineItemDAOImpl implements LineItemDAO {

    private static final String INSERT_LINE_ITEM = "INSERT INTO LineItem(invoiceId, productId, productName, quantity, unitPrice, total, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_LINE_ITEM = "UPDATE LineItem SET quantity = ?, unit_price = ?, total = ? WHERE id = ?";
    private static final String DELETE_LINE_ITEM = "DELETE FROM LineItem WHERE id = ?";
    private static final String SELECT_LINE_ITEM_BY_ID = "SELECT * FROM LineItem WHERE id = ?";
    private static final String SELECT_LINE_ITEMS_BY_INVOICE = "SELECT * FROM LineItem WHERE invoiceId = ? AND type = ?";

    @Override
    public void addLineItem(LineItem item) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_LINE_ITEM)) {

            stmt.setInt(1, item.getInvoiceId());
            stmt.setInt(2, item.getProductId());
            stmt.setString(3, item.getProductName());
            stmt.setInt(4, item.getQuantity());
            stmt.setDouble(5, item.getUnitPrice());
            stmt.setDouble(6, item.getTotal());
            stmt.setString(7, item.getType());
System.out.println(stmt);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding line item", e);
        }
    }

    @Override
    public void updateLineItem(LineItem item) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_LINE_ITEM)) {

            stmt.setInt(1, item.getQuantity());
            stmt.setDouble(2, item.getUnitPrice());
            stmt.setDouble(3, item.getTotal());
            stmt.setInt(4, item.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating line item", e);
        }
    }

    @Override
    public void deleteLineItem(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_LINE_ITEM)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting line item", e);
        }
    }

    @Override
    public LineItem getLineItemById(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_LINE_ITEM_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToLineItem(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching line item by ID", e);
        }
        return null;
    }


    public List<LineItem> getLineItemsByInvoiceId(int invoiceId, String type) {
        List<LineItem> items = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_LINE_ITEMS_BY_INVOICE)) {

            stmt.setInt(1, invoiceId);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                items.add(mapResultSetToLineItem(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching line items for invoice", e);
        }
        return items;
    }

    @Override
    public List<LineItem> getAllLineItems() {
        List<LineItem> items = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM line_items")) {

            while (rs.next()) {
                items.add(mapResultSetToLineItem(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all line items", e);
        }
        return items;
    }

    private LineItem mapResultSetToLineItem(ResultSet rs) throws SQLException {
        LineItem item = new LineItem();
        item.setId(rs.getInt("id"));
        item.setInvoiceId(rs.getInt("invoice_id"));
        item.setProductId(rs.getInt("product_id"));
        item.setProductName(rs.getString("product_name"));
        item.setQuantity(rs.getInt("quantity"));
        item.setUnitPrice(rs.getDouble("unit_price"));
        item.setTotal(rs.getDouble("total"));
        item.setType(rs.getString("type"));

        return item;
    }
}