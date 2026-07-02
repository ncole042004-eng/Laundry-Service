package com.mycompany.laundryservice.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/laundry_service_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Returns a new connection to the laundry_service_db database.
     * Call this as: DBConnection.getConnection()
     *
     * Always use try-with-resources when calling this method:
     * try (Connection conn = DBConnection.getConnection()) { ... }
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Quick check to verify the database is reachable.
     * Returns true if a connection can be opened, false otherwise.
     */
    public static boolean canConnectToDB() {
        try (Connection conn = getConnection()) {
            return true;
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            return false;
        }
    }
}
