/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.laundryservice.panels;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.laundryservice.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author jairus
 */
public class CustomerListDialog extends javax.swing.JDialog {

    private int selectedCustomerId = -1;
    private String selectedCustomerName = "";
    private String selectedPhoneNumber = "";
    private boolean customerSelected = false;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    /**
     * Creates new form CustomerListDialog
     *
     * @param parent
     * @param modal
     */
    public CustomerListDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initializeDialog();
    }

    private void initializeDialog() {
        // Set up table model - using scrCustomer (matches generated code)
        tableModel = (DefaultTableModel) scrCustomer.getModel();
        sorter = new TableRowSorter<>(tableModel);
        scrCustomer.setRowSorter(sorter);

        // ====== SORT ONLY CUSTOMER ID IN ASCENDING ORDER ======
        // Enable sorting only on Customer ID (Column 0)
        sorter.setSortable(0, true);   // Enable Customer ID
        sorter.setSortable(1, false);  // Disable Customer Name
        sorter.setSortable(2, false);  // Disable Address
        sorter.setSortable(3, false);  // Disable Phone Number

        // Set Customer ID (Column 0) as the default sort in ascending order
        sorter.setSortKeys(java.util.Arrays.asList(
            new RowSorter.SortKey(0, SortOrder.ASCENDING)
        ));

        // Fix: Sort Customer ID as numbers (not text)
        sorter.setComparator(0, (o1, o2) -> {
            try {
                int id1 = Integer.parseInt(o1.toString());
                int id2 = Integer.parseInt(o2.toString());
                return Integer.compare(id1, id2);
            } catch (NumberFormatException e) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        // ====== END ======

        // Set dialog properties
        setTitle("Select Customer");
        setModal(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        // Load customers
        loadCustomers();

        // Setup double-click selection
        scrCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    selectCustomer();
                }
            }
        });
    }

    private void loadCustomers() {
        String sql = "SELECT customer_id, name, phone, address FROM Customers WHERE is_active = 1 ORDER BY name";
        tableModel.setRowCount(0);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("address") != null ? rs.getString("address") : "",
                    rs.getString("phone")
                });
            }

            lblCount.setText(String.valueOf(tableModel.getRowCount()));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Unable to load customers. Please check database connection.",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void searchCustomers() {
        String searchText = txtSearch.getText().trim();

        if (searchText.isEmpty()) {
            sorter.setRowFilter(null);
            lblCount.setText(String.valueOf(tableModel.getRowCount()));
            return;
        }

        try {
            // Try to search as number for ID column
            try {
                int id = Integer.parseInt(searchText);
                sorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, id, 0));
            } catch (NumberFormatException e) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 1, 2, 3));
            }
        } catch (Exception e) {
            sorter.setRowFilter(null);
        }

        int filteredCount = scrCustomer.getRowCount();
        lblCount.setText(filteredCount + " / " + tableModel.getRowCount());
    }

    private void selectCustomer() {
        int selectedRow = scrCustomer.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a customer first.",
                "Selection Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = scrCustomer.convertRowIndexToModel(selectedRow);

        selectedCustomerId = (int) tableModel.getValueAt(modelRow, 0);
        selectedCustomerName = (String) tableModel.getValueAt(modelRow, 1);
        selectedPhoneNumber = (String) tableModel.getValueAt(modelRow, 3);

        customerSelected = true;
        dispose();
    }

    // Getters
    public int getSelectedCustomerId() {
        return selectedCustomerId;
    }

    public String getSelectedCustomerName() {
        return selectedCustomerName;
    }

    public String getSelectedPhoneNumber() {
        return selectedPhoneNumber;
    }

    public boolean isCustomerSelected() {
        return customerSelected;
    }

    // ==================== MAIN METHOD ====================

    public static void main(String[] args) {
        FlatLightLaf.setup();

        JFrame frame = new JFrame();
        frame.add(new CustomerListDialog(null, true));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        scrCustomer = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblCount = new javax.swing.JLabel();
        btnSelect = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 500));

        scrCustomer.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        scrCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer ID", "Customer Name", "Address", "Phone Number"
            }
        ));
        jScrollPane1.setViewportView(scrCustomer);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Select Customer");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Search: ");

        txtSearch.addActionListener(this::txtSearchActionPerformed);

        btnSearch.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        btnRefresh.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addGap(18, 18, 18)
                .addComponent(btnRefresh)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(btnRefresh))
                .addGap(12, 12, 12))
        );

        jPanel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel3.setText("Total Customer: ");

        lblCount.setText("0");

        btnSelect.setText("Select");
        btnSelect.addActionListener(this::btnSelectActionPerformed);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCount, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSelect)
                .addGap(18, 18, 18)
                .addComponent(btnCancel)
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSelect)
                        .addComponent(btnCancel))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(lblCount)))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
         searchCustomers();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
         searchCustomers();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        // TODO add your handling code here:
       selectCustomer();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
         txtSearch.setText("");
        loadCustomers();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
         customerSelected = false;
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCount;
    private javax.swing.JTable scrCustomer;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
