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
import javax.swing.border.Border;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
/**
 *
 * @author Cral
 */
public class UpdateStatusPanel extends javax.swing.JPanel {

    /**
     * Creates new form UpdateStatusPanel
     */
    public UpdateStatusPanel() {
        loadCustomFonts();
        initComponents();
        setupIcons();  
        setupLayout();
        loadTableData();
        setupTableStyles(); 
        setupEnhancedLayout();
        
         SwingUtilities.invokeLater(() -> {
        pnlEditorContent.revalidate();
        pnlEditorContent.repaint();
    });
     
        
    }
    
    
    
    
    
    
    
    private void setupLayout() {
    pnlContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 24, 24, 24));
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
        
       
        c.setForeground(new java.awt.Color(51, 153, 255)); 
        c.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        

        return c;
    }
};
    
    
    private class ChipCellRenderer extends DefaultTableCellRenderer {
    private Color chipBg = Color.WHITE;

    public ChipCellRenderer() {
        setHorizontalAlignment(JLabel.CENTER);
        setOpaque(false); // important: we paint our own background
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
tblOrders.getTableHeader().setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 14));
    
tblOrders.getColumnModel().getColumn(6).setCellRenderer(new ChipCellRenderer()); // Status
tblOrders.getColumnModel().getColumn(7).setCellRenderer(new ChipCellRenderer()); // Payment

DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
centerRenderer.setHorizontalAlignment(JLabel.CENTER);
tblOrders.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); 
tblOrders.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);


DefaultTableCellRenderer amountRenderer = new DefaultTableCellRenderer();
amountRenderer.setHorizontalAlignment(JLabel.CENTER);
tblOrders.getColumnModel().getColumn(8).setCellRenderer(amountRenderer);
    
    }
  
       
private void styleCardLayout() {
    // Background colors
    pnlEditorContent.setBackground(new Color(249, 249, 249));
    cardEditor.setBackground(new Color(249, 249, 249));
    pnlEditorHeader.setBackground(new Color(249, 249, 249));
    pnlEditorFooter.setBackground(new Color(249, 249, 249));
    
    // Card backgrounds
    pnlCustomer.setBackground(Color.WHITE);
    pnlService.setBackground(Color.WHITE);
    pnlWorkflow.setBackground(Color.WHITE);
    
    // Customer Panel
    pnlCustomer.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(195, 198, 215), 1),
        BorderFactory.createEmptyBorder(8, 12, 8, 12)
    ));
    
    // Service Panel
    pnlService.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(195, 198, 215), 1),
        BorderFactory.createEmptyBorder(8, 12, 8, 12)
    ));
    
    // Workflow Panel
    pnlWorkflow.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(195, 198, 215), 1),
        BorderFactory.createEmptyBorder(12, 15, 12, 15)
    ));
    
    setupInnerLayouts();
    setupWorkflowLayout();
    
    styleLabelsAndValues();
    styleButtons();
    styleComboBoxes();
}


private void setupInnerLayouts() {
    // --- Customer Panel ---
    pnlCustomer.removeAll();
    pnlCustomer.setLayout(new java.awt.GridBagLayout());
    java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
    gbc.anchor = java.awt.GridBagConstraints.WEST;
    gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
    
    // Header
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.insets = new java.awt.Insets(0, 0, 8, 0);
    pnlCustomer.add(jLabel10, gbc);
    
    gbc.gridwidth = 1;
    
    // Row 1: CLAIM NUMBER
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.5;
    gbc.insets = new java.awt.Insets(2, 5, 0, 5);
    pnlCustomer.add(jLabel2, gbc);
    
    gbc.gridx = 1;
    pnlCustomer.add(jLabel4, gbc);
    
    // Row 2: Values
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.insets = new java.awt.Insets(0, 5, 8, 5);
    pnlCustomer.add(lblClaimNumber, gbc);
    
    gbc.gridx = 1;
    pnlCustomer.add(lblFullName, gbc);
    
    // Row 3: PHONE NUMBER
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.insets = new java.awt.Insets(2, 5, 0, 5);
    pnlCustomer.add(jLabel3, gbc);
    
    gbc.gridx = 1;
    pnlCustomer.add(jLabel5, gbc);
    
    // Row 4: Values
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.insets = new java.awt.Insets(0, 5, 0, 5);
    pnlCustomer.add(lblPhone, gbc);
    
    gbc.gridx = 1;
    pnlCustomer.add(lblAddress, gbc);
    
    // --- Service Panel ---
    pnlService.removeAll();
    pnlService.setLayout(new java.awt.GridBagLayout());
    gbc = new java.awt.GridBagConstraints();
    gbc.anchor = java.awt.GridBagConstraints.WEST;
    gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
    
    // Header
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.insets = new java.awt.Insets(0, 0, 8, 0);
    pnlService.add(jLabel11, gbc);
    
    gbc.gridwidth = 1;
    
    // Row 1: SERVICE TYPE
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.5;
    gbc.insets = new java.awt.Insets(2, 5, 0, 5);
    pnlService.add(jLabel6, gbc);
    
    gbc.gridx = 1;
    pnlService.add(jLabel7, gbc);
    
    // Row 2: Values
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.insets = new java.awt.Insets(0, 5, 0, 5);
    pnlService.add(lblServiceType, gbc);
    
    gbc.gridx = 1;
    pnlService.add(lblWeight, gbc);
    
    pnlCustomer.revalidate();
    pnlCustomer.repaint();
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
    
    // Header
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new java.awt.Insets(0, 0, 10, 0);
    pnlWorkflow.add(jLabel12, gbc);
    
    // CHANGE STATUS TO
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.insets = new java.awt.Insets(2, 0, 2, 0);
    pnlWorkflow.add(jLabel8, gbc);
    
    // Status ComboBox
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.insets = new java.awt.Insets(2, 0, 10, 0);
    gbc.ipady = 2;
    pnlWorkflow.add(jComboBox1, gbc);
    
    // PAYMENT STATUS
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.insets = new java.awt.Insets(2, 0, 2, 0);
    gbc.ipady = 0;
    pnlWorkflow.add(jLabel13, gbc);
    
    // Payment ComboBox
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.insets = new java.awt.Insets(2, 0, 10, 0);
    gbc.ipady = 2;
    pnlWorkflow.add(jComboBox2, gbc);
    
    // Note
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.insets = new java.awt.Insets(0, 0, 10, 0);
    gbc.ipady = 0;
    pnlWorkflow.add(jLabel14, gbc);
    
    // Update button
    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.insets = new java.awt.Insets(4, 0, 2, 0);
    gbc.ipady = 6;
    pnlWorkflow.add(btnUpdate, gbc);
    
    // Cancel button
    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.insets = new java.awt.Insets(2, 0, 0, 0);
    gbc.ipady = 6;
    pnlWorkflow.add(btnCancel, gbc);
    
    pnlWorkflow.revalidate();
    pnlWorkflow.repaint();
}


private void styleLabelsAndValues() {
    // Headers
    Font headerFont = new java.awt.Font("Inter", java.awt.Font.BOLD, 10);
    Color headerColor = new Color(67, 70, 84);

    jLabel10.setFont(headerFont);
    jLabel10.setForeground(headerColor);
    jLabel10.setText("CUSTOMER CONTACT");

    jLabel11.setFont(headerFont);
    jLabel11.setForeground(headerColor);
    jLabel11.setText("SERVICE SUMMARY");

    jLabel12.setFont(headerFont);
    jLabel12.setForeground(headerColor);
    jLabel12.setText("UPDATE WORKFLOW STATUS");

    // Labels
    Font labelFont = new java.awt.Font("Inter", java.awt.Font.BOLD, 8);
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

    jLabel14.setFont(new java.awt.Font("Inter", java.awt.Font.ITALIC, 8));
    jLabel14.setForeground(new Color(115, 118, 134));

    // Values
    Font valueFont = new java.awt.Font("Inter", java.awt.Font.BOLD, 12);
    Color valueColor = new Color(26, 28, 28);

    lblClaimNumber.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
    lblClaimNumber.setForeground(new Color(38, 85, 189));

    lblFullName.setFont(valueFont);
    lblFullName.setForeground(valueColor);

    lblPhone.setFont(valueFont);
    lblPhone.setForeground(valueColor);

    lblAddress.setFont(valueFont);
    lblAddress.setForeground(valueColor);

    lblServiceType.setFont(valueFont);
    lblServiceType.setForeground(valueColor);

    lblWeight.setFont(valueFont);
    lblWeight.setForeground(valueColor);

    // Main header
    jLabel9.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 20));
    jLabel9.setForeground(new Color(26, 28, 28));
}


private void styleComboBoxes() {
    jComboBox1.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 12));
    jComboBox1.setBackground(Color.WHITE);
    jComboBox1.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(195, 198, 215), 1),
        BorderFactory.createEmptyBorder(3, 8, 3, 8)
    ));
    jComboBox1.setPreferredSize(new java.awt.Dimension(180, 26));
    
    jComboBox2.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 12));
    jComboBox2.setBackground(Color.WHITE);
    jComboBox2.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(195, 198, 215), 1),
        BorderFactory.createEmptyBorder(3, 8, 3, 8)
    ));
    jComboBox2.setPreferredSize(new java.awt.Dimension(180, 26));
}

