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
 

 
public class ReportsPanel extends javax.swing.JPanel {
        private MainJFrame mainFrame;

	/**
	 * Creates new form ReportsPanel
	 */
	public ReportsPanel() {
		initComponents();
                txtStartDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(createDateMask()));
                txtEndDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(createDateMask()));
                refreshData();
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
    }

    private java.time.LocalDateTime[] getSelectedRange() {
        String selectedRange = (String) cmbDateFilter.getSelectedItem();
        java.time.LocalDateTime start;
        java.time.LocalDateTime end = java.time.LocalDateTime.now();

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

        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new String[]{"Employee Name", "Order Count"}, 0
        );

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

        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new String[]{"Status", "Count"}, 0
        );

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
                lblHighestMonth.setText("Highest: " + months.get(0) + " (\u20B1" + String.format("%,.2f", revenues.get(0)) + ")");
                lblLowestMonth.setText("Lowest: " + months.get(months.size() - 1) + " (\u20B1" + String.format("%,.2f", revenues.get(months.size() - 1)) + ")");
            } else {
                lblHighestMonth.setText("Highest: N/A");
                lblLowestMonth.setText("Lowest: N/A");
            }

        } catch (java.sql.SQLException e) {
            lblHighestMonth.setText("Highest: Error");
            lblLowestMonth.setText("Lowest: Error");
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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
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
        ));
        tblEmployeeOrders.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tblEmployeeOrders);

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
        ));
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTrend, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(45, 45, 45)
                                .addComponent(lblHighestMonth))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblLowestMonth)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblHighestMonth))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLowestMonth))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTrend)
                .addContainerGap())
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
                                .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
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
