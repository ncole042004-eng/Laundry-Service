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
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;


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
    
    
     // ====== CUSTOM ROUNDED BORDER CLASS ======
            private static class RoundedBorder extends javax.swing.border.AbstractBorder {
                 private final int radius;
                 private final Color color;
                 private final int thickness;

            RoundedBorder(int radius, Color color, int thickness) {
                this.radius = radius;
                this.color = color;
                this.thickness = thickness;
            }

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setStroke(new java.awt.BasicStroke(thickness));
                g2.drawRoundRect(x + thickness/2, y + thickness/2, width - thickness, height - thickness, radius, radius);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(radius/2, radius/2, radius/2, radius/2);
            }
        }
    // ====== END ======


    /**
     * Creates new form CustomerListDialog
     *
     * @param parent
     * @param modal
     */
    public CustomerListDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        // ====== SET FIXED SIZE ======
        this.setResizable(false);
        this.setSize(821, 650);
        // ====== END ======
        initializeDialog();
    }

    private void initializeDialog() {
        // Set up table model - using scrCustomer (matches generated code)
        tableModel = (DefaultTableModel) scrCustomer.getModel();
        sorter = new TableRowSorter<>(tableModel);
        scrCustomer.setRowSorter(sorter);
        
                    // ====== CENTER CUSTOMER ID COLUMN ======
                scrCustomer.getColumnModel().getColumn(0).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
                    @Override
                    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                            boolean isSelected, boolean hasFocus, int row, int column) {
                        javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        label.setHorizontalAlignment(javax.swing.JLabel.CENTER);
                        return label;
                    }
                });
                // ====== END ======
        
      
        
        
          //      ====== ROUNDED PANEL BORDERS (BLUE) ======
         // ====== ROUNDED PANEL BORDERS (BLUE) ======
            int arc = 12;
            Color borderColor = new java.awt.Color(38, 85, 189);

            javax.swing.border.Border padding = javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2);
            javax.swing.border.Border roundedBorder = new RoundedBorder(arc, borderColor, 1);

            jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(roundedBorder, padding));
            jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(roundedBorder, padding));

            jPanel1.setBackground(java.awt.Color.WHITE);
            jPanel2.setBackground(java.awt.Color.WHITE);
            // ====== END ======
         // ====== END ======
        
         // ====== APPLY INTER FONTS (MATCHING HTML DESIGN) ======
        // Title - Headline Medium (Inter 18pt SemiBold)
        jLabel1.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 18));
        jLabel1.setForeground(new java.awt.Color(26, 28, 28));
        
        // Search Label - Label Medium (Inter)
        jLabel2.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        jLabel2.setForeground(new java.awt.Color(44, 62, 80));
        
        // Search Text Field - Body Medium (Inter)
        txtSearch.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        txtSearch.setForeground(new java.awt.Color(26, 28, 28));
        
        // Buttons - Label Medium (Inter Bold)
        btnSearch.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
        btnSearch.setBackground(new java.awt.Color(52, 152, 219));
        btnSearch.setForeground(java.awt.Color.WHITE);
        
        btnRefresh.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
        btnRefresh.setBackground(new java.awt.Color(52, 152, 219));
        btnRefresh.setForeground(java.awt.Color.WHITE);
        
        btnSelect.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
        btnSelect.setBackground(new java.awt.Color(52, 152, 219));
        btnSelect.setForeground(java.awt.Color.WHITE);
        
        btnCancel.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
        btnCancel.setBackground(new java.awt.Color(52, 152, 219));
        btnCancel.setForeground(java.awt.Color.WHITE);
        
        // Total Customers Label - Label Medium (Inter)
        jLabel3.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        jLabel3.setForeground(new java.awt.Color(44, 62, 80));
        
        // Total Count - Label Medium Bold (Inter)
        lblCount.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
        lblCount.setForeground(new java.awt.Color(38, 85, 189));
        
        // Table Header - Label Medium Bold (Inter)
        scrCustomer.getTableHeader().setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 12));
        scrCustomer.getTableHeader().setForeground(new java.awt.Color(26, 28, 28));
        scrCustomer.getTableHeader().setBackground(new java.awt.Color(238, 238, 238));
        
        // Table Content - Body Medium (Inter)
        scrCustomer.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        scrCustomer.setRowHeight(35);
        // ====== END FONT STYLING ======

        // ====== ADD SVG ICONS ======
        // Title Label - person_search.svg
        jLabel1.setIcon(loadIcon("person_search.svg", 16, 0x2655bd));
        jLabel1.setIconTextGap(8);
        jLabel1.setText(" Select Customer");

        // Search Button - search.svg
        btnSearch.setIcon(loadIcon("search.svg", 16, 0x2655bd));
        btnSearch.setIconTextGap(8);
        btnSearch.setText(" Search");

        // Refresh Button - density_small.svg
        btnRefresh.setIcon(loadIcon("density_small.svg", 16, 0x2655bd));
        btnRefresh.setIconTextGap(8);
        btnRefresh.setText(" Refresh");

        // Select Button - check_circle.svg
        btnSelect.setIcon(loadIcon("check_circle.svg", 16, 0xFFFFFF));
        btnSelect.setIconTextGap(8);
        btnSelect.setBackground(new java.awt.Color(52,152,219)); 
        btnSelect.setText(" Select");

        // Cancel Button - logout.svg
        btnCancel.setIcon(loadIcon("logout.svg", 16, 0x666666));
        btnCancel.setIconTextGap(8);
        btnCancel.setText(" Cancel");
        // ====== END ADD ICONS ======
        
        
            // ====== DISABLE COLUMN REORDERING ======
            scrCustomer.getTableHeader().setReorderingAllowed(false);
            // ====== END ======
    
    

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
    /**
     * Helper method to load SVG icons
     */
    private javax.swing.Icon loadIcon(String iconName, int size, int colorHex) {
        try {
            FlatSVGIcon icon = new FlatSVGIcon("icons/" + iconName, size, size);
            icon.setColorFilter(new FlatSVGIcon.ColorFilter(c -> new java.awt.Color(colorHex)));
            return icon;
        } catch (Exception e) {
            return null; // Icon not found
        }
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

        scrCustomer.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        scrCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer ID", "Customer Name", "Address", "Phone Number"
            }
        ));
        jScrollPane1.setViewportView(scrCustomer);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Select Customer");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Search: ");

        txtSearch.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtSearch.addActionListener(this::txtSearchActionPerformed);

        btnSearch.setBackground(new java.awt.Color(52, 152, 219));
        btnSearch.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        btnRefresh.setBackground(new java.awt.Color(52, 152, 219));
        btnRefresh.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
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
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnSearch)
                .addGap(18, 18, 18)
                .addComponent(btnRefresh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(btnRefresh))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Total Customer: ");

        lblCount.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCount.setText("0");

        btnSelect.setBackground(new java.awt.Color(52, 152, 219));
        btnSelect.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnSelect.setForeground(new java.awt.Color(255, 255, 255));
        btnSelect.setText("Select");
        btnSelect.addActionListener(this::btnSelectActionPerformed);

        btnCancel.setBackground(new java.awt.Color(52, 152, 219));
        btnCancel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lblCount, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(btnSelect)
                .addGap(72, 72, 72)
                .addComponent(btnCancel)
                .addGap(42, 42, 42))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblCount)
                    .addComponent(btnSelect)
                    .addComponent(btnCancel))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

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