private void styleButtons() {
    btnUpdate.setBackground(new Color(38, 85, 189));
    btnUpdate.setForeground(Color.WHITE);
    btnUpdate.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 12));
    btnUpdate.setFocusPainted(false);
    btnUpdate.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    btnUpdate.setPreferredSize(new java.awt.Dimension(180, 28));
    
    btnCancel.setBackground(new Color(226, 226, 226));
    btnCancel.setForeground(new Color(67, 70, 84));
    btnCancel.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 12));
    btnCancel.setFocusPainted(false);
    btnCancel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    btnCancel.setPreferredSize(new java.awt.Dimension(180, 28));
}
       

private void setupEnhancedLayout() {
    pnlEditorContent.removeAll();
    pnlEditorContent.setLayout(new java.awt.GridBagLayout());
    java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
    gbc.insets = new java.awt.Insets(5, 8, 5, 8);
    gbc.fill = java.awt.GridBagConstraints.BOTH;
    
    // Customer Panel
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.6;
    gbc.weighty = 0.5;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    pnlEditorContent.add(pnlCustomer, gbc);
    
    // Service Panel
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.6;
    gbc.weighty = 0.5;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    pnlEditorContent.add(pnlService, gbc);
    
    // Workflow Panel
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 0.4;
    gbc.weighty = 1.0;
    gbc.gridwidth = 1;
    gbc.gridheight = 2;
    pnlEditorContent.add(pnlWorkflow, gbc);
    
    pnlEditorContent.revalidate();
    pnlEditorContent.repaint();
    
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

    
    String query = "SELECT o.claim_number, c.name, c.phone, c.address, s.service_name, o.weight_kg, o.order_status, o.payment_status, o.total_amount " +
               "FROM orders o " +
               "JOIN customers c ON o.customer_id = c.customer_id " +
               "JOIN services s ON o.service_id = s.service_id " +
               "WHERE o.order_status != 'Claimed'";

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
        rs.getString("payment_status"),
        "\u20b1" + String.format("%,.2f", rs.getDouble("total_amount"))
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
    }

    }
 /*   
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
  
    */
    
    
    
    
    
    
    
    
    
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
        java.awt.GridBagConstraints gridBagConstraints;

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
                .addContainerGap(894, Short.MAX_VALUE))
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
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Claim Number", "Customer", "Phone Number", "Address", "Service Type", "Weight (kg)", "Status", "Payment", "Total Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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

        javax.swing.GroupLayout pnlEditorHeaderLayout = new javax.swing.GroupLayout(pnlEditorHeader);
        pnlEditorHeader.setLayout(pnlEditorHeaderLayout);
        pnlEditorHeaderLayout.setHorizontalGroup(
            pnlEditorHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditorHeaderLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel9)
                .addContainerGap(865, Short.MAX_VALUE))
        );
        pnlEditorHeaderLayout.setVerticalGroup(
            pnlEditorHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditorHeaderLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(39, 39, 39))
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
                .addContainerGap(23, Short.MAX_VALUE))
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

        javax.swing.GroupLayout pnlServiceLayout = new javax.swing.GroupLayout(pnlService);
        pnlService.setLayout(pnlServiceLayout);
        pnlServiceLayout.setHorizontalGroup(
            pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServiceLayout.createSequentialGroup()
                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlServiceLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblServiceType)
                            .addComponent(jLabel6))
                        .addGap(211, 211, 211)
                        .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(lblWeight)))
                    .addGroup(pnlServiceLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel11)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlServiceLayout.setVerticalGroup(
            pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlServiceLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWeight)
                    .addComponent(lblServiceType))
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
            String fullName = tblOrders.getValueAt(selectedRow, 1).toString();
            String phone = tblOrders.getValueAt(selectedRow, 2).toString();
            String address = tblOrders.getValueAt(selectedRow, 3).toString();
            String service = tblOrders.getValueAt(selectedRow, 4).toString();
            String weight = tblOrders.getValueAt(selectedRow, 5).toString();
String currentStatus = tblOrders.getValueAt(selectedRow, 6).toString();
String paymentStatus = tblOrders.getValueAt(selectedRow, 7).toString();

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
        } if ("Ready".equals(currentStatus)) {
            jComboBox1.addItem("Ready");
          
            
            if ("Paid".equalsIgnoreCase(paymentStatus)) {
                jComboBox1.addItem("Claimed");
              }
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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