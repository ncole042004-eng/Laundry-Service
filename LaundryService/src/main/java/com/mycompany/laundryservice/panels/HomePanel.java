/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.laundryservice.panels;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.mycompany.laundryservice.MainJFrame;
import com.mycompany.laundryservice.AppConstants;
import com.mycompany.laundryservice.database.DBConnection;
import com.mycompany.laundryservice.model.Customer;
import com.mycompany.laundryservice.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cral
 */
public class HomePanel extends javax.swing.JPanel {

	/**
	 * Creates new form HomePanel
	 */
	private MainJFrame mainFrame;

	public HomePanel() {
		initComponents();

		pnlMetrics.setBackground(new Color(249, 249, 249));

		double earningsToday = getEarningsToday();
		double earningsYesterday = getEarningsYesterday();
		boolean earningsUp = earningsToday >= earningsYesterday;

		pnlMetrics.add(createStatCard("payments.svg", 0x2655bd, "Earnings Today",
			"\u20b1" + String.format("%,.2f", earningsToday),
			formatTrend(earningsToday, earningsYesterday),
			earningsUp ? TREND_UP_COLOR : TREND_DOWN_COLOR,
			earningsUp));

		int ordersToday = getOrdersToday();
		int ordersYesterday = getOrdersYesterday();
		boolean ordersUp = ordersToday >= ordersYesterday;

		pnlMetrics.add(createStatCard("shopping_basket.svg", 0x2655bd, "Orders Today",
			String.valueOf(ordersToday),
			formatTrend(ordersToday, ordersYesterday),
			ordersUp ? TREND_UP_COLOR : TREND_DOWN_COLOR,
			ordersUp));

		pnlMetrics.add(createStatCard("check_circle.svg", 0x006781, "Claimed Today",
			String.valueOf(getClaimedToday()), null, 0, true));
		pnlMetrics.add(createStatCard("local_laundry_service.svg", 0x2a58c0, "Active Laundry",
			String.valueOf(getActiveLaundryCount()), null, 0, true));
		pnlMetrics.add(createStatCard("inventory_2.svg", 0x2e7d32, "Ready for Pickup",
			String.valueOf(getReadyForPickupCount()), null, 0, true));

		tblRecentOrders.setShowVerticalLines(false);
		tblRecentOrders.setShowHorizontalLines(true);
		tblRecentOrders.setGridColor(new Color(0xc3, 0xc6, 0xd7));
		tblRecentOrders.setRowHeight(48);

		tblRecentOrders.getColumnModel().getColumn(4).setCellRenderer(new ChipCellRenderer()); // Status
		tblRecentOrders.getColumnModel().getColumn(5).setCellRenderer(new ChipCellRenderer()); // Payment

		lblViewAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblViewAll.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				mainFrame.showCard(AppConstants.CARD_ORDER_LIST);
			}
		});

		startClock();

		tblRecentOrders.getTableHeader().setFont(new Font("Inter 18pt", Font.BOLD, 14));
		tblRecentOrders.setFont(new Font("Inter 18pt", Font.PLAIN, 14));

		javax.swing.table.DefaultTableCellRenderer claimRenderer = new javax.swing.table.DefaultTableCellRenderer() {
			@Override
			public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setFont(new Font("Inter 18pt", Font.BOLD, 14));
				setForeground(new Color(0x2655bd));
				setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

				return this;
			}
		};
		tblRecentOrders.getColumnModel().getColumn(0).setCellRenderer(claimRenderer);

		javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		tblRecentOrders.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		tblRecentOrders.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tblRecentOrders.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tblRecentOrders.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

		loadRecentOrdersTable();
	}

	private void startClock() {
		javax.swing.Timer clockTimer = new javax.swing.Timer(1000, evt -> {
			java.time.LocalDateTime now = java.time.LocalDateTime.now();
			lblCurrentTime.setText(now.format(java.time.format.DateTimeFormatter.ofPattern("h:mm:ss a")));
			lblCurrentDate.setText(now.format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")));
		});

		clockTimer.setInitialDelay(0);

		clockTimer.start();
	}

	private JPanel createStatCard(String iconName, int iconColor, String label,
		String value, String trendText, int trendColor, boolean trendUp) {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setBackground(new Color(249, 249, 249));
		card.setOpaque(true);

		JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
		topRow.setBackground(new Color(249, 249, 249));
		topRow.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblLabel = new JLabel(label);
		lblLabel.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
		lblLabel.setForeground(new Color(0x43, 0x46, 0x54));

		FlatSVGIcon icon = new FlatSVGIcon("icons/" + iconName, 18, 18);
		icon.setColorFilter(new FlatSVGIcon.ColorFilter(c -> new Color(iconColor)));
		JLabel lblIcon = new JLabel(icon);

		topRow.add(lblLabel);
		topRow.add(lblIcon);

		JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
		bottomRow.setBackground(new Color(249, 249, 249));
		bottomRow.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblValue = new JLabel(value);
		lblValue.setFont(new Font("Inter 18pt", Font.BOLD, 24));
		lblValue.setForeground(new Color(0x1a, 0x1c, 0x1c));
		bottomRow.add(lblValue);

		if (trendText != null) {
			FlatSVGIcon trendIcon = new FlatSVGIcon(
				trendUp ? "icons/trending_up.svg" : "icons/trending_down.svg", 14, 14);
			trendIcon.setColorFilter(new FlatSVGIcon.ColorFilter(c -> new Color(trendColor)));
			JLabel lblTrendIcon = new JLabel(trendIcon);
			bottomRow.add(lblTrendIcon);

			JLabel lblTrend = new JLabel(trendText);
			lblTrend.setFont(new Font("Inter 18pt", Font.PLAIN, 12));
			lblTrend.setForeground(new Color(trendColor));
			bottomRow.add(lblTrend);
		}

		card.add(topRow);
		card.add(bottomRow);
		return card;
	}

	public void setMainFrame(MainJFrame frame) {
		this.mainFrame = frame;
	}

	private double getEarningsToday() {
		String sql = "SELECT COALESCE(SUM(total_amount), 0) AS total FROM Orders WHERE DATE(order_date) = CURDATE()";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				return rs.getDouble("total");
			}
		} catch (SQLException e) {
			System.err.println("Failed to load earnings: " + e.getMessage());
		}
		return 0;
	}

	private int getOrdersToday() {
		return getCountWhere("DATE(order_date) = CURDATE()");
	}

	private int getClaimedToday() {
		return getCountWhere("order_status = 'Claimed' AND DATE(claimed_at) = CURDATE()");
	}

	private int getActiveLaundryCount() {
		return getCountWhere("order_status = 'Processing'");
	}

	private int getReadyForPickupCount() {
		return getCountWhere("order_status = 'Ready'");
	}

	private int getCountWhere(String whereClause) {
		String sql = "SELECT COUNT(*) AS total FROM Orders WHERE " + whereClause;
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				return rs.getInt("total");
			}
		} catch (SQLException e) {
			System.err.println("Failed to load count: " + e.getMessage());
		}
		return 0;
	}

	private double getEarningsYesterday() {
		String sql = "SELECT COALESCE(SUM(total_amount), 0) AS total FROM Orders "
			+ "WHERE DATE(order_date) = CURDATE() - INTERVAL 1 DAY";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				return rs.getDouble("total");
			}
		} catch (SQLException e) {
			System.err.println("Failed to load yesterday's earnings: " + e.getMessage());
		}
		return 0;
	}

	private int getOrdersYesterday() {
		return getCountWhere("DATE(order_date) = CURDATE() - INTERVAL 1 DAY");
	}

	private static final int TREND_UP_COLOR = 0x2e7d32;   // green
	private static final int TREND_DOWN_COLOR = 0xba1a1a; // red

	private String formatTrend(double current, double previous) {
		if (previous == 0) {
			return null;
		}
		double change = ((current - previous) / previous) * 100;
		if (change == 0) {
			return null;
		}
		String sign = change >= 0 ? "+" : "";
		return sign + String.format("%.0f", change) + "%";
	}

	private List<Order> getRecentOrders() {
		List<Order> orders = new ArrayList<>();
		String sql = "SELECT order_id, claim_number, customer_id, employee_id, service_id, "
			+ "order_date, ready_at, claimed_at, weight_kg, price_at_order, total_amount, "
			+ "payment_status, order_status, notes "
			+ "FROM Orders ORDER BY order_date DESC LIMIT 10";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				java.sql.Timestamp readyTs = rs.getTimestamp("ready_at");
				java.sql.Timestamp claimedTs = rs.getTimestamp("claimed_at");

				orders.add(new Order(
					rs.getInt("order_id"),
					rs.getString("claim_number"),
					rs.getInt("customer_id"),
					rs.getInt("employee_id"),
					rs.getInt("service_id"),
					rs.getTimestamp("order_date").toLocalDateTime(),
					readyTs != null ? readyTs.toLocalDateTime() : null,
					claimedTs != null ? claimedTs.toLocalDateTime() : null,
					rs.getDouble("weight_kg"),
					rs.getDouble("price_at_order"),
					rs.getDouble("total_amount"),
					rs.getString("payment_status"),
					rs.getString("order_status"),
					rs.getString("notes")
				));
			}
		} catch (SQLException e) {
			System.err.println("Failed to load recent orders: " + e.getMessage());
		}
		return orders;
	}

	private void loadRecentOrdersTable() {
		DefaultTableModel model = (DefaultTableModel) tblRecentOrders.getModel();
		model.setRowCount(0); // clear existing rows before reloading

		for (Order order : getRecentOrders()) {
			Customer customer = findCustomerById(order.getCustomerId()); // write this the same way as findCustomerByPhone, but WHERE customer_id = ?

			model.addRow(new Object[]{
				order.getClaimNumber(),
				customer != null ? customer.getName() : "Unknown",
				customer != null ? customer.getPhone() : "",
				order.getWeightKg(),
				order.getOrderStatus(),
				order.getPaymentStatus(),
				order.getNotes(),
				"\u20b1" + String.format("%,.2f", order.getTotalAmount())
			});
		}
	}

	private Customer findCustomerById(int id) {
		String sql = "SELECT * FROM Customers WHERE customer_id = ?";
		try (java.sql.Connection conn = DBConnection.getConnection(); java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			try (java.sql.ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Customer(
						rs.getInt("customer_id"),
						rs.getString("name"),
						rs.getString("phone"),
						rs.getString("address"),
						rs.getInt("is_active") == 1
					);

				}
			}
		} catch (java.sql.SQLException e) {
			System.err.println("Failed to find customer: " + e.getMessage());
		}
		return null;
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                pnlHeader = new javax.swing.JPanel();
                pnlHeaderLeft = new javax.swing.JPanel();
                lblPageTitle = new javax.swing.JLabel();
                lblPageSubtitle = new javax.swing.JLabel();
                pnlHeaderRight = new javax.swing.JPanel();
                lblCurrentTime = new javax.swing.JLabel();
                lblCurrentDate = new javax.swing.JLabel();
                pnlBody = new javax.swing.JPanel();
                pnlMetrics = new javax.swing.JPanel();
                pnlTable = new javax.swing.JPanel();
                pnlTableHeader = new javax.swing.JPanel();
                lblRecentOrders = new javax.swing.JLabel();
                lblViewAll = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                tblRecentOrders = new javax.swing.JTable();

                setBackground(new java.awt.Color(249, 249, 249));
                setLayout(new java.awt.BorderLayout());

                pnlHeader.setBackground(new java.awt.Color(249, 249, 249));
                pnlHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 24, 24, 24));
                pnlHeader.setPreferredSize(new java.awt.Dimension(1003, 100));
                pnlHeader.setLayout(new java.awt.BorderLayout());

                pnlHeaderLeft.setBackground(new java.awt.Color(249, 249, 249));
                pnlHeaderLeft.setPreferredSize(new java.awt.Dimension(400, 100));
                pnlHeaderLeft.setLayout(new javax.swing.BoxLayout(pnlHeaderLeft, javax.swing.BoxLayout.Y_AXIS));

                lblPageTitle.setFont(new java.awt.Font("Inter 18pt", 1, 28)); // NOI18N
                lblPageTitle.setForeground(new java.awt.Color(26, 28, 28));
                lblPageTitle.setText("Operations Overview");
                pnlHeaderLeft.add(lblPageTitle);

                lblPageSubtitle.setFont(new java.awt.Font("Inter 18pt", 0, 14)); // NOI18N
                lblPageSubtitle.setForeground(new java.awt.Color(67, 70, 84));
                lblPageSubtitle.setText("Today's overview and recent activity");
                pnlHeaderLeft.add(lblPageSubtitle);

                pnlHeader.add(pnlHeaderLeft, java.awt.BorderLayout.WEST);

                pnlHeaderRight.setBackground(new java.awt.Color(249, 249, 249));
                pnlHeaderRight.setPreferredSize(new java.awt.Dimension(400, 100));
                pnlHeaderRight.setLayout(new javax.swing.BoxLayout(pnlHeaderRight, javax.swing.BoxLayout.Y_AXIS));

                lblCurrentTime.setFont(new java.awt.Font("Inter 18pt", 1, 20)); // NOI18N
                lblCurrentTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                lblCurrentTime.setText("10:45 AM");
                lblCurrentTime.setAlignmentX(1.0F);
                lblCurrentTime.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                pnlHeaderRight.add(lblCurrentTime);

                lblCurrentDate.setFont(new java.awt.Font("Inter 18pt", 0, 14)); // NOI18N
                lblCurrentDate.setForeground(new java.awt.Color(67, 70, 84));
                lblCurrentDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                lblCurrentDate.setText("Thursday, July 2, 2026");
                lblCurrentDate.setAlignmentX(1.0F);
                lblCurrentDate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                pnlHeaderRight.add(lblCurrentDate);

                pnlHeader.add(pnlHeaderRight, java.awt.BorderLayout.EAST);

                add(pnlHeader, java.awt.BorderLayout.PAGE_START);

                pnlBody.setBackground(new java.awt.Color(249, 249, 249));
                pnlBody.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 24, 24, 24));
                pnlBody.setLayout(new java.awt.BorderLayout());

                pnlMetrics.setBackground(new java.awt.Color(249, 249, 249));
                pnlMetrics.setPreferredSize(new java.awt.Dimension(1188, 65));
                pnlMetrics.setLayout(new java.awt.GridLayout(1, 5, 20, 0));
                pnlBody.add(pnlMetrics, java.awt.BorderLayout.NORTH);

                pnlTable.setBackground(new java.awt.Color(249, 249, 249));
                pnlTable.setLayout(new java.awt.BorderLayout());

                pnlTableHeader.setBackground(new java.awt.Color(249, 249, 249));
                pnlTableHeader.setPreferredSize(new java.awt.Dimension(100, 100));
                pnlTableHeader.setLayout(new java.awt.BorderLayout());

                lblRecentOrders.setFont(new java.awt.Font("Inter 18pt", 1, 20)); // NOI18N
                lblRecentOrders.setText("Recent Orders");
                pnlTableHeader.add(lblRecentOrders, java.awt.BorderLayout.LINE_START);

                lblViewAll.setFont(new java.awt.Font("Inter 18pt", 1, 14)); // NOI18N
                lblViewAll.setForeground(new java.awt.Color(38, 85, 189));
                lblViewAll.setText("View All");
                pnlTableHeader.add(lblViewAll, java.awt.BorderLayout.LINE_END);

                pnlTable.add(pnlTableHeader, java.awt.BorderLayout.PAGE_START);

                jScrollPane1.setBackground(new java.awt.Color(249, 249, 249));

                tblRecentOrders.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null}
                        },
                        new String [] {
                                "Claim Number", "Customer", "Phone Number", "Weight (kg)", "Status", "Payment", "Notes", "Total Amount"
                        }
                ));
                jScrollPane1.setViewportView(tblRecentOrders);

                pnlTable.add(jScrollPane1, java.awt.BorderLayout.CENTER);

                pnlBody.add(pnlTable, java.awt.BorderLayout.CENTER);

                add(pnlBody, java.awt.BorderLayout.CENTER);
        }// </editor-fold>//GEN-END:initComponents

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JLabel lblCurrentDate;
        private javax.swing.JLabel lblCurrentTime;
        private javax.swing.JLabel lblPageSubtitle;
        private javax.swing.JLabel lblPageTitle;
        private javax.swing.JLabel lblRecentOrders;
        private javax.swing.JLabel lblViewAll;
        private javax.swing.JPanel pnlBody;
        private javax.swing.JPanel pnlHeader;
        private javax.swing.JPanel pnlHeaderLeft;
        private javax.swing.JPanel pnlHeaderRight;
        private javax.swing.JPanel pnlMetrics;
        private javax.swing.JPanel pnlTable;
        private javax.swing.JPanel pnlTableHeader;
        private javax.swing.JTable tblRecentOrders;
        // End of variables declaration//GEN-END:variables
}
