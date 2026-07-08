/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.laundryservice.panels;
import com.mycompany.laundryservice.MainJFrame;
import com.mycompany.laundryservice.database.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.FontMetrics;
 
public class ReportsPanel extends javax.swing.JPanel {
        private MainJFrame mainFrame;

	/**
	 * Creates new form ReportsPanel
	 */
	public ReportsPanel() {
		initComponents();
                txtStartDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(createDateMask()));
                txtEndDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(createDateMask()));
                applyDashboardStyle();
                refreshData();
                styleTable();
                styleOrderStatusTable(); 
                tblOrderStatus.getColumnModel()
                    .getColumn(0)
                    .setCellRenderer(new StatusChipRenderer());
                tblOrderStatus.repaint();
                txtStartDate.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
                txtEndDate.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
                lblHighestMonth.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                lblHighestMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

                lblLowestMonth.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                lblLowestMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                
                
        }
        private void styleTable() {

            tblEmployeeOrders.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
            tblEmployeeOrders.getTableHeader().setBackground(new Color(0x45, 0x6F, 0xD7));
            tblEmployeeOrders.getTableHeader().setForeground(Color.WHITE);

            tblEmployeeOrders.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            tblEmployeeOrders.setRowHeight(32);

            tblEmployeeOrders.setShowVerticalLines(false);
            tblEmployeeOrders.setShowHorizontalLines(true);
            tblEmployeeOrders.setGridColor(new Color(0xC3, 0xC6, 0xD7));
            tblEmployeeOrders.getTableHeader().setFont(new Font("Inter 18pt", Font.BOLD, 14));
            tblEmployeeOrders.getTableHeader().setBackground(new Color(0x45, 0x6F, 0xD7));
            tblEmployeeOrders.getTableHeader().setForeground(Color.WHITE);

            tblEmployeeOrders.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
            tblEmployeeOrders.setRowHeight(48);

            tblEmployeeOrders.setShowVerticalLines(false);
            tblEmployeeOrders.setShowHorizontalLines(true);
            tblEmployeeOrders.setGridColor(new Color(0xC3, 0xC6, 0xD7));
            
}
    private void styleOrderStatusTable() {

    tblOrderStatus.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    tblOrderStatus.getTableHeader().setBackground(new Color(0x45, 0x6F, 0xD7));
    tblOrderStatus.getTableHeader().setForeground(Color.WHITE);
    tblOrderStatus.getTableHeader().setReorderingAllowed(false);
    tblOrderStatus.getTableHeader().setResizingAllowed(false);

    tblOrderStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    tblOrderStatus.setRowHeight(30);

    tblOrderStatus.setShowVerticalLines(false);
    tblOrderStatus.setShowHorizontalLines(true);
    tblOrderStatus.setGridColor(new Color(0xC3, 0xC6, 0xD7));
    tblOrderStatus.setSelectionBackground(new Color(0xE8F0FE));
    tblOrderStatus.setSelectionForeground(Color.BLACK);
    tblOrderStatus.getTableHeader().setFont(new Font("Inter 18pt", Font.BOLD, 14));
    tblOrderStatus.getTableHeader().setBackground(new Color(0x45, 0x6F, 0xD7));
    tblOrderStatus.getTableHeader().setForeground(Color.WHITE);

    tblOrderStatus.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
    tblOrderStatus.setRowHeight(48);

    tblOrderStatus.setShowVerticalLines(false);
    tblOrderStatus.setShowHorizontalLines(true);
    tblOrderStatus.setGridColor(new Color(0xC3, 0xC6, 0xD7));
}
    private class StatusChipRenderer extends DefaultTableCellRenderer {

    private Color chipBg = Color.WHITE;

    public StatusChipRenderer() {
        setHorizontalAlignment(JLabel.CENTER);
        setOpaque(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        String text = value == null ? "" : value.toString();

        setFont(new Font("Segoe UI", Font.BOLD, 12));

        switch (text.toLowerCase()) {

            case "pending":
                chipBg = new Color(255, 243, 224);      // Light orange
                setForeground(new Color(239, 107, 0));  // Orange text
                break;

            case "processing":
                chipBg = new Color(192, 237, 250);      // Light cyan
                setForeground(new Color(0, 85, 108));   // Dark cyan
                break;

            case "ready":
                chipBg = new Color(232, 245, 233);      // Light green
                setForeground(new Color(47, 124, 49));  // Green
                break;

            case "claimed":
                chipBg = new Color(220, 245, 224);      // Soft green
                setForeground(new Color(34, 139, 34));  // Dark green
                break;

            default:
                chipBg = Color.WHITE;
                setForeground(Color.BLACK);
        }

        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        FontMetrics fm = g2.getFontMetrics(getFont());

        int textWidth = fm.stringWidth(getText());

        int chipHeight = getHeight() - 14;
        int chipWidth = textWidth + 28;

        int x = (getWidth() - chipWidth) / 2;
        int y = (getHeight() - chipHeight) / 2;

        g2.setColor(chipBg);
        g2.fillRoundRect(x, y, chipWidth, chipHeight,
                chipHeight, chipHeight);

        g2.dispose();

        super.paintComponent(g);
    }
}
        private void applyDashboardStyle() {
        // Page title - bold black
        jLabel1.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 30));
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));

        // Total Revenue label
        jLabel5.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        jLabel5.setForeground(new java.awt.Color(15, 23, 42));

        // Revenue amount - big blue
        jLabel4.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 40));
        jLabel4.setForeground(new java.awt.Color(74, 99, 231));

        // Highest / Lowest - big bold black, matches screenshot
        lblHighestMonth.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));
        lblHighestMonth.setForeground(new java.awt.Color(15, 23, 42));
        lblLowestMonth.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));
        lblLowestMonth.setForeground(new java.awt.Color(15, 23, 42));

        // Trend text - small gray with arrow
        lblTrend.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblTrend.setForeground(new java.awt.Color(100, 116, 139));

        // Section headers
        jLabel3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        jLabel3.setForeground(new java.awt.Color(15, 23, 42));
        jLabel2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        jLabel2.setForeground(new java.awt.Color(15, 23, 42));

        // Tables - grid lines visible like screenshot
        styleTable(tblEmployeeOrders);
        styleTable(tblOrderStatus);

        // Refresh button - blue rounded look
        btnRefresh.setBackground(new java.awt.Color(74, 99, 231));
        btnRefresh.setForeground(java.awt.Color.WHITE);
        btnRefresh.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorderPainted(false);

        // Card panel border - light gray box like screenshot
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        jPanel1.setBackground(new java.awt.Color(248, 250, 252));
        lblTrend.setPreferredSize(new java.awt.Dimension(250, 20));
        
    }

        private void styleTable(javax.swing.JTable table) {
            table.setShowGrid(true);
            table.setGridColor(new java.awt.Color(226, 232, 240));
            table.setRowHeight(28);
            table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
            table.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
            table.getTableHeader().setBackground(new java.awt.Color(248, 250, 252));
            table.getTableHeader().setForeground(new java.awt.Color(51, 65, 85));
    }
        private void centerTableText(javax.swing.JTable table) {
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
        public void refreshData() {
        loadTotalRevenue();
        loadEmployeeOrders();
        loadOrderStatus();
        loadHighestLowestMonth();
        loadTrend();
        tblOrderStatus.getColumnModel()
            .getColumn(0)   // Change 0 if Status is another column
            .setCellRenderer(new StatusChipRenderer());
    }

    private java.time.LocalDateTime[] getSelectedRange() {
        String selectedRange = (String) cmbDateFilter.getSelectedItem();
        java.time.LocalDateTime start;
        java.time.LocalDateTime end = java.time.LocalDateTime.now();
        cmbDateFilter.setFont(new Font("Inter 18pt", Font.PLAIN, 14));
        switch (selectedRange) {
            case "Today":
                start = java.time.LocalDate.now().atStartOfDay();
                break;
            case "This Week":
                start = java.time.LocalDate.now().minusDays(7).atStartOfDay();
                break;
            case "This Month":
                start = java.time.LocalDate.now().withDayOfMonth(1).atStartOfDay();
                break;
            case "Custom Range":
                try {
                    start = java.time.LocalDate.parse(txtStartDate.getText().trim()).atStartOfDay();
                    end = java.time.LocalDate.parse(txtEndDate.getText().trim()).atTime(23, 59, 59);
                } catch (Exception e) {
                    start = java.time.LocalDate.now().atStartOfDay();
                }
                break;
            case "All Time":
                start = java.time.LocalDateTime.of(2000, 1, 1, 0, 0);
                end = java.time.LocalDateTime.of(2100, 1, 1, 0, 0);
                break;
            default:
                start = java.time.LocalDateTime.of(2000, 1, 1, 0, 0);
                end = java.time.LocalDateTime.of(2100, 1, 1, 0, 0);
                break;
        }
        return new java.time.LocalDateTime[]{start, end};
    }

    private javax.swing.text.MaskFormatter createDateMask() {
        try {
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("####-##-##");
            mask.setPlaceholderCharacter('_');
            return mask;
        } catch (java.text.ParseException e) {
            return null;
        }
    }
    private void loadTotalRevenue() {
        java.time.LocalDateTime[] range = getSelectedRange();
        String sql = "SELECT SUM(total_amount) AS revenue FROM Orders "
                   + "WHERE payment_status = 'Paid' AND order_date BETWEEN ? AND ?";

        try (java.sql.Connection conn = com.mycompany.laundryservice.database.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(range[0]));
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(range[1]));

            java.sql.ResultSet rs = stmt.executeQuery();
            java.math.BigDecimal total = java.math.BigDecimal.ZERO;
            if (rs.next()) {
                java.math.BigDecimal result = rs.getBigDecimal("revenue");
                if (result != null) total = result;
            }
            jLabel4.setText(String.format("\u20B1%,.2f", total));

        } catch (java.sql.SQLException e) {
            jLabel4.setText("Error");
        }
    }

    private void loadEmployeeOrders() {
        java.time.LocalDateTime[] range = getSelectedRange();
        String sql = "SELECT e.name, COUNT(o.order_id) AS order_count "
                   + "FROM Orders o JOIN Employees e ON o.employee_id = e.employee_id "
                   + "WHERE o.order_date BETWEEN ? AND ? "
                   + "GROUP BY e.employee_id, e.name "
                   + "ORDER BY order_count DESC";

        javax.swing.table.DefaultTableModel model = new DefaultTableModel(
    new String[]{"Employee Name", "Order Count"}, 0
) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};

        try (java.sql.Connection conn = com.mycompany.laundryservice.database.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(range[0]));
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(range[1]));

            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("name"),
                    rs.getInt("order_count")
                });
            }
        } catch (java.sql.SQLException e) {
            // table stays empty on error
        }

        tblEmployeeOrders.setModel(model);
        centerTableText(tblEmployeeOrders);
    }
    
    

    private void loadOrderStatus() {
        java.time.LocalDateTime[] range = getSelectedRange();
        String sql = "SELECT order_status, COUNT(*) AS status_count "
                   + "FROM Orders "
                   + "WHERE order_date BETWEEN ? AND ? "
                   + "GROUP BY order_status";

        javax.swing.table.DefaultTableModel model = new DefaultTableModel(
    new String[]{"Status", "Count"}, 0
) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};

        try (java.sql.Connection conn = com.mycompany.laundryservice.database.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(range[0]));
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(range[1]));

            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("order_status"),
                    rs.getInt("status_count")
                });
            }
        } catch (java.sql.SQLException e) {
            // table stays empty on error
        }

        tblOrderStatus.setModel(model);
        centerTableText(tblOrderStatus);
    }
        public void setMainFrame(MainJFrame mainFrame) {
         this.mainFrame = mainFrame;
        }
        private void loadHighestLowestMonth() {
        String sql = "SELECT DATE_FORMAT(order_date, '%Y-%m') AS month, SUM(total_amount) AS revenue "
                   + "FROM Orders WHERE payment_status = 'Paid' "
                   + "GROUP BY month ORDER BY revenue DESC";

        try (java.sql.Connection conn = com.mycompany.laundryservice.database.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            java.sql.ResultSet rs = stmt.executeQuery();
            java.util.List<String> months = new java.util.ArrayList<>();
            java.util.List<java.math.BigDecimal> revenues = new java.util.ArrayList<>();

            while (rs.next()) {
                months.add(rs.getString("month"));
                revenues.add(rs.getBigDecimal("revenue"));
            }

            if (!months.isEmpty()) {
                String highestMonth = months.get(0);
                    String lowestMonth = months.get(months.size() - 1);

                    String highestAmount = String.format("%,.2f", revenues.get(0));
                    String lowestAmount = String.format("%,.2f", revenues.get(revenues.size() - 1));

                    lblHighestMonth.setText(
                        "<html><div style='text-align:center;'>"
                        + "<b>Highest</b><br>"
                        + "<span style='font-size:20px; color:#16A34A;'>₱" + highestAmount + "</span><br>"
                        + "<span style='color:#64748B;'>" + highestMonth + "</span>"
                        + "</div></html>"
                    );

                    lblLowestMonth.setText(
                        "<html><div style='text-align:center;'>"
                        + "<b>Lowest</b><br>"
                        + "<span style='font-size:20px; color:#DC2626;'>₱" + lowestAmount + "</span><br>"
                        + "<span style='color:#64748B;'>" + lowestMonth + "</span>"
                        + "</div></html>"
                    );
            } else {
                lblHighestMonth.setText("<html><b>Highest</b><br>N/A</html>");
                lblLowestMonth.setText("<html><b>Lowest</b><br>N/A</html>");
            }

        } catch (java.sql.SQLException e) {
            lblHighestMonth.setText("<html><b>Highest</b><br>Error</html>");
            lblLowestMonth.setText("<html><b>Lowest</b><br>Error</html>");
        }
    }

    private void loadTrend() {
        java.time.LocalDateTime[] range = getSelectedRange();
        long daysInRange = java.time.temporal.ChronoUnit.DAYS.between(range[0], range[1]);
        if (daysInRange < 1) daysInRange = 1;

        java.time.LocalDateTime prevEnd = range[0];
        java.time.LocalDateTime prevStart = range[0].minusDays(daysInRange);

        String sql = "SELECT SUM(total_amount) AS revenue FROM Orders "
                   + "WHERE payment_status = 'Paid' AND order_date BETWEEN ? AND ?";

        java.math.BigDecimal currentRevenue = getRevenueForRange(range[0], range[1]);
        java.math.BigDecimal previousRevenue = getRevenueForRange(prevStart, prevEnd);

        if (previousRevenue.compareTo(java.math.BigDecimal.ZERO) == 0) {
            lblTrend.setText("No prior data to compare");
            return;
        }

        java.math.BigDecimal change = currentRevenue.subtract(previousRevenue)
                .divide(previousRevenue, 4, java.math.RoundingMode.HALF_UP)
                .multiply(java.math.BigDecimal.valueOf(100));

        String arrow = change.compareTo(java.math.BigDecimal.ZERO) >= 0 ? "\u2191" : "\u2193";
        lblTrend.setText(arrow + " " + change.abs().setScale(1, java.math.RoundingMode.HALF_UP) + "% vs previous period");
    }

    private java.math.BigDecimal getRevenueForRange(java.time.LocalDateTime start, java.time.LocalDateTime end) {
        String sql = "SELECT SUM(total_amount) AS revenue FROM Orders "
                   + "WHERE payment_status = 'Paid' AND order_date BETWEEN ? AND ?";
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;

        try (java.sql.Connection conn = com.mycompany.laundryservice.database.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(start));
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(end));
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                java.math.BigDecimal result = rs.getBigDecimal("revenue");
                if (result != null) total = result;
            }
        } catch (java.sql.SQLException e) {
            // leave as zero
        }
        return total;
    }
        
	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        btnRefresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmployeeOrders = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblOrderStatus = new javax.swing.JTable();
        cmbDateFilter = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblHighestMonth = new javax.swing.JLabel();
        lblTrend = new javax.swing.JLabel();
        lblLowestMonth = new javax.swing.JLabel();
        txtStartDate = new javax.swing.JFormattedTextField();
        txtEndDate = new javax.swing.JFormattedTextField();

        btnRefresh.setBackground(new java.awt.Color(74, 99, 231));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.setToolTipText("");
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setText("Reports Dashboard");

        tblEmployeeOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Employee Name", "Order Count"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmployeeOrders.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tblEmployeeOrders);
        tblEmployeeOrders.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        tblOrderStatus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Status", "Count"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblOrderStatus);

        cmbDateFilter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbDateFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Today", "This Week", "This Month", "All Time", "Custom Range" }));
        cmbDateFilter.addActionListener(this::cmbDateFilterActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Orders by Status");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Orders per Employee");

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), null, new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Total Revenue");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(74, 99, 231));
        jLabel4.setText("₱0.00");

        lblHighestMonth.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblHighestMonth.setForeground(new java.awt.Color(51, 51, 51));
        lblHighestMonth.setText("Total Revenue");

        lblTrend.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
        lblTrend.setForeground(new java.awt.Color(51, 51, 51));
        lblTrend.setText("Total Revenue");

        lblLowestMonth.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblLowestMonth.setForeground(new java.awt.Color(51, 51, 51));
        lblLowestMonth.setText("Total Revenue");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTrend, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(82, 82, 82)
                        .addComponent(lblHighestMonth)
                        .addGap(118, 118, 118)
                        .addComponent(lblLowestMonth)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHighestMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblLowestMonth)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTrend)
                .addGap(80, 80, 80))
        );

        txtStartDate.setEnabled(false);
        txtStartDate.addActionListener(this::txtStartDateActionPerformed);

        txtEndDate.setEnabled(false);
        txtEndDate.addActionListener(this::txtEndDateActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(280, 280, 280)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbDateFilter, 0, 205, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRefresh))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbDateFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbDateFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDateFilterActionPerformed
             String selected = (String) cmbDateFilter.getSelectedItem();
             boolean isCustom = "Custom Range".equals(selected);
             txtStartDate.setEnabled(isCustom);
             txtEndDate.setEnabled(isCustom);

    if (!isCustom) {
        refreshData();
        
    }
    }//GEN-LAST:event_cmbDateFilterActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
            refreshData();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEndDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEndDateActionPerformed

    private void txtStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStartDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStartDateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> cmbDateFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHighestMonth;
    private javax.swing.JLabel lblLowestMonth;
    private javax.swing.JLabel lblTrend;
    private javax.swing.JTable tblEmployeeOrders;
    private javax.swing.JTable tblOrderStatus;
    private javax.swing.JFormattedTextField txtEndDate;
    private javax.swing.JFormattedTextField txtStartDate;
    // End of variables declaration//GEN-END:variables
}
