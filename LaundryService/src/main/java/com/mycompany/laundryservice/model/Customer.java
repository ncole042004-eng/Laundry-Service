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

	// 1. FIELDS — private, matching the real Customers table columns exactly
	//    (confirmed against init_db.sql — note the real column names are
	//    "name" and "phone", not "full_name"/"phone_number" as guessed earlier).
	private int customerId;
	private String name;
	private String phone;
	private String address;
	private boolean isActive;

	// 2. CONSTRUCTOR — how you build a Customer object.
	//    This one takes every field, matching a full row from the database.
	public Customer(int customerId, String name, String phone, String address, boolean isActive) {
		this.customerId = customerId;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.isActive = isActive;
	}

	// A second constructor, for when you're creating a BRAND NEW customer
	// that doesn't have an ID yet (the database assigns that automatically
	// when you INSERT). This is a common, useful pattern — one constructor
	// for "loading an existing row," one for "creating a new one."
	public Customer(String name, String phone, String address) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.isActive = true; // new customers are active by default
	}

	// 3. GETTERS AND SETTERS — the only way outside code reads or changes
	//    the private fields above. This is called "encapsulation."
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
