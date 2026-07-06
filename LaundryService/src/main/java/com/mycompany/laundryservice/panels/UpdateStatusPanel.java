/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.laundryservice.panels;

import javax.swing.JFrame;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.laundryservice.database.DBConnection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author Cral
 */
public class UpdateStatusPanel extends javax.swing.JPanel {

    /**
     * Creates new form UpdateStatusPanel
     */
    public UpdateStatusPanel() {
        initComponents();
        loadTableData();
        setupTableStyles(); 
    }
    
    
       private void setupTableStyles() { 
        
   try {
    // Siguraduhin na ang inter.ttf file ay nasa iyong project folder
    java.awt.Font interFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, 
            new java.io.File("fonts/Inter-Regular.ttf")).deriveFont(12f);
    java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(interFont);
} catch (Exception e) {
    e.printStackTrace();
}
    
    tblOrders.getTableHeader().setBackground(new Color(15, 23, 42));
tblOrders.getTableHeader().setForeground(Color.WHITE);
    tblOrders.setRowHeight(35);
    
    // I-set ang Inter font para sa table content at header
java.awt.Font interFont = new java.awt.Font("Inter", java.awt.Font.PLAIN, 12);
tblOrders.setFont(interFont);
tblOrders.getTableHeader().setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 12));
    

    // DITO MO ILAGAY ANG CUSTOM RENDERER PARA SA STATUS (Index 6)
    tblOrders.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = value != null ? value.toString() : "";
            
            // Pag-set ng kulay base sa status
            if (status.equalsIgnoreCase("Pending")) {
                label.setBackground(Color.LIGHT_GRAY);
            } else if (status.equalsIgnoreCase("Processing")) {
                label.setBackground(Color.CYAN);
           } else if (status.equalsIgnoreCase("Ready")) {
    label.setBackground(new Color(5, 150, 105));
     } else if (status.equalsIgnoreCase("Claimed")) {
              label.setBackground(new Color(0, 153, 204)); 
            } else {
                label.setBackground(Color.WHITE);
            }
            
            
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
centerRenderer.setHorizontalAlignment(JLabel.CENTER);

