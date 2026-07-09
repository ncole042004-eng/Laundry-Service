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
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingUtilities;



public class UpdateStatusPanel extends javax.swing.JPanel {

    
    public UpdateStatusPanel() {
        loadCustomFonts();
        initComponents();
        setupIcons();
        setupLayout();
        loadTableData();
        setupTableStyles();
        setupEnhancedLayout();
        setupSearchFunctionality();

          SwingUtilities.invokeLater(() -> {
        applyStyles();  
        pnlEditorContent.revalidate();
        pnlEditorContent.repaint();
        });

    }

    private void setupLayout() {
    pnlContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
}

    private void setupIcons() {
        btnUpdate.setIcon(new FlatSVGIcon("icons/save.svg", 20, 20));

        jLabel10.setIcon(new FlatSVGIcon("icons/account_circle.svg", 20, 20));
        jLabel10.setIconTextGap(8);

        jLabel11.setIcon(new FlatSVGIcon("icons/list_alt.svg", 20, 20));
        jLabel11.setIconTextGap(8);

        jLabel12.setIcon(new FlatSVGIcon("icons/edit.svg", 20, 20));
        jLabel12.setIconTextGap(8);
    }

   DefaultTableCellRenderer claimNumberRenderer = new DefaultTableCellRenderer() {
    @Override
    public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setHorizontalAlignment(JLabel.CENTER);
        
        c.setForeground(isSelected ? Color.WHITE : new java.awt.Color(38, 85, 189)); // Match HomePanel
        c.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 14));
        if (!isSelected) {
            c.setBackground(new java.awt.Color(249, 249, 249));
        }

        return c;
    }
};

    private class ChipCellRenderer extends DefaultTableCellRenderer {

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
            setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 12));

            switch (text.toLowerCase()) {
                case "pending":
                    chipBg = new Color(255, 243, 224);
                    setForeground(new Color(239, 107, 0));
                    break;
                case "processing":
                    chipBg = new Color(192, 237, 250);
                    setForeground(new Color(0, 85, 108));
                    break;
                case "ready":
                    chipBg = new Color(204, 235, 255);
                    setForeground(new Color(37, 99, 235));
                    break;
                case "claimed":
                case "paid":
                    chipBg = new Color(232, 245, 233);
                    setForeground(new Color(47, 124, 49));
                    break;
                case "unpaid":
                    chipBg = new Color(255, 218, 214);
                    setForeground(new Color(198, 40, 40));
                    break;
                default:
                    chipBg = Color.WHITE;
                    setForeground(Color.BLACK);
            }
            return this;
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

            java.awt.FontMetrics fm = g2.getFontMetrics(getFont());
            int textWidth = fm.stringWidth(getText());
            int chipHeight = getHeight() - 16;
            int chipWidth = textWidth + 28;
            int x = (getWidth() - chipWidth) / 2;
            int y = (getHeight() - chipHeight) / 2;

            g2.setColor(chipBg);
            g2.fillRoundRect(x, y, chipWidth, chipHeight, chipHeight, chipHeight);
            g2.dispose();

            super.paintComponent(g);
        }
    }

    public void loadCustomFonts() {
        try {
            java.awt.GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontFiles = {"Inter_18pt-Regular.ttf", "Inter_18pt-Bold.ttf", "Inter_18pt-Medium.ttf", "Inter_18pt-SemiBold.ttf", "PlayfairDisplay-Bold.ttf"};

            for (String fontFile : fontFiles) {
                java.awt.Font customFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                        new java.io.File("res/fonts/" + fontFile));
                ge.registerFont(customFont);
            }

            lblClaimNumber.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
            jLabel9.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 24));

        } catch (Exception e) {
            System.err.println("Error loading fonts: " + e.getMessage());
        }
    }

   private void setupTableStyles() {
    tblOrders.getColumnModel().getColumn(0).setCellRenderer(claimNumberRenderer);

    try {
        java.awt.Font interFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
            new java.io.File("fonts/Inter-Regular.ttf")).deriveFont(12f);
        java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(interFont);
    } catch (Exception e) {
        e.printStackTrace();
    }

   
    tblOrders.getTableHeader().setBackground(new Color(0x45, 0x6f, 0xd7));
    tblOrders.getTableHeader().setForeground(Color.WHITE);
    tblOrders.setRowHeight(48);
    tblOrders.setGridColor(new Color(0xc3, 0xc6, 0xd7));
    tblOrders.setShowGrid(false);
    tblOrders.setShowHorizontalLines(true);
    tblOrders.setShowVerticalLines(false);

    tblOrders.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.PLAIN, 14));
    tblOrders.getTableHeader().setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 12));
    tblOrders.getTableHeader().putClientProperty("FlatTableHeader.separatorColor", new Color(195, 198, 215));
    ((DefaultTableCellRenderer) tblOrders.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

    tblOrders.setBackground(new Color(249, 249, 249));
    filteredTable.getViewport().setBackground(new Color(249, 249, 249));

    filteredTable.putClientProperty("JScrollPane.showBorder", false);
    filteredTable.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    filteredTable.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder());
    tblOrders.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    
    if (pnlContent != null) pnlContent.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    if (cardTable != null) cardTable.setBackground(new Color(249, 249, 249));
    if (pnlContent != null) pnlContent.setBackground(new Color(249, 249, 249));
  
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setFont(new java.awt.Font("Inter 18pt", java.awt.Font.PLAIN, 14));
            setHorizontalAlignment(JLabel.CENTER);
            return this;
        }
    };
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);   
    
    for (int i = 0; i < tblOrders.getColumnCount(); i++) {
        if (i == 7 || i == 8) {
            continue;
        }
    tblOrders.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    tblOrders.getColumnModel().getColumn(0).setCellRenderer(claimNumberRenderer);
    tblOrders.getColumnModel().getColumn(7).setCellRenderer(new ChipCellRenderer()); 
    tblOrders.getColumnModel().getColumn(8).setCellRenderer(new ChipCellRenderer()); 
}

    private void styleCardLayout() {
        pnlEditorContent.setBackground(new Color(249, 249, 249));
        cardEditor.setBackground(new Color(249, 249, 249));
        pnlEditorHeader.setBackground(new Color(249, 249, 249));
        pnlEditorFooter.setBackground(new Color(249, 249, 249));

        
        pnlCustomer.setBackground(Color.WHITE);
        pnlService.setBackground(Color.WHITE);
        pnlWorkflow.setBackground(Color.WHITE);

        
        pnlCustomer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(195, 198, 215), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        
        pnlService.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(195, 198, 215), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        
        pnlWorkflow.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(195, 198, 215), 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        setupInnerLayouts();
        setupWorkflowLayout();     
    }

  private void setupInnerLayouts() {
    pnlCustomer.removeAll();
    pnlCustomer.setLayout(new java.awt.GridBagLayout());
    java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
    gbc.anchor = java.awt.GridBagConstraints.WEST;
    gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

    
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.insets = new java.awt.Insets(0, 0, 8, 0);
    pnlCustomer.add(jLabel10, gbc);
    gbc.gridwidth = 1;

    
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.5;
    gbc.insets = new java.awt.Insets(2, 5, 0, 5);
    pnlCustomer.add(jLabel2, gbc);

    gbc.gridx = 1;
    pnlCustomer.add(jLabel4, gbc);

    
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.insets = new java.awt.Insets(0, 5, 8, 5);
    pnlCustomer.add(lblClaimNumber, gbc);

    gbc.gridx = 1;
    pnlCustomer.add(lblFullName, gbc);

    
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.insets = new java.awt.Insets(2, 5, 0, 5);
    pnlCustomer.add(jLabel3, gbc);

    gbc.gridx = 1;
    pnlCustomer.add(jLabel5, gbc);

   
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.insets = new java.awt.Insets(0, 5, 0, 5);
    pnlCustomer.add(lblPhone, gbc);

    gbc.gridx = 1;
    pnlCustomer.add(lblAddress, gbc);

    pnlCustomer.revalidate();
    pnlCustomer.repaint();

    
    pnlService.removeAll();
    pnlService.setLayout(new java.awt.GridBagLayout());
    gbc = new java.awt.GridBagConstraints();
    gbc.anchor = java.awt.GridBagConstraints.WEST;
    gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

    
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.insets = new java.awt.Insets(0, 0, 12, 0);
    pnlService.add(jLabel11, gbc);

    gbc.gridwidth = 1;

   
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.33;
    gbc.insets = new java.awt.Insets(2, 8, 0, 8);
    pnlService.add(jLabel15, gbc);
  
    gbc.gridx = 1;
    gbc.insets = new java.awt.Insets(2, 8, 0, 8);
    pnlService.add(jLabel6, gbc);
    
    gbc.gridx = 2;
    gbc.insets = new java.awt.Insets(2, 8, 0, 8);
    pnlService.add(jLabel7, gbc);
   
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.insets = new java.awt.Insets(0, 8, 4, 8);
    pnlService.add(lblEmployee, gbc);
    
    gbc.gridx = 1;
    gbc.insets = new java.awt.Insets(0, 8, 4, 8);
    pnlService.add(lblServiceType, gbc);
    
    gbc.gridx = 2;
    gbc.insets = new java.awt.Insets(0, 8, 4, 8);
    pnlService.add(lblWeight, gbc);

    pnlService.revalidate();
    pnlService.repaint();
}
   
    private void setupWorkflowLayout() {
        pnlWorkflow.removeAll();
        pnlWorkflow.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(2, 5, 2, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new java.awt.Insets(0, 0, 10, 0);
        pnlWorkflow.add(jLabel12, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new java.awt.Insets(2, 0, 2, 0);
        pnlWorkflow.add(jLabel8, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new java.awt.Insets(2, 0, 10, 0);
        gbc.ipady = 2;
        pnlWorkflow.add(jComboBox1, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new java.awt.Insets(2, 0, 2, 0);
        gbc.ipady = 0;
        pnlWorkflow.add(jLabel13, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new java.awt.Insets(2, 0, 10, 0);
        gbc.ipady = 2;
        pnlWorkflow.add(jComboBox2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new java.awt.Insets(0, 0, 10, 0);
        gbc.ipady = 0;
        pnlWorkflow.add(jLabel14, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new java.awt.Insets(4, 0, 2, 0);
        gbc.ipady = 6;
        pnlWorkflow.add(btnUpdate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new java.awt.Insets(2, 0, 0, 0);
        gbc.ipady = 6;
        pnlWorkflow.add(btnCancel, gbc);

        pnlWorkflow.revalidate();
        pnlWorkflow.repaint();
    }


   private void setupEnhancedLayout() {
    pnlEditorContent.removeAll();
    pnlEditorContent.setLayout(new java.awt.GridBagLayout());
    java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
    gbc.insets = new java.awt.Insets(8, 12, 8, 12);
    gbc.fill = java.awt.GridBagConstraints.BOTH;
   
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.6;
    gbc.weighty = 0.5;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    pnlEditorContent.add(pnlCustomer, gbc);
   
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.6;
    gbc.weighty = 0.5;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    pnlEditorContent.add(pnlService, gbc);
  
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 0.4;
    gbc.weighty = 1.0;
    gbc.gridwidth = 1;
    gbc.gridheight = 2;
    pnlEditorContent.add(pnlWorkflow, gbc);

    pnlEditorContent.revalidate();
    pnlEditorContent.repaint();

    filteredTable.setBorder(null);
    filteredTable.setViewportBorder(null);
    tblOrders.setBorder(null);

    styleCardLayout();
}

    public void refreshData() {
        loadTableData();

        this.revalidate();
        this.repaint();
    }

    public void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) tblOrders.getModel();
        model.setRowCount(0);

        String query = "SELECT o.claim_number, e.name AS employee_name, c.name, c.phone, c.address, s.service_name, o.weight_kg, o.order_status, o.payment_status, o.total_amount "
        + "FROM orders o "
        + "JOIN customers c ON o.customer_id = c.customer_id "
        + "JOIN services s ON o.service_id = s.service_id "
        + "LEFT JOIN employees e ON o.employee_id = e.employee_id "
        + "WHERE o.order_status != 'Claimed'";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
    String employeeName = rs.getString("employee_name");
    model.addRow(new Object[]{
        rs.getString("claim_number"),
        employeeName != null ? employeeName : "Unassigned",
        rs.getString("name"),
        rs.getString("phone"),
        rs.getString("address"),
        rs.getString("service_name"),
        rs.getDouble("weight_kg"),
        rs.getString("order_status"),
        rs.getString("payment_status"),
        "\u20b1" + String.format("%,.2f", rs.getDouble("total_amount"))
    });
}
        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }

    }

   
  private void applyStyles() {
    
    Font headerFont = new java.awt.Font("Inter", java.awt.Font.BOLD, 14);
    Color headerColor = new Color(26, 28, 28);

    jLabel10.setFont(headerFont);
    jLabel10.setForeground(headerColor);
    jLabel10.setText("CUSTOMER CONTACT");

    jLabel11.setFont(headerFont);
    jLabel11.setForeground(headerColor);
    jLabel11.setText("SERVICE SUMMARY");

    jLabel12.setFont(headerFont);
    jLabel12.setForeground(headerColor);
    jLabel12.setText("UPDATE WORKFLOW STATUS");

    if (lblPageTitle != null) {
        lblPageTitle.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 28));
        lblPageTitle.setForeground(new java.awt.Color(26, 28, 28));
    }

    if (lblsubtile1 != null) {
        lblsubtile1.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.PLAIN, 14));
        lblsubtile1.setForeground(new java.awt.Color(67, 70, 84));
    }

    if (lblsubtitle2 != null) {
        lblsubtitle2.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.PLAIN, 14));
        lblsubtitle2.setForeground(new java.awt.Color(67, 70, 84));
    }

    if (jLabel9 != null) {
        jLabel9.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 28));
        jLabel9.setForeground(new java.awt.Color(26, 28, 28));
    }

   
    Font labelFont = new java.awt.Font("Inter", java.awt.Font.PLAIN, 14);
    Color labelColor = new Color(67, 70, 84);

    jLabel2.setFont(labelFont);
    jLabel2.setForeground(labelColor);
    jLabel2.setText("CLAIM NUMBER");

    jLabel3.setFont(labelFont);
    jLabel3.setForeground(labelColor);
    jLabel3.setText("PHONE NUMBER");

    jLabel4.setFont(labelFont);
    jLabel4.setForeground(labelColor);
    jLabel4.setText("FULL NAME");

    jLabel5.setFont(labelFont);
    jLabel5.setForeground(labelColor);
    jLabel5.setText("ADDRESS");

    jLabel15.setFont(labelFont);
    jLabel15.setForeground(labelColor);
    jLabel15.setText("PROCESSED BY");

    jLabel6.setFont(labelFont);
    jLabel6.setForeground(labelColor);
    jLabel6.setText("SERVICE TYPE");

    jLabel7.setFont(labelFont);
    jLabel7.setForeground(labelColor);
    jLabel7.setText("WEIGHT");

    jLabel8.setFont(labelFont);
    jLabel8.setForeground(labelColor);
    jLabel8.setText("CHANGE STATUS TO");

    jLabel13.setFont(labelFont);
    jLabel13.setForeground(labelColor);
    jLabel13.setText("PAYMENT STATUS");

    jLabel14.setFont(new java.awt.Font("Inter", java.awt.Font.ITALIC, 12));
    jLabel14.setForeground(new Color(115, 118, 134));
    jLabel14.setText("Note: Order cannot be Claimed unless Paid");

    // Value labels — consistent Inter Bold 14 across all fields
    Font valueFont = new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 14);
    Color valueColor = new Color(26, 28, 28);

    lblClaimNumber.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 16));
    lblClaimNumber.setForeground(new Color(38, 85, 189));

    lblFullName.setFont(valueFont);
    lblFullName.setForeground(valueColor);

    lblPhone.setFont(valueFont);
    lblPhone.setForeground(valueColor);

    lblAddress.setFont(valueFont);
    lblAddress.setForeground(valueColor);

    lblEmployee.setFont(valueFont);
    lblEmployee.setForeground(new Color(38, 85, 189));

    lblServiceType.setFont(valueFont);
    lblServiceType.setForeground(valueColor);

    lblWeight.setFont(valueFont);
    lblWeight.setForeground(valueColor);

    jComboBox1.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
    jComboBox1.setBackground(Color.WHITE);
    jComboBox1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(195, 198, 215), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
    ));
    jComboBox1.setPreferredSize(new java.awt.Dimension(200, 32));

    jComboBox2.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
    jComboBox2.setBackground(Color.WHITE);
    jComboBox2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(195, 198, 215), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
    ));
    jComboBox2.setPreferredSize(new java.awt.Dimension(200, 32));

    
    btnUpdate.setBackground(new Color(38, 85, 189));
    btnUpdate.setForeground(Color.WHITE);
    btnUpdate.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
    btnUpdate.setFocusPainted(false);
    btnUpdate.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    btnUpdate.setPreferredSize(new java.awt.Dimension(200, 38));

    btnCancel.setBackground(new Color(226, 226, 226));
    btnCancel.setForeground(new Color(67, 70, 84));
    btnCancel.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
    btnCancel.setFocusPainted(false);
    btnCancel.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    btnCancel.setPreferredSize(new java.awt.Dimension(200, 38));
}
     
    
    public static void main(String[] args) {
        FlatLightLaf.setup();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 700);
                frame.add(new UpdateStatusPanel());
                frame.setVisible(true);
            }
        });
    }

    // --- Dynamic Search Injection ---
    private javax.swing.JTextField txtSearch;
    private javax.swing.JComboBox<String> cbFilter;
    private javax.swing.JComboBox<String> cbPayment;
    private javax.swing.JButton btnSearch;
    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> rowSorter;

    private void setupSearchFunctionality() {
        txtSearch = new javax.swing.JTextField(20);
        txtSearch.putClientProperty("JTextField.placeholderText", "Search Claim # or Customer");
        txtSearch.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        txtSearch.setPreferredSize(new java.awt.Dimension(320, 44));
        
        cbFilter = new javax.swing.JComboBox<>(new String[]{"All", "Pending", "Processing", "Ready"});
        cbFilter.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        cbFilter.setPreferredSize(new java.awt.Dimension(160, 44));
        
        cbPayment = new javax.swing.JComboBox<>(new String[]{"All", "Paid", "Unpaid"});
        cbPayment.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        cbPayment.setPreferredSize(new java.awt.Dimension(160, 44));
        
        btnSearch = new javax.swing.JButton("Search");
        btnSearch.setBackground(new java.awt.Color(38, 85, 189));
        btnSearch.setForeground(java.awt.Color.WHITE);
        btnSearch.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        btnSearch.setPreferredSize(new java.awt.Dimension(90, 44));
        btnSearch.putClientProperty("JButton.buttonType", "roundRect");
        btnSearch.setFocusPainted(false);
        
        javax.swing.JPanel pnlSearch = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 10));
        pnlSearch.setBackground(new java.awt.Color(249, 249, 249));
        pnlSearch.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        javax.swing.JLabel lblFilter = new javax.swing.JLabel("Filter by Status:");
        lblFilter.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 12));
        lblFilter.setForeground(new java.awt.Color(67, 70, 84));
        
        javax.swing.JLabel lblPayment = new javax.swing.JLabel("Filter by Payment:");
        lblPayment.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 12));
        lblPayment.setForeground(new java.awt.Color(67, 70, 84));
        
        javax.swing.JLabel lblSearch = new javax.swing.JLabel(" Search:");
        lblSearch.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 12));
        lblSearch.setForeground(new java.awt.Color(67, 70, 84));
        
        pnlSearch.add(lblFilter);
        pnlSearch.add(cbFilter);
        pnlSearch.add(lblPayment);
        pnlSearch.add(cbPayment);
        pnlSearch.add(lblSearch);
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnSearch);
        
        // Safe injection into GUI Builder layout
        cardTable.remove(pnlHeader);
        javax.swing.JPanel topContainer = new javax.swing.JPanel(new java.awt.BorderLayout());
        topContainer.setBackground(new java.awt.Color(249, 249, 249));
        topContainer.add(pnlHeader, java.awt.BorderLayout.NORTH);
        topContainer.add(pnlSearch, java.awt.BorderLayout.SOUTH);
        cardTable.add(topContainer, java.awt.BorderLayout.PAGE_START);
        
        // Table filtering wiring
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblOrders.getModel();
        rowSorter = new javax.swing.table.TableRowSorter<>(model);
        tblOrders.setRowSorter(rowSorter);
        
        java.awt.event.ActionListener searchAction = e -> executeSearch();
        btnSearch.addActionListener(searchAction);
        cbFilter.addActionListener(searchAction);
        cbPayment.addActionListener(searchAction);
        
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    executeSearch();
                }
            }
        });
    }

    private void executeSearch() {
        if (rowSorter == null) return;
        
        String text = txtSearch.getText().trim();
        String status = (String) cbFilter.getSelectedItem();
        String payment = (String) cbPayment.getSelectedItem();
        
        java.util.List<javax.swing.RowFilter<Object, Object>> filters = new java.util.ArrayList<>();
        
        if (!text.isEmpty()) {
            filters.add(javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(text), 0, 2));
        }
        
        if (!"All".equals(status)) {
            filters.add(javax.swing.RowFilter.regexFilter("(?i)^" + status + "$", 7));
        }
        
        if (!"All".equals(payment)) {
            filters.add(javax.swing.RowFilter.regexFilter("(?i)^" + payment + "$", 8));
        }
        
        if (filters.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(javax.swing.RowFilter.andFilter(filters));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {
                java.awt.GridBagConstraints gridBagConstraints;

                jPanel1 = new javax.swing.JPanel();
                updateSubContainer = new javax.swing.JPanel();
                cardTable = new javax.swing.JPanel();
                pnlHeader = new javax.swing.JPanel();
                lblPageTitle = new javax.swing.JLabel();
                lblsubtile1 = new javax.swing.JLabel();
                pnlContent = new javax.swing.JPanel();
                filteredTable = new javax.swing.JScrollPane();
                tblOrders = new javax.swing.JTable();
                pnlFooter = new javax.swing.JPanel();
                cardEditor = new javax.swing.JPanel();
                pnlEditorHeader = new javax.swing.JPanel();
                jLabel9 = new javax.swing.JLabel();
                lblsubtitle2 = new javax.swing.JLabel();
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
                jLabel12 = new javax.swing.JLabel();
                jComboBox2 = new javax.swing.JComboBox<>();
                jLabel13 = new javax.swing.JLabel();
                jLabel14 = new javax.swing.JLabel();
                pnlService = new javax.swing.JPanel();
                lblServiceType = new javax.swing.JLabel();
                lblWeight = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();
                jLabel11 = new javax.swing.JLabel();
                jLabel15 = new javax.swing.JLabel();
                lblEmployee = new javax.swing.JLabel();
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

                setBackground(new java.awt.Color(249, 249, 249));
                setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
                setLayout(new java.awt.BorderLayout());

                updateSubContainer.setBackground(new java.awt.Color(249, 249, 249));
                updateSubContainer.setLayout(new java.awt.CardLayout());

                cardTable.setLayout(new java.awt.BorderLayout());

                pnlHeader.setBackground(new java.awt.Color(249, 249, 249));

                lblPageTitle.setFont(new java.awt.Font("Inter 18pt", 1, 28)); // NOI18N
                lblPageTitle.setText("Update Order Status");

                lblsubtile1.setFont(new java.awt.Font("Inter 18pt", 0, 14)); // NOI18N
                lblsubtile1.setForeground(new java.awt.Color(67, 70, 84));
                lblsubtile1.setText("Select an order to update its current workflow progress.");

                javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
                pnlHeader.setLayout(pnlHeaderLayout);
                pnlHeaderLayout.setHorizontalGroup(
                        pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblsubtile1)
                                        .addComponent(lblPageTitle))
                                .addGap(0, 895, Short.MAX_VALUE))
                );
                pnlHeaderLayout.setVerticalGroup(
                        pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(lblPageTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblsubtile1)
                                .addContainerGap(49, Short.MAX_VALUE))
                );

                cardTable.add(pnlHeader, java.awt.BorderLayout.PAGE_START);

                pnlContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
                pnlContent.setLayout(new java.awt.BorderLayout());

                filteredTable.setBackground(new java.awt.Color(249, 249, 249));
                filteredTable.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 0, 24, 24));

                tblOrders.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
                tblOrders.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null}
                        },
                        new String [] {
                                "Claim Number", "Employee", "Customer", "Phone Number", "Address", "Service Type", "Weight (kg)", "Status", "Payment", "Total Amount"
                        }
                ) {
                        Class[] types = new Class [] {
                                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class
                        };
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false, false, false, false
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

                pnlEditorHeader.setBackground(new java.awt.Color(249, 249, 249));
                pnlEditorHeader.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

                jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
                jLabel9.setText("Update Order Status");

                lblsubtitle2.setText("View order specifics and modify the current processing stage.");

                javax.swing.GroupLayout pnlEditorHeaderLayout = new javax.swing.GroupLayout(pnlEditorHeader);
                pnlEditorHeader.setLayout(pnlEditorHeaderLayout);
                pnlEditorHeaderLayout.setHorizontalGroup(
                        pnlEditorHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlEditorHeaderLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(pnlEditorHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblsubtitle2)
                                        .addComponent(jLabel9))
                                .addContainerGap(1696, Short.MAX_VALUE))
                );
                pnlEditorHeaderLayout.setVerticalGroup(
                        pnlEditorHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditorHeaderLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblsubtitle2)
                                .addContainerGap(53, Short.MAX_VALUE))
                );

                cardEditor.add(pnlEditorHeader, java.awt.BorderLayout.PAGE_START);

                pnlEditorContent.setBackground(new java.awt.Color(249, 249, 249));
                pnlEditorContent.setLayout(new java.awt.GridBagLayout());

                pnlCustomer.setBackground(new java.awt.Color(255, 255, 255));
                pnlCustomer.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
                pnlCustomer.setLayout(new java.awt.GridBagLayout());

                lblClaimNumber.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                lblClaimNumber.setForeground(new java.awt.Color(51, 153, 255));
                lblClaimNumber.setText("#LS-260627-001");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.gridwidth = 5;
                gridBagConstraints.gridheight = 2;
                gridBagConstraints.ipadx = 30;
                gridBagConstraints.ipady = 8;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(4, 20, 0, 0);
                pnlCustomer.add(lblClaimNumber, gridBagConstraints);

                lblFullName.setText("Stephanie Casimiro");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 5;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.gridwidth = 3;
                gridBagConstraints.ipadx = 3;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(4, 40, 0, 0);
                pnlCustomer.add(lblFullName, gridBagConstraints);

                lblPhone.setText("+63 99999999912");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 5;
                gridBagConstraints.gridwidth = 3;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(4, 20, 16, 0);
                pnlCustomer.add(lblPhone, gridBagConstraints);

                lblAddress.setText("Brgy. GenZ, Pasig City, Albay");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 5;
                gridBagConstraints.gridy = 5;
                gridBagConstraints.gridwidth = 4;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(4, 40, 16, 20);
                pnlCustomer.add(lblAddress, gridBagConstraints);

                jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
                jLabel2.setText("Claim Number");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.ipady = -4;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(4, 20, 0, 0);
                pnlCustomer.add(jLabel2, gridBagConstraints);

                jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
                jLabel3.setText("Phone Number");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 4;
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(16, 20, 0, 0);
                pnlCustomer.add(jLabel3, gridBagConstraints);

                jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
                jLabel4.setText("Full Name");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 5;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(18, 40, 0, 0);
                pnlCustomer.add(jLabel4, gridBagConstraints);

                jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
                jLabel5.setText("Address");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 5;
                gridBagConstraints.gridy = 4;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(16, 40, 0, 0);
                pnlCustomer.add(jLabel5, gridBagConstraints);

                jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel10.setText("Customer Contact");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridwidth = 4;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(16, 20, 0, 0);
                pnlCustomer.add(jLabel10, gridBagConstraints);

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.ipadx = 288;
                gridBagConstraints.ipady = 33;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(20, 24, 8, 24);
                pnlEditorContent.add(pnlCustomer, gridBagConstraints);

                pnlWorkflow.setBackground(new java.awt.Color(255, 255, 255));
                pnlWorkflow.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ready", "Pending", "Processing", "Claimed" }));
                jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

                btnUpdate.setBackground(new java.awt.Color(51, 153, 255));
                btnUpdate.setText("Update");
                btnUpdate.addActionListener(this::btnUpdateActionPerformed);

                btnCancel.setText("Cancel");
                btnCancel.addActionListener(this::btnCancelActionPerformed);

                jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
                jLabel8.setText("CHANGE STATUS TO");

                jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel12.setText("Update Workflow Status");

                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Paid", "Unpaid" }));
                jComboBox2.addActionListener(this::jComboBox2ActionPerformed);

                jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
                jLabel13.setText("PAYMENT STATUS");

                jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
                jLabel14.setText("Note: Order cannot be Claimed unless Paid");

                javax.swing.GroupLayout pnlWorkflowLayout = new javax.swing.GroupLayout(pnlWorkflow);
                pnlWorkflow.setLayout(pnlWorkflowLayout);
                pnlWorkflowLayout.setHorizontalGroup(
                        pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlWorkflowLayout.createSequentialGroup()
                                .addGroup(pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlWorkflowLayout.createSequentialGroup()
                                                .addGap(60, 60, 60)
                                                .addGroup(pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                        .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(pnlWorkflowLayout.createSequentialGroup()
                                                .addGap(32, 32, 32)
                                                .addGroup(pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel12)
                                                        .addComponent(jLabel8)))
                                        .addGroup(pnlWorkflowLayout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jComboBox1, 0, 221, Short.MAX_VALUE)
                                                                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(pnlWorkflowLayout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addGroup(pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel13)
                                                                        .addComponent(jLabel14))))))
                                .addContainerGap(57, Short.MAX_VALUE))
                );
                pnlWorkflowLayout.setVerticalGroup(
                        pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlWorkflowLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))
                );

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridheight = 2;
                gridBagConstraints.ipadx = 17;
                gridBagConstraints.ipady = 8;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(6, 59, 39, 64);
                pnlEditorContent.add(pnlWorkflow, gridBagConstraints);

                pnlService.setBackground(new java.awt.Color(255, 255, 255));
                pnlService.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

                lblServiceType.setText("Wash/Dry/Fold");

                lblWeight.setText("7.00 kg");

                jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
                jLabel6.setText("Service Type");

                jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
                jLabel7.setText("Weight");

                jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel11.setText("Service Summary");

                jLabel15.setText("Employee");

                lblEmployee.setText("Nics");

                javax.swing.GroupLayout pnlServiceLayout = new javax.swing.GroupLayout(pnlService);
                pnlService.setLayout(pnlServiceLayout);
                pnlServiceLayout.setHorizontalGroup(
                        pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlServiceLayout.createSequentialGroup()
                                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlServiceLayout.createSequentialGroup()
                                                .addGap(79, 79, 79)
                                                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel15)
                                                        .addComponent(lblEmployee))
                                                .addGap(93, 93, 93)
                                                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel6)
                                                        .addComponent(lblServiceType))
                                                .addGap(110, 110, 110)
                                                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblWeight)
                                                        .addComponent(jLabel7)))
                                        .addGroup(pnlServiceLayout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addComponent(jLabel11)))
                                .addContainerGap(1134, Short.MAX_VALUE))
                );
                pnlServiceLayout.setVerticalGroup(
                        pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlServiceLayout.createSequentialGroup()
                                .addContainerGap(36, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblServiceType)
                                        .addComponent(lblWeight)
                                        .addComponent(lblEmployee))
                                .addGap(25, 25, 25))
                );

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.ipadx = 376;
                gridBagConstraints.ipady = 10;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(18, 32, 39, 0);
                pnlEditorContent.add(pnlService, gridBagConstraints);

                cardEditor.add(pnlEditorContent, java.awt.BorderLayout.CENTER);

                pnlEditorFooter.setBackground(new java.awt.Color(249, 249, 249));
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
            String employeeName = tblOrders.getValueAt(selectedRow, 1).toString();
            String fullName = tblOrders.getValueAt(selectedRow, 2).toString();
            String phone = tblOrders.getValueAt(selectedRow, 3).toString();
            String address = tblOrders.getValueAt(selectedRow, 4).toString();
            String service = tblOrders.getValueAt(selectedRow, 5).toString();
            String weight = tblOrders.getValueAt(selectedRow, 6).toString();
            String currentStatus = tblOrders.getValueAt(selectedRow, 7).toString();
            String paymentStatus = tblOrders.getValueAt(selectedRow, 8).toString();

lblClaimNumber.setText(claimNum);
lblEmployee.setText(employeeName);
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
            }
            if ("Ready".equals(currentStatus)) {
                jComboBox1.addItem("Ready");

                if ("Paid".equalsIgnoreCase(paymentStatus)) {
                    jComboBox1.addItem("Claimed");
                }
            }
            jComboBox1.setSelectedItem(currentStatus);
            jComboBox2.setSelectedItem(paymentStatus);

            cl.show(updateSubContainer, "card3");

        }
    }//GEN-LAST:event_tblOrdersMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        String newStatus = jComboBox1.getSelectedItem().toString();
        String newPaymentStatus = jComboBox2.getSelectedItem().toString();
        String claimId = lblClaimNumber.getText();

        boolean isClaimed = "Claimed".equals(newStatus);
        String query = isClaimed 
            ? "UPDATE orders SET order_status = ?, payment_status = ?, claimed_at = CURRENT_TIMESTAMP WHERE claim_number = ?"
            : "UPDATE orders SET order_status = ?, payment_status = ? WHERE claim_number = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newStatus);
            pstmt.setString(2, newPaymentStatus);
            pstmt.setString(3, claimId);
            pstmt.executeUpdate();

            javax.swing.JOptionPane.showMessageDialog(this, "Order updated to: " + newStatus + "\nOrder updated to: " + newPaymentStatus);

            loadTableData();

            java.awt.CardLayout cl = (java.awt.CardLayout) updateSubContainer.getLayout();
            cl.show(updateSubContainer, "card2");

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error updating status: " + e.getMessage());
        }
    }                                         

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        java.awt.CardLayout cl = (java.awt.CardLayout) updateSubContainer.getLayout();
        cl.show(updateSubContainer, "card2");
    }//GEN-LAST:event_btnCancelActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnCancel;
        private javax.swing.JButton btnUpdate;
        private javax.swing.JPanel cardEditor;
        private javax.swing.JPanel cardTable;
        private javax.swing.JScrollPane filteredTable;
        private javax.swing.JComboBox<String> jComboBox1;
        private javax.swing.JComboBox<String> jComboBox2;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
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
        private javax.swing.JLabel lblEmployee;
        private javax.swing.JLabel lblFullName;
        private javax.swing.JLabel lblPageTitle;
        private javax.swing.JLabel lblPhone;
        private javax.swing.JLabel lblServiceType;
        private javax.swing.JLabel lblWeight;
        private javax.swing.JLabel lblsubtile1;
        private javax.swing.JLabel lblsubtitle2;
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
