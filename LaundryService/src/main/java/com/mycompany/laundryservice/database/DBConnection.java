package com.mycompany.laundryservice.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
<<<<<<< HEAD
    private static final String URL =
            "jdbc:mysql://localhost:3306/laundry_service_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
=======

	private static final String URL
		= "jdbc:mysql://localhost:3306/laundry_service_db";
	private static final String USER = "root";
	private static final String PASSWORD = "";
>>>>>>> ae5508c (Updated MainJFrame: System Status Added (System Operational/Down Indicator). Updated DBConnection: Force MySQL driver to load and register with DriveManager. Updated HomePanel: Updated Row Count and Table Selection to non editable, Claim Number when highlighted is now white)

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("MySQL JDBC Driver not found on classpath: " + e.getMessage());
		}
	}

<<<<<<< HEAD
    public static boolean canConnectToDB() {
        try (Connection conn = getConnection()) {
            return true;
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            return false;
        }
    }

    public static int authenticateEmployee(String username, String password) {
        String sql = "SELECT employee_id, password FROM Employees WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (org.mindrot.jbcrypt.BCrypt.checkpw(password, storedHash)) {
                        return rs.getInt("employee_id");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Login query error: " + e.getMessage());
        }
        return -1; // invalid credentials
    }
}
=======
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static boolean canConnectToDB() {
		try (Connection conn = getConnection()) {
			return true;
		} catch (SQLException e) {
			System.out.println("Database Error: " + e.getMessage());
			return false;
		}
	}
}
>>>>>>> ae5508c (Updated MainJFrame: System Status Added (System Operational/Down Indicator). Updated DBConnection: Force MySQL driver to load and register with DriveManager. Updated HomePanel: Updated Row Count and Table Selection to non editable, Claim Number when highlighted is now white)
