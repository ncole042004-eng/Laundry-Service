package com.mycompany.laundryservice.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {

	private static final String URL
		= "jdbc:mysql://localhost:3306/laundry_service_db";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	/**
	 * Returns a new connection to the laundry_service_db database. Call
	 * this as: DBConnection.getConnection()
	 *
	 * Always use try-with-resources when calling this method: try
	 * (Connection conn = DBConnection.getConnection()) { ... }
	 *
	 * @return
	 * @throws java.sql.SQLException
	 */
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

	public static int authenticateEmployee(String username, String password) {
		String sql = "SELECT employee_id, password FROM Employees WHERE username = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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

	public static String getUserName(int employeeId) {
		String sql = "SELECT name FROM Employees WHERE employee_id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, employeeId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("name");
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to load employee name: " + e.getMessage());
		}
		return "Unknown";
	}

	public static String getUserRole(int employeeId) {
		String sql = "SELECT role FROM Employees WHERE employee_id = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, employeeId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("role");
				}
			}

		} catch (SQLException e) {
			System.out.println("Failed to load employee role: " + e.getMessage());
		}

		return "Unknown";
	}
}
