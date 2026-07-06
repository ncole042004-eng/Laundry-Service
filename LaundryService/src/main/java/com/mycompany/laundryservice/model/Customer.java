package com.mycompany.laundryservice.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.laundryservice.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Cral
 */
public class Customer {

	private int customerId;
	private String name;
	private String phone;
	private String address;
	private boolean isActive;
	
	public Customer(int customerId, String name, String phone, String address, boolean isActive) {
		this.customerId = customerId;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.isActive = isActive;
	}

	public Customer(String name, String phone, String address) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.isActive = true; 
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	@Override
	public String toString() {
		return name + " (" + phone + ")";
	}

	public Customer findCustomerByPhone(String phone) {
		String sql = "SELECT customer_id, name, phone, address, is_active FROM Customers WHERE phone = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, phone);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new Customer(
					rs.getInt("customer_id"),
					rs.getString("name"),
					rs.getString("phone"),
					rs.getString("address"),
					rs.getBoolean("is_active")
				);
			}
			return null; // no customer found with that phone number

		} catch (SQLException e) {
			System.err.println("Database error: " + e.getMessage());
			return null;
		}
	}
}
