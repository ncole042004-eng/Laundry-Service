/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laundryservice.model;

/**
 *
 * @author Cral
 */
import java.time.LocalDateTime;

public class Order {

	private int orderId;
	private String claimNumber;
	private int customerId;
	private int employeeId;
	private int serviceId;
	private LocalDateTime orderDate;
	private LocalDateTime readyAt;      // null until status becomes "Ready"
	private LocalDateTime claimedAt;    // null until status becomes "Claimed"
	private double weightKg;
	private double priceAtOrder;        // the service's fixed price at the time of the order
	private double totalAmount;         // priceAtOrder + any additional charges
	private String paymentStatus;       // "Unpaid" / "Paid" — a REAL, separate column
	private String orderStatus;         // "Pending" / "Processing" / "Ready" / "Claimed" / "Cancelled"
	private String notes;

	public Order(int orderId, String claimNumber, int customerId, int employeeId, int serviceId,
		LocalDateTime orderDate, LocalDateTime readyAt, LocalDateTime claimedAt,
		double weightKg, double priceAtOrder, double totalAmount,
		String paymentStatus, String orderStatus, String notes) {
		this.orderId = orderId;
		this.claimNumber = claimNumber;
		this.customerId = customerId;
		this.employeeId = employeeId;
		this.serviceId = serviceId;
		this.orderDate = orderDate;
		this.readyAt = readyAt;
		this.claimedAt = claimedAt;
		this.weightKg = weightKg;
		this.priceAtOrder = priceAtOrder;
		this.totalAmount = totalAmount;
		this.paymentStatus = paymentStatus;
		this.orderStatus = orderStatus;
		this.notes = notes;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getClaimNumber() {
		return claimNumber;
	}

	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getReadyAt() {
		return readyAt;
	}

	public void setReadyAt(LocalDateTime readyAt) {
		this.readyAt = readyAt;
	}

	public LocalDateTime getClaimedAt() {
		return claimedAt;
	}

	public void setClaimedAt(LocalDateTime claimedAt) {
		this.claimedAt = claimedAt;
	}

	public double getWeightKg() {
		return weightKg;
	}

	public void setWeightKg(double weightKg) {
		this.weightKg = weightKg;
	}

	public double getPriceAtOrder() {
		return priceAtOrder;
	}

	public void setPriceAtOrder(double priceAtOrder) {
		this.priceAtOrder = priceAtOrder;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
