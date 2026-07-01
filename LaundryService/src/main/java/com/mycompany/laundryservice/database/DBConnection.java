package com.mycompany.laundryservice.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/laundry_service_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD); 
    }

    public boolean canConnectToDB() {
        try (Connection conn = createConnection()) {
            return true;
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        DBConnection db = new DBConnection();

        if (db.canConnectToDB()) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed!");
        }
    }
}
