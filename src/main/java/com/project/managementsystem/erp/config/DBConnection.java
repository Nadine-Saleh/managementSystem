package com.project.managementsystem.erp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection class handles the connection to the SQLite database.
 * singleton
 */
public class DBConnection {

    // Path to SQLite DB file
    private static final String DB_URL = "jdbc:sqlite:erp.db";

    // Singleton instance
    private static Connection conn = null;

    /**
     * Get the database connection.
     * Opens a new connection if none exists or the current one is closed.
     *
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                openConnection();
            }
        } catch (SQLException e) {
            System.err.println("Error checking connection status:");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Opens a new connection to the SQLite database.
     */
    private static void openConnection() {
        try {
            // Load SQLite JDBC driver (optional in newer versions)
            Class.forName("org.sqlite.JDBC");

            // Establish the connection
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("✅ Successfully connected to the database.");
        } catch (Exception e) {
            System.err.println("❌ Failed to connect to the database:");
            e.printStackTrace();
        }
    }

    /**
     * Closes the current database connection, if open.
     */
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("🔌 Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection:");
            e.printStackTrace();
        }
    }

    /**
     * Utility method to test the connection.
     * You can call this from main() or during app startup to verify connectivity.
     */
    public static void testConnection() {
        try (Connection testConn = getConnection()) {
            if (testConn != null && !testConn.isClosed()) {
                System.out.println("✔️ Database connection is active.");
            } else {
                System.out.println("✘ Database connection failed.");
            }
        } catch (SQLException e) {
            System.err.println("Connection test failed:");
            e.printStackTrace();
        }
    }
}