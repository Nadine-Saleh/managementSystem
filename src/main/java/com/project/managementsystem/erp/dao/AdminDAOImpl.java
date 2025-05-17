package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.config.DBConnection;
import com.project.managementsystem.erp.models.Admin;

import java.sql.*;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public boolean register(Admin admin) {
        String sql = "INSERT INTO admin(name, username, email, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getUsername());
            stmt.setString(3, admin.getEmail());
            stmt.setString(4, admin.getPassword());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Register failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Admin login(String email, String password) {
        String sql = "SELECT * FROM admin WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            System.err.println("Login failed: " + e.getMessage());
        }

        return null;
    }
}
