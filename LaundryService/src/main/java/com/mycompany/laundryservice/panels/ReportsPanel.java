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
            default:
                start = java.time.LocalDateTime.of(2000, 1, 1, 0, 0);
                break;
        }
        return new java.time.LocalDateTime[]{start, end};
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
        cmbDateFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Today", "This Week", "This Month", "All Time" }));
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

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
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 516, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbDateFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRefresh)))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbDateFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbDateFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDateFilterActionPerformed
            refreshData();
       
    }//GEN-LAST:event_cmbDateFilterActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
            refreshData();
    }//GEN-LAST:event_btnRefreshActionPerformed


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
    private javax.swing.JTable tblEmployeeOrders;
    private javax.swing.JTable tblOrderStatus;
    // End of variables declaration//GEN-END:variables
}