// I-apply ang center alignment sa mga specific columns
tblOrders.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Service Type
tblOrders.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Weight
tblOrders.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

            label.setOpaque(true);
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        }
    });
    
    }
  
    
    
    
    
    
    public void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) tblOrders.getModel();
    model.setRowCount(0); 

    
    String query = "SELECT o.claim_number, c.name, c.phone, c.address, s.service_name, o.weight_kg, o.order_status, o.total_amount " +
               "FROM orders o " +
               "JOIN customers c ON o.customer_id = c.customer_id " +
               "JOIN services s ON o.service_id = s.service_id";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) { 

        while (rs.next()) {
    model.addRow(new Object[]{
        rs.getString("claim_number"),
        rs.getString("name"), 
        rs.getString("phone"),
        rs.getString("address"),
        rs.getString("service_name"),
        rs.getDouble("weight_kg"),
        rs.getString("order_status"),
        rs.getDouble("total_amount")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
    }

    }
    
private void refreshFilteredTableData() {
    
    DefaultTableModel model = (DefaultTableModel) tblOrders.getModel(); 
    model.setRowCount(0); 

    String query = "SELECT o.claim_number, c.customer_name, o.phone_number, o.address, s.service_name, o.weight_kg, o.order_status, o.total_amount " +
               "FROM orders o " +
               "JOIN customers c ON o.customer_id = c.customer_id " +
               "JOIN services s ON o.service_id = s.service_id";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {
        
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("claim_number"),
                rs.getString("customer_name"),
                rs.getString("order_status"),
                rs.getDouble("total_amount")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public static void main(String[] args) {
        FlatLightLaf.setup();

        JFrame frame = new JFrame();
        frame.add(new UpdateStatusPanel());
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

        jPanel1 = new javax.swing.JPanel();
        updateSubContainer = new javax.swing.JPanel();
        cardTable = new javax.swing.JPanel();
        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        filteredTable = new javax.swing.JScrollPane();
        tblOrders = new javax.swing.JTable();
        pnlFooter = new javax.swing.JPanel();
        cardEditor = new javax.swing.JPanel();
        pnlEditorHeader = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        pnlEditorContent = new javax.swing.JPanel();
        pnlCustomer = new javax.swing.JPanel();
        lblClaimNumber = new javax.swing.JLabel();
        lblFullName = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        pnlWorkflow = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        pnlService = new javax.swing.JPanel();
        lblServiceType = new javax.swing.JLabel();
        lblWeight = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pnlEditorFooter = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setLayout(new java.awt.BorderLayout());

        updateSubContainer.setLayout(new java.awt.CardLayout());

        cardTable.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Update Order Status");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel1)
                .addContainerGap(888, Short.MAX_VALUE))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        cardTable.add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlContent.setLayout(new java.awt.BorderLayout());

        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Claim Number", "Customer", "Phone Number", "Address", "Service Type", "Weight (kg)", "Status", "Total Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrders.setColumnSelectionAllowed(true);
        tblOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrdersMouseClicked(evt);
            }
        });
        filteredTable.setViewportView(tblOrders);
        tblOrders.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        pnlContent.add(filteredTable, java.awt.BorderLayout.CENTER);
        pnlContent.add(pnlFooter, java.awt.BorderLayout.PAGE_END);

        cardTable.add(pnlContent, java.awt.BorderLayout.CENTER);

        updateSubContainer.add(cardTable, "card2");

        cardEditor.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setText("Update Order Status");

        javax.swing.GroupLayout pnlEditorHeaderLayout = new javax.swing.GroupLayout(pnlEditorHeader);
        pnlEditorHeader.setLayout(pnlEditorHeaderLayout);
        pnlEditorHeaderLayout.setHorizontalGroup(
            pnlEditorHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditorHeaderLayout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jLabel9)
                .addContainerGap(854, Short.MAX_VALUE))
        );
        pnlEditorHeaderLayout.setVerticalGroup(
            pnlEditorHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditorHeaderLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(35, 35, 35))
        );

        cardEditor.add(pnlEditorHeader, java.awt.BorderLayout.PAGE_START);

        pnlCustomer.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblClaimNumber.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblClaimNumber.setText("#LS-260627-001");

        lblFullName.setText("Stephanie Casimiro");

        lblPhone.setText("+63 99999999912");

        lblAddress.setText("Brgy. GenZ, Pasig City, Albay");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel2.setText("Claim Number");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel3.setText("Phone Number");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel4.setText("Full Name");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel5.setText("Address");

        jLabel10.setText("Customer Contact");

        javax.swing.GroupLayout pnlCustomerLayout = new javax.swing.GroupLayout(pnlCustomer);
        pnlCustomer.setLayout(pnlCustomerLayout);
        pnlCustomerLayout.setHorizontalGroup(
            pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCustomerLayout.createSequentialGroup()
                        .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(lblPhone))
                        .addGap(197, 197, 197)
                        .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(lblAddress)))
                    .addGroup(pnlCustomerLayout.createSequentialGroup()
                        .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(lblClaimNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(154, 154, 154)
                        .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addContainerGap(317, Short.MAX_VALUE))
        );
        pnlCustomerLayout.setVerticalGroup(
            pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFullName)
                    .addGroup(pnlCustomerLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblClaimNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddress)
                    .addComponent(jLabel3))
                .addGap(8, 8, 8)
                .addComponent(lblPhone)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pnlWorkflow.setBorder(javax.swing.BorderFactory.createTitledBorder("Update Workflow Status"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ready", "Pending", "Processing", "Claimed" }));

        btnUpdate.setBackground(new java.awt.Color(51, 153, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel8.setText("CHANGE STATUS TO");

        javax.swing.GroupLayout pnlWorkflowLayout = new javax.swing.GroupLayout(pnlWorkflow);
        pnlWorkflow.setLayout(pnlWorkflowLayout);
        pnlWorkflowLayout.setHorizontalGroup(
            pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWorkflowLayout.createSequentialGroup()
                .addGroup(pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlWorkflowLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlWorkflowLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlWorkflowLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel8)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        pnlWorkflowLayout.setVerticalGroup(
            pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWorkflowLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pnlService.setBorder(javax.swing.BorderFactory.createTitledBorder("Service Summary"));

        lblServiceType.setText("Wash/Dry/Fold");

        lblWeight.setText("7.00 kg");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel6.setText("Service Type");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel7.setText("Weight");

        javax.swing.GroupLayout pnlServiceLayout = new javax.swing.GroupLayout(pnlService);
        pnlService.setLayout(pnlServiceLayout);
        pnlServiceLayout.setHorizontalGroup(
            pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServiceLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblServiceType)
                    .addComponent(jLabel6))
                .addGap(211, 211, 211)
                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(lblWeight))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlServiceLayout.setVerticalGroup(
            pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlServiceLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServiceType)
                    .addComponent(lblWeight))
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout pnlEditorContentLayout = new javax.swing.GroupLayout(pnlEditorContent);
        pnlEditorContent.setLayout(pnlEditorContentLayout);
        pnlEditorContentLayout.setHorizontalGroup(
            pnlEditorContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditorContentLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnlEditorContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlService, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(59, 59, 59)
                .addComponent(pnlWorkflow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        pnlEditorContentLayout.setVerticalGroup(
            pnlEditorContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditorContentLayout.createSequentialGroup()
                .addGroup(pnlEditorContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditorContentLayout.createSequentialGroup()
                        .addComponent(pnlCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEditorContentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlWorkflow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(101, Short.MAX_VALUE))
        );

        cardEditor.add(pnlEditorContent, java.awt.BorderLayout.LINE_END);
        cardEditor.add(pnlEditorFooter, java.awt.BorderLayout.PAGE_END);

        updateSubContainer.add(cardEditor, "card3");

        add(updateSubContainer, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tblOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdersMouseClicked

   
        
        java.awt.CardLayout cl = (java.awt.CardLayout) updateSubContainer.getLayout();
        cl.show(updateSubContainer, "cardEditor");
        int selectedRow = tblOrders.getSelectedRow();
        if (selectedRow != -1) {

            String claimNum = tblOrders.getValueAt(selectedRow, 0).toString();
            String fullName = tblOrders.getValueAt(selectedRow, 1).toString();
            String phone = tblOrders.getValueAt(selectedRow, 2).toString();
            String address = tblOrders.getValueAt(selectedRow, 3).toString();
            String service = tblOrders.getValueAt(selectedRow, 4).toString();
            String weight = tblOrders.getValueAt(selectedRow, 5).toString();
String currentStatus = tblOrders.getValueAt(selectedRow, 6).toString();

            lblClaimNumber.setText(claimNum);
            lblFullName.setText(fullName);
            lblPhone.setText(phone);
            lblAddress.setText(address);
            lblServiceType.setText(service);
            lblWeight.setText(weight);

            
            jComboBox1.removeAllItems();
        if ("Pending".equals(currentStatus)) {
            jComboBox1.addItem("Pending");
            jComboBox1.addItem("Processing");
            jComboBox1.addItem("Ready");
        } else if ("Processing".equals(currentStatus)) {
            jComboBox1.addItem("Processing");
            jComboBox1.addItem("Ready");
        } else if ("Ready".equals(currentStatus)) {
            jComboBox1.addItem("Ready");
            jComboBox1.addItem("Claimed");
        }
        jComboBox1.setSelectedItem(currentStatus);

        
        cl.show(updateSubContainer, "card3");
        
        }
    }//GEN-LAST:event_tblOrdersMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String newStatus = jComboBox1.getSelectedItem().toString();
    String claimId = lblClaimNumber.getText();

    String query = "UPDATE orders SET order_status = ? WHERE claim_number = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, newStatus);
        pstmt.setString(2, claimId);
        pstmt.executeUpdate();

        javax.swing.JOptionPane.showMessageDialog(this, "Order updated to: " + newStatus);

        
        loadTableData();

        
        java.awt.CardLayout cl = (java.awt.CardLayout) updateSubContainer.getLayout();
       cl.show(updateSubContainer, "card2");

    } catch (SQLException e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error updating status: " + e.getMessage());
    }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        java.awt.CardLayout cl = (java.awt.CardLayout) updateSubContainer.getLayout();
        cl.show(updateSubContainer, "card2");
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel cardEditor;
    private javax.swing.JPanel cardTable;
    private javax.swing.JScrollPane filteredTable;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblClaimNumber;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblServiceType;
    private javax.swing.JLabel lblWeight;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlCustomer;
    private javax.swing.JPanel pnlEditorContent;
    private javax.swing.JPanel pnlEditorFooter;
    private javax.swing.JPanel pnlEditorHeader;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlService;
    private javax.swing.JPanel pnlWorkflow;
    private javax.swing.JTable tblOrders;
    private javax.swing.JPanel updateSubContainer;
    // End of variables declaration//GEN-END:variables

}