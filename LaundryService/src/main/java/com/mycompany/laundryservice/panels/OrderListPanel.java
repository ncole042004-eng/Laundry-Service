/*
 * OrderListPanel.java
 * Fully programmatic UI — no NetBeans Designer GEN block.
 */
package com.mycompany.laundryservice.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.mycompany.laundryservice.database.DBConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Order List Panel — displays all orders with search, sort, and action buttons.
 *
 * @author Cral
 */
public class OrderListPanel extends JPanel {

	// -------------------------------------------------------------------------
	// Components
	// -------------------------------------------------------------------------
	private JLabel lblTitle;
	private JLabel lblSubtitle;

	private JPanel pnlControls;
	private JLabel lblSearchOrders;
	private JTextField txtSearch;
	private JLabel lblSearchBy;
	private JComboBox<String> cboSearchBy;
	private JButton btnSearch;
	private JButton btnRefresh;
	private JComboBox<String> cboOrderState; // Added for Active/Cancelled view
	private JLabel lblSortBy;
	private JComboBox<String> cboSortBy;
	private JButton btnSort;

	private JTable tblOrders;
	private JScrollPane jScrollPane1;

	private JPanel pnlFooter;
	private JLabel lblTotalOrders;
	private JButton btnCancelOrder;
	private JButton btnViewDetails;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	public OrderListPanel() {
		initComponents();
		setupStyles();
		setupTableStyles();
		loadTableData();
	}

	// -------------------------------------------------------------------------
	// UI Construction
	// -------------------------------------------------------------------------
	private void initComponents() {
		setBackground(new Color(249, 249, 249));
		setLayout(new BorderLayout());

		// ── Page header (matches HomePanel spacing) ───────────────────────────
		JPanel pnlHeader = new JPanel();
		pnlHeader.setBackground(new Color(249, 249, 249));
		pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
		pnlHeader.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

		lblTitle = new JLabel("Order List");
		lblSubtitle = new JLabel("Manage and track all laundry service orders.");
		pnlHeader.add(lblTitle);
		pnlHeader.add(lblSubtitle);
		add(pnlHeader, BorderLayout.NORTH);

		// ── Body wrapper (matches HomePanel spacing) ──────────────────────────
		JPanel pnlBody = new JPanel(new BorderLayout());
		pnlBody.setBackground(new Color(249, 249, 249));
		pnlBody.setBorder(BorderFactory.createEmptyBorder(0, 24, 24, 24));
		add(pnlBody, BorderLayout.CENTER);

		// White container for controls, table, and footer
		JPanel pnlContent = new JPanel(new BorderLayout());
		pnlContent.setBackground(Color.WHITE);
		pnlBody.add(pnlContent, BorderLayout.CENTER);

		// Control bar
		pnlControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
		pnlControls.setBackground(Color.WHITE);

		lblSearchOrders = new JLabel("Search Orders");
		txtSearch = new JTextField(28);
		lblSearchBy = new JLabel("Search By");
		cboSearchBy = new JComboBox<>(new String[] { "Customer Name", "Claim Number", "Status" });
		btnSearch = new JButton("Search");
		btnRefresh = new JButton();
		cboOrderState = new JComboBox<>(new String[] { "Active Orders", "Cancelled Orders", "All Orders" });
		lblSortBy = new JLabel("Sort By");
		cboSortBy = new JComboBox<>(new String[] { "Order Date", "Status", "Amount" });
		btnSort = new JButton("Sort");

		pnlControls.add(lblSearchOrders);
		pnlControls.add(txtSearch);
		pnlControls.add(lblSearchBy);
		pnlControls.add(cboSearchBy);
		pnlControls.add(btnSearch);
		pnlControls.add(btnRefresh);
		pnlControls.add(cboOrderState);
		pnlControls.add(lblSortBy);
		pnlControls.add(cboSortBy);
		pnlControls.add(btnSort);
		pnlContent.add(pnlControls, BorderLayout.NORTH);

		// ── Table ─────────────────────────────────────────────────────────────
		JPanel pnlTableWrapper = new JPanel(new BorderLayout());
		pnlTableWrapper.setBackground(Color.WHITE);
		pnlContent.add(pnlTableWrapper, BorderLayout.CENTER);

		String[] columns = {
				"Order ID", "Claim Number", "Employee", "Customer", "Phone", "Address",
				"Weight (kg)", "Status", "Payment", "Notes", "Total Amount"
		};

		DefaultTableModel model = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tblOrders = new JTable(model);
		tblOrders.setBackground(new Color(249, 249, 249));
		jScrollPane1 = new JScrollPane(tblOrders);
		jScrollPane1.getViewport().setBackground(new Color(249, 249, 249));
		jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
		pnlTableWrapper.add(jScrollPane1, BorderLayout.CENTER);

		// ── Footer ────────────────────────────────────────────────────────────
		pnlFooter = new JPanel(new BorderLayout());
		pnlFooter.setBackground(Color.WHITE);
		pnlContent.add(pnlFooter, BorderLayout.SOUTH);

		lblTotalOrders = new JLabel("Total Orders: 0");
		pnlFooter.add(lblTotalOrders, BorderLayout.WEST);

		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
		pnlButtons.setBackground(Color.WHITE);
		btnCancelOrder = new JButton("Cancel Order");
		btnViewDetails = new JButton("View Details");
		pnlButtons.add(btnCancelOrder);
		pnlButtons.add(btnViewDetails);
		pnlFooter.add(pnlButtons, BorderLayout.EAST);

		// ── Wire events ───────────────────────────────────────────────────────
		btnSearch.addActionListener(evt -> btnSearchActionPerformed());
		txtSearch.addActionListener(evt -> btnSearchActionPerformed()); // Search on Enter
		btnRefresh.addActionListener(evt -> btnRefreshActionPerformed());
		cboOrderState.addActionListener(evt -> loadTableData()); // Reload when view changes
		btnSort.addActionListener(evt -> btnSortActionPerformed());
		btnCancelOrder.addActionListener(evt -> btnCancelOrderActionPerformed());
		btnViewDetails.addActionListener(evt -> btnViewDetailsActionPerformed());
	}

	// -------------------------------------------------------------------------
	// Styling
	// -------------------------------------------------------------------------
	private void setupStyles() {
		// Page header labels
		lblTitle.setFont(new Font("Inter 18pt", Font.BOLD, 28)); // Adjusted to match HomePanel (28pt)
		lblTitle.setForeground(new Color(26, 28, 28));

		lblSubtitle.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
		lblSubtitle.setForeground(new Color(67, 70, 84));

		// Control bar
		pnlControls.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(195, 198, 215)),
				BorderFactory.createEmptyBorder(8, 24, 8, 24)));

		lblSearchOrders.setFont(new Font("Inter 18pt", Font.BOLD, 12));
		lblSearchOrders.setForeground(new Color(67, 70, 84));

		lblSearchBy.setFont(new Font("Inter 18pt", Font.BOLD, 12));
		lblSearchBy.setForeground(new Color(67, 70, 84));

		lblSortBy.setFont(new Font("Inter 18pt", Font.BOLD, 12));
		lblSortBy.setForeground(new Color(67, 70, 84));

		txtSearch.setPreferredSize(new Dimension(320, 44));
		txtSearch.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
		txtSearch.putClientProperty("JTextField.placeholderText", "Enter search term...");

		cboSearchBy.setPreferredSize(new Dimension(160, 44));
		cboSearchBy.setFont(new Font("Inter 18pt", Font.PLAIN, 14));

		cboOrderState.setPreferredSize(new Dimension(160, 44));
		cboOrderState.setFont(new Font("Inter 18pt", Font.PLAIN, 14));

		cboSortBy.setPreferredSize(new Dimension(160, 44));
		cboSortBy.setFont(new Font("Inter 18pt", Font.PLAIN, 14));

		// Search button — Primary blue
		btnSearch.setBackground(new Color(38, 85, 189));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
		btnSearch.setPreferredSize(new Dimension(90, 44));
		btnSearch.putClientProperty("JButton.buttonType", "roundRect");
		btnSearch.setFocusPainted(false);

		// Refresh button — icon only
		btnRefresh.setPreferredSize(new Dimension(44, 44));
		btnRefresh.putClientProperty("JButton.buttonType", "roundRect");
		btnRefresh.setFocusPainted(false);
		try {
			FlatSVGIcon refreshIcon = new FlatSVGIcon("icons/published_with_changes.svg", 20, 20);
			refreshIcon.setColorFilter(new FlatSVGIcon.ColorFilter(c -> new Color(67, 70, 84)));
			btnRefresh.setIcon(refreshIcon);
		} catch (Exception ignored) {
			btnRefresh.setText("↺");
		}

		// Sort button
		btnSort.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
		btnSort.setPreferredSize(new Dimension(90, 44));
		btnSort.putClientProperty("JButton.buttonType", "roundRect");
		btnSort.setFocusPainted(false);
		try {
			FlatSVGIcon sortIcon = new FlatSVGIcon("icons/filter_list.svg", 16, 16);
			sortIcon.setColorFilter(new FlatSVGIcon.ColorFilter(c -> new Color(26, 28, 28)));
			btnSort.setIcon(sortIcon);
		} catch (Exception ignored) {
		}

		// Footer
		pnlFooter.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(195, 198, 215)),
				BorderFactory.createEmptyBorder(12, 24, 12, 24)));

		lblTotalOrders.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
		lblTotalOrders.setForeground(new Color(67, 70, 84));

		// Cancel Order button — Error red
		btnCancelOrder.setBackground(new Color(186, 26, 26));
		btnCancelOrder.setForeground(Color.WHITE);
		btnCancelOrder.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
		btnCancelOrder.setPreferredSize(new Dimension(140, 40));
		btnCancelOrder.putClientProperty("JButton.buttonType", "roundRect");
		btnCancelOrder.setFocusPainted(false);

		// View Details button — Primary blue
		btnViewDetails.setBackground(new Color(38, 85, 189));
		btnViewDetails.setForeground(Color.WHITE);
		btnViewDetails.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
		btnViewDetails.setPreferredSize(new Dimension(140, 40));
		btnViewDetails.putClientProperty("JButton.buttonType", "roundRect");
		btnViewDetails.setFocusPainted(false);
	}

	private void setupTableStyles() {
		// Header row
		tblOrders.getTableHeader().setBackground(new Color(0x45, 0x6f, 0xd7));
		tblOrders.getTableHeader().setForeground(Color.WHITE);
		tblOrders.getTableHeader().setFont(new Font("Inter 18pt", Font.BOLD, 12));
		((DefaultTableCellRenderer) tblOrders.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		// Rows
		tblOrders.setRowHeight(48);
		tblOrders.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
		tblOrders.setShowVerticalLines(false);
		tblOrders.setShowHorizontalLines(true);
		tblOrders.setGridColor(new Color(195, 198, 215));

		// Default center renderer for all columns
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tblOrders.getColumnCount(); i++) {
			tblOrders.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		// Column 1 — Claim Number: bold blue and centered
		DefaultTableCellRenderer claimRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setForeground(isSelected ? Color.WHITE : new Color(38, 85, 189));
				setFont(new Font("Inter 18pt", Font.BOLD, 14));
				setHorizontalAlignment(JLabel.CENTER);
				return this;
			}
		};
		tblOrders.getColumnModel().getColumn(1).setCellRenderer(claimRenderer);

		// Column 7 — Status chip
		tblOrders.getColumnModel().getColumn(7).setCellRenderer(new ChipCellRenderer());
		// Column 8 — Payment chip
		tblOrders.getColumnModel().getColumn(8).setCellRenderer(new ChipCellRenderer());

		// Column 9 — Notes: italic, muted, and LEFT aligned (only column not centered)
		DefaultTableCellRenderer notesRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setFont(new Font("Inter 18pt", Font.ITALIC, 12));
				if (!isSelected) {
					setForeground(new Color(67, 70, 84));
				}
				setHorizontalAlignment(JLabel.LEFT);
				return this;
			}
		};
		tblOrders.getColumnModel().getColumn(9).setCellRenderer(notesRenderer);
	}

	// -------------------------------------------------------------------------
	// Chip renderer (Status & Payment columns)
	// -------------------------------------------------------------------------
	private static class ChipCellRenderer extends DefaultTableCellRenderer {

		private Color chipBg = Color.WHITE;

		public ChipCellRenderer() {
			setHorizontalAlignment(JLabel.CENTER);
			setOpaque(false);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			String text = value != null ? value.toString() : "";
			setFont(new Font("Inter 18pt", Font.BOLD, 12));

			switch (text.toLowerCase()) {
				case "pending" -> {
					chipBg = new Color(255, 243, 224);
					setForeground(new Color(239, 108, 0));
				}
				case "processing" -> {
					chipBg = new Color(59, 208, 253, 77);
					setForeground(new Color(0, 86, 108));
				}
				case "ready" -> {
					chipBg = new Color(59, 208, 253, 77);
					setForeground(new Color(0, 86, 108));
				}
				case "claimed", "paid" -> {
					chipBg = new Color(232, 245, 233);
					setForeground(new Color(46, 125, 50));
				}
				case "unpaid" -> {
					chipBg = new Color(255, 218, 214);
					setForeground(new Color(186, 26, 26));
				}
				case "cancelled" -> {
					chipBg = new Color(226, 226, 226);
					setForeground(new Color(67, 70, 84));
				}
				default -> {
					chipBg = new Color(226, 226, 226);
					setForeground(new Color(26, 28, 28));
				}
			}
			return this;
		}

		@Override
		protected void paintComponent(java.awt.Graphics g) {
			java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
			g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
					java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

			java.awt.FontMetrics fm = g2.getFontMetrics(getFont());
			int textWidth = fm.stringWidth(getText());
			int chipHeight = getHeight() - 16;
			int chipWidth = textWidth + 20;
			int x = (getWidth() - chipWidth) / 2;
			int y = (getHeight() - chipHeight) / 2;

			g2.setColor(chipBg);
			g2.fillRoundRect(x, y, chipWidth, chipHeight, chipHeight, chipHeight);
			g2.dispose();

			super.paintComponent(g);
		}
	}

	// -------------------------------------------------------------------------
	// Data
	// -------------------------------------------------------------------------
	public void refreshData() {
		loadTableData();
	}

	private void loadTableData() {
		DefaultTableModel model = (DefaultTableModel) tblOrders.getModel();
		model.setRowCount(0);

		String searchBy = cboSearchBy.getSelectedItem() != null ? cboSearchBy.getSelectedItem().toString() : "";
		String searchTxt = txtSearch.getText().trim();
		String stateFilter = cboOrderState.getSelectedItem() != null ? cboOrderState.getSelectedItem().toString() : "";

		StringBuilder sql = new StringBuilder(
				"SELECT o.order_id, o.claim_number, c.name as customer_name, c.phone as customer_phone, "
						+ "c.address as customer_address, o.weight_kg, o.order_status, o.payment_status, "
						+ "e.name as employee_name, o.notes, o.total_amount "
						+ "FROM Orders o "
						+ "JOIN Customers c ON o.customer_id = c.customer_id "
						+ "LEFT JOIN Employees e ON o.employee_id = e.employee_id "
						+ "WHERE 1=1 ");

		if (stateFilter.equals("Active Orders")) {
			sql.append("AND o.order_status != 'Cancelled' ");
		} else if (stateFilter.equals("Cancelled Orders")) {
			sql.append("AND o.order_status = 'Cancelled' ");
		}

		if (!searchTxt.isEmpty()) {
			if (searchBy.equals("Customer Name")) {
				sql.append("AND c.name LIKE ? ");
			} else if (searchBy.equals("Claim Number")) {
				sql.append("AND o.claim_number LIKE ? ");
			} else if (searchBy.equals("Status")) {
				sql.append("AND o.order_status LIKE ? ");
			}
		}

		sql.append("ORDER BY o.order_date DESC");

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
			if (!searchTxt.isEmpty()) {
				stmt.setString(1, "%" + searchTxt + "%");
			}

			try (ResultSet rs = stmt.executeQuery()) {
				int count = 0;
				while (rs.next()) {
					String orderIdFormat = "ORD-" + String.format("%04d", rs.getInt("order_id"));
					model.addRow(new Object[] {
							orderIdFormat,
							rs.getString("claim_number"),
							rs.getString("employee_name") != null ? rs.getString("employee_name") : "",
							rs.getString("customer_name"),
							rs.getString("customer_phone") != null ? rs.getString("customer_phone") : "",
							rs.getString("customer_address") != null ? rs.getString("customer_address") : "",
							rs.getDouble("weight_kg"),
							rs.getString("order_status"),
							rs.getString("payment_status"),
							rs.getString("notes") != null ? rs.getString("notes") : "",
							"\u20b1" + String.format("%,.2f", rs.getDouble("total_amount"))
					});
					count++;
				}
				lblTotalOrders.setText("Total Orders: " + count);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
		}
	}

	// -------------------------------------------------------------------------
	// Event Handlers
	// -------------------------------------------------------------------------
	private void btnSearchActionPerformed() {
		String searchText = txtSearch.getText().trim().toLowerCase();
		String searchType = cboSearchBy.getSelectedItem().toString();

		// Reload full data then filter in-memory
		loadTableData();

		if (searchText.isEmpty()) {
			return;
		}

		DefaultTableModel model = (DefaultTableModel) tblOrders.getModel();
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			String cell = switch (searchType) {
				case "Customer Name" ->
					model.getValueAt(i, 3).toString().toLowerCase();
				case "Claim Number" ->
					model.getValueAt(i, 1).toString().toLowerCase();
				case "Status" ->
					model.getValueAt(i, 7).toString().toLowerCase();
				default ->
					"";
			};
			if (!cell.contains(searchText)) {
				model.removeRow(i);
			}
		}
		lblTotalOrders.setText("Total Orders: " + model.getRowCount());
	}

	private void btnRefreshActionPerformed() {
		txtSearch.setText("");
		loadTableData();
	}

	private void btnSortActionPerformed() {
		String sortBy = cboSortBy.getSelectedItem().toString();
		DefaultTableModel model = (DefaultTableModel) tblOrders.getModel();

		// Snapshot rows
		List<Vector> rows = new ArrayList<>();
		for (int i = 0; i < model.getRowCount(); i++) {
			rows.add((Vector) model.getDataVector().get(i));
		}

		rows.sort((a, b) -> switch (sortBy) {
			case "Status" ->
				a.get(7).toString().compareTo(b.get(7).toString());
			case "Amount" -> {
				double da = Double.parseDouble(a.get(10).toString().replace("\u20b1", "").replace(",", ""));
				double db = Double.parseDouble(b.get(10).toString().replace("\u20b1", "").replace(",", ""));
				yield Double.compare(da, db);
			}
			default ->
				0; // Order Date — already DESC from DB
		});

		model.setRowCount(0);
		for (Vector row : rows) {
			model.addRow(row);
		}
	}

	private void btnCancelOrderActionPerformed() {
		int selectedRow = tblOrders.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this,
					"Please select an order to cancel.",
					"No Selection", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String claimNumber = tblOrders.getValueAt(selectedRow, 1).toString();
		String currentStatus = tblOrders.getValueAt(selectedRow, 7).toString();

		if (currentStatus.equalsIgnoreCase("Claimed") || currentStatus.equalsIgnoreCase("Cancelled")) {
			JOptionPane.showMessageDialog(this,
					"Cannot cancel an order that is already " + currentStatus + ".",
					"Action Not Allowed", JOptionPane.ERROR_MESSAGE);
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this,
				"Are you sure you want to cancel order: " + claimNumber + "?",
				"Confirm Cancellation", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			String query = "UPDATE Orders SET order_status = 'Cancelled', cancelled_at = NOW() WHERE claim_number = ?";
			try (Connection conn = DBConnection.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setString(1, claimNumber);
				pstmt.executeUpdate();
				loadTableData();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this,
						"Error cancelling order: " + e.getMessage(),
						"Database Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void btnViewDetailsActionPerformed() {
		int selectedRow = tblOrders.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this,
				"Please select an order to view.",
				"No Selection", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String details
			= "Order ID:      " + tblOrders.getValueAt(selectedRow, 0) + "\n"
			+ "Claim Number:  " + tblOrders.getValueAt(selectedRow, 1) + "\n"
			+ "Employee:      " + tblOrders.getValueAt(selectedRow, 2) + "\n"
			+ "Customer:      " + tblOrders.getValueAt(selectedRow, 3) + "\n"
			+ "Phone:         " + tblOrders.getValueAt(selectedRow, 4) + "\n"
			+ "Address:       " + tblOrders.getValueAt(selectedRow, 5) + "\n"
			+ "Weight (kg):   " + tblOrders.getValueAt(selectedRow, 6) + "\n"
			+ "Status:        " + tblOrders.getValueAt(selectedRow, 7) + "\n"
			+ "Payment:       " + tblOrders.getValueAt(selectedRow, 8) + "\n"
			+ "Notes:         " + tblOrders.getValueAt(selectedRow, 9) + "\n"
			+ "Total Amount:  " + tblOrders.getValueAt(selectedRow, 10);

		JOptionPane.showMessageDialog(this,
			details,
			"Order Details — " + tblOrders.getValueAt(selectedRow, 1),
			JOptionPane.INFORMATION_MESSAGE);
	}
}
