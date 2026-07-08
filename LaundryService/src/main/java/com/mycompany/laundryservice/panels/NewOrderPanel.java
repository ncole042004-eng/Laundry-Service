/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.laundryservice.panels;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.laundryservice.AppConstants;
import com.mycompany.laundryservice.MainJFrame;
import com.mycompany.laundryservice.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Cral
 */
public class NewOrderPanel extends javax.swing.JPanel {

    private final MainJFrame mainFrame;
    private int selectedCustomerId = -1;
    private double servicePrice = 0.0;
    private int selectedServiceId = -1;
    
    // ====== EMPLOYEE TRACKING ======
    private int currentEmployeeId = -1;
    private String currentEmployeeName = "";
    // ====== END ======

    /**
     * Creates new form NewOrderPanel
     *
     * @param mainFrame
     */
    public NewOrderPanel(MainJFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        
        // ====== GET CURRENT EMPLOYEE ======
        try {
            currentEmployeeId = mainFrame.getCurrentEmployeeId();
            if (currentEmployeeId != -1) {
                currentEmployeeName = com.mycompany.laundryservice.database.DBConnection.getUserName(currentEmployeeId);
            }
        } catch (Exception e) {
            currentEmployeeId = 1;
            currentEmployeeName = "Unknown";
        }
        if (currentEmployeeId == -1) {
            currentEmployeeId = 1;
            currentEmployeeName = "System";
        }
        // ====== END ======
        
        initializePanel();
    }

    private void setPreferredSize(int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
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

    private void initializePanel() {
     
        loadServices();
        clearForm();
        setupListeners();
        setupValidation();
        updateTotalAmount();
        btnSaveOrder.setEnabled(false);
        
        // ====== SHOW EMPLOYEE INFO ======
        System.out.println("Current Employee: " + currentEmployeeName + " (ID: " + currentEmployeeId + ")");
        // ====== END ======
        
        // ====== CONFIGURE SPINNER MODELS ======
        spnWeightKg.setModel(new javax.swing.SpinnerNumberModel(0.0, 0.0, 7.0, 0.1));
        spnAdditionalCharges.setModel(new javax.swing.SpinnerNumberModel(0.0, 0.0, 9999.0, 0.1));
        // ====== END ======

        // ====== LEFT ALIGN SPINNER TEXT ======
        Color spinnerBg = new java.awt.Color(231, 228, 228);

        spnWeightKg.setBackground(spinnerBg);
        JComponent editor1 = spnWeightKg.getEditor();
        if (editor1 instanceof javax.swing.JSpinner.DefaultEditor defaultEditor) {
            JTextField tf1 = defaultEditor.getTextField();
            tf1.setHorizontalAlignment(JTextField.LEFT);
            tf1.setBackground(spinnerBg);
            tf1.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        }

        spnAdditionalCharges.setBackground(spinnerBg);
        JComponent editor2 = spnAdditionalCharges.getEditor();
        if (editor2 instanceof javax.swing.JSpinner.DefaultEditor defaultEditor) {
            JTextField tf2 = defaultEditor.getTextField();
            tf2.setHorizontalAlignment(JTextField.LEFT);
            tf2.setBackground(spinnerBg);
            tf2.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        }
        // ====== END ======
                
        // ====== ROUNDED PANEL BORDERS (BLUE) ======
        int arc = 12;
        Color borderColor = new java.awt.Color(38, 85, 189);

        // Padding to prevent clipping
        javax.swing.border.Border padding = javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2);
        javax.swing.border.Border roundedBorder = new RoundedBorder(arc, borderColor, 1);

        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(roundedBorder, padding));
        jPanel4.setBorder(javax.swing.BorderFactory.createCompoundBorder(roundedBorder, padding));
        jPanel5.setBorder(javax.swing.BorderFactory.createCompoundBorder(roundedBorder, padding));

        jPanel2.setBackground(java.awt.Color.WHITE);
        jPanel4.setBackground(java.awt.Color.WHITE);
        jPanel5.setBackground(java.awt.Color.WHITE);
        // ====== END ======

        // ====== APPLY INTER FONTS (MATCHING HTML DESIGN) ======
        // Title
        lblTitle.setFont(new java.awt.Font("Inter 28pt", java.awt.Font.BOLD, 28));
        lblTitle.setForeground(new java.awt.Color(26, 28, 28));

        // Subtitle
        lblSubtitle.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 16));
        lblSubtitle.setForeground(new java.awt.Color(67, 70, 84));

        // Section Headers
        jLabel3.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 18));
        jLabel3.setForeground(new java.awt.Color(26, 28, 28));

        jLabel9.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 18));
        jLabel9.setForeground(new java.awt.Color(26, 28, 28));

        jLabel15.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 24 ));
        jLabel15.setForeground(new java.awt.Color(38, 85, 189));

        jLabel4.setFont(new java.awt.Font("Inter 18pt", java.awt.Font.BOLD, 18));
        jLabel4.setForeground(new java.awt.Color(26, 28, 28));

        // Field Labels
        jLabel5.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        jLabel7.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        jLabel2.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        jLabel10.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        jLabel11.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        jLabel12.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));
        jLabel14.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 14));

        // Small Label - Max 7kg
        jLabel1.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 12));
        jLabel1.setForeground(new java.awt.Color(116, 116, 116));

        // Customer Values
        lblCustomerValue.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 16));
        lblCustomerValue.setForeground(new java.awt.Color(44, 62, 80));

        lblPhoneValue.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 16));
        lblPhoneValue.setForeground(new java.awt.Color(44, 62, 80));

        lblAddressValue.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 16));
        lblAddressValue.setForeground(new java.awt.Color(44, 62, 80));

        // Order Summary
        lblService1.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 16));
        lblServiceAmount.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 16));
        lblService.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 16));
        lblAdditonalChargesAmount.setFont(new java.awt.Font("Inter", java.awt.Font.PLAIN, 16));
        
        // ====== FIX: SERVICE TYPE LABEL FONT STYLING ======
        lblServiceType.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 16));
        lblServiceType.setForeground(new java.awt.Color(44, 62, 80));
        // ===================================================

        // Total Amount - Display Large
        lblTotalAmount.setFont(new java.awt.Font("Inter 28pt", java.awt.Font.BOLD, 36));
        lblTotalAmount.setForeground(new java.awt.Color(38, 85, 189));

        // Buttons
        btnCustomerList.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
        btnManageCustomers.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
        btnSaveOrder.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
        btnCancel.setFont(new java.awt.Font("Inter", java.awt.Font.BOLD, 14));
        // ====== END FONT STYLING ======

        // ====== BUTTON ROUNDED CORNERS ======
        int buttonArc = 8;
        btnCustomerList.putClientProperty("JButton.arc", buttonArc);
        btnCustomerList.putClientProperty("JButton.buttonType", "roundRect");

        btnManageCustomers.putClientProperty("JButton.arc", buttonArc);
        btnManageCustomers.putClientProperty("JButton.buttonType", "roundRect");

        btnSaveOrder.putClientProperty("JButton.arc", buttonArc);
        btnSaveOrder.putClientProperty("JButton.buttonType", "roundRect");

        btnCancel.putClientProperty("JButton.arc", buttonArc);
        btnCancel.putClientProperty("JButton.buttonType", "roundRect");
        // ====== END ======

        // ====== ADD SVG ICONS ======
        // 1. Customer Info Label (jLabel3) - person_search.svg
        jLabel3.setIcon(loadIcon("person_search.svg", 18, 0x2655bd));
        jLabel3.setIconTextGap(8);
        jLabel3.setText(" CUSTOMER INFO");

        // 2. Order Details Label (jLabel9) - density_small.svg
        jLabel9.setIcon(loadIcon("density_small.svg", 18, 0x2655bd));
        jLabel9.setIconTextGap(8);
        jLabel9.setText(" ORDER DETAILS");

        // 3. Save Order Button - save.svg
        btnSaveOrder.setIcon(loadIcon("save.svg", 22, 0xFFFFFF));
        btnSaveOrder.setIconTextGap(8);

        // 4. Manage Customers Button - add.svg
        btnManageCustomers.setIcon(loadIcon("add.svg", 22, 0xFFFFFF));
        btnManageCustomers.setIconTextGap(8);
        
        // Customer List Button - list_alt.svg
        btnCustomerList.setIcon(loadIcon("list.svg", 22, 0xFFFFFF));
        btnCustomerList.setIconTextGap(8);
        
        // Cancel Button - logout.svg
        btnCancel.setIcon(loadIcon("logout.svg", 22, 0x666666));
        btnCancel.setIconTextGap(8);
        // ====== END ADD ICONS ======
        
        // ====== SETUP SPINNER LISTENERS ======
        spnWeightKg.addChangeListener(e -> validateForm());
        spnAdditionalCharges.addChangeListener(e -> calculateTotal());
        // ====== END ======
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

    // ==================== SERVICE LOADING ====================

    private void loadServices() {
        String sql = "SELECT service_id, service_name, fixed_price FROM Services";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            cboService.removeAllItems();
            while (rs.next()) {
                ServiceItem item = new ServiceItem(
                    rs.getInt("service_id"),
                    rs.getString("service_name"),
                    rs.getDouble("fixed_price")
                );
                cboService.addItem(item);
            }

            if (cboService.getItemCount() > 0) {
                cboService.setSelectedIndex(0);
                // ====== FIX: UPDATE SERVICE TYPE LABEL INITIALLY ======
                ServiceItem firstItem = (ServiceItem) cboService.getSelectedItem();
                if (firstItem != null) {
                    lblServiceType.setText(firstItem.serviceName);
                }
                // ======================================================
                updateTotalAmount();
            }

        } catch (SQLException e) {
            showError("Unable to load services. Please check database connection.");
        }
    }

    // Inner class to store service data in combo box
    private class ServiceItem {
        int serviceId;
        String serviceName;
        double price;

        ServiceItem(int serviceId, String serviceName, double price) {
            this.serviceId = serviceId;
            this.serviceName = serviceName;
            this.price = price;
        }

        @Override
        public String toString() {
            return serviceName;
        }
    }

    // ==================== TOTAL AMOUNT CALCULATION ====================

    private void updateTotalAmount() {
        ServiceItem selected = (ServiceItem) cboService.getSelectedItem();
        if (selected != null) {
            servicePrice = selected.price;
            selectedServiceId = selected.serviceId;
            lblServiceAmount.setText(String.format("₱ %.2f", servicePrice));
            
            // ====== FIX: UPDATE SERVICE TYPE LABEL ======
            lblServiceType.setText(selected.serviceName);
            // ============================================
            
            calculateTotal();
        }
    }

    private void calculateTotal() {
        try {
            double additionalCharges = getAdditionalCharges();
            double total = servicePrice + additionalCharges;
            lblAdditonalChargesAmount.setText(String.format("₱ %.2f", additionalCharges));
            lblTotalAmount.setText(String.format("₱ %.2f", total));

        } catch (NumberFormatException e) {
            lblTotalAmount.setText("₱ 0.00");
        }
    }

    // ==================== CUSTOMER SELECTION ====================

    private void openCustomerList() {
        CustomerListDialog dialog = new CustomerListDialog(null, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (dialog.isCustomerSelected()) {
            selectedCustomerId = dialog.getSelectedCustomerId();
            lblCustomerValue.setText(dialog.getSelectedCustomerName());
            lblPhoneValue.setText(dialog.getSelectedPhoneNumber());
            loadCustomerAddress(selectedCustomerId);
            validateForm();
        }
    }

    // ==================== LOAD CUSTOMER ADDRESS ====================

    private void loadCustomerAddress(int customerId) {
        String sql = "SELECT address FROM Customers WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String address = rs.getString("address");
                if (address != null && !address.isEmpty()) {
                    lblAddressValue.setText(address);
                    lblAddressValue.setForeground(new java.awt.Color(44, 62, 80));
                } else {
                    lblAddressValue.setText("No address on file");
                    lblAddressValue.setForeground(new java.awt.Color(127, 140, 141));
                }
            } else {
                lblAddressValue.setText("No address on file");
                lblAddressValue.setForeground(new java.awt.Color(127, 140, 141));
            }
            
        } catch (SQLException e) {
            lblAddressValue.setText("Unable to load address");
            lblAddressValue.setForeground(new java.awt.Color(186, 26, 26));
            e.printStackTrace();
        }
    }

    // ==================== WEIGHT VALIDATION ====================

    private double getWeight() {
        Object value = spnWeightKg.getValue();
        if (value instanceof Number) {
            double weight = ((Number) value).doubleValue();
            if (weight >= 0 && weight <= 7) {
                return weight;
            }
        }
        return -1;
    }

    private boolean validateWeight(double weight) {
        if (weight == -1) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid weight between 0 and 7kg.",
                "Input Required",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (weight > 7.0) {
            JOptionPane.showMessageDialog(this,
                "Weight exceeds maximum capacity of 7kg",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (weight <= 0) {
            JOptionPane.showMessageDialog(this,
                "Weight must be greater than 0.",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    // ==================== CLAIM NUMBER GENERATION ====================

    private String generateClaimNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String datePrefix = formatter.format(LocalDate.now());

        String sql = "SELECT MAX(CAST(SUBSTRING(claim_number, -3) AS UNSIGNED)) as max_num "
                + "FROM Orders "
                + "WHERE claim_number LIKE 'LS-" + datePrefix + "-%'";

        int nextNumber = 1;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int maxNum = rs.getInt("max_num");
                if (!rs.wasNull()) {
                    nextNumber = maxNum + 1;
                }
            }
        } catch (SQLException e) {
            // If error occurs, default to 1
        }

        return String.format("LS-%s-%03d", datePrefix, nextNumber);
    }

    // ==================== SAVE ORDER ====================

    private void saveOrder() {
        // Validate weight
        double weight = getWeight();
        if (!validateWeight(weight)) {
            return;
        }

        // Check if customer is selected
        if (selectedCustomerId == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a customer first.",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Check service selection
        if (selectedServiceId == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a service.",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Generate claim number
        String claimNumber = generateClaimNumber();

        // Calculate total
        double total = servicePrice + getAdditionalCharges();

        // Get notes
        String notes = txtNotes.getText().trim();

        // FIXED: Include price_at_order in INSERT
        String sql = "INSERT INTO Orders (claim_number, customer_id, employee_id, service_id, " +
                     "weight_kg, price_at_order, total_amount, payment_status, order_status, notes) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, 'Unpaid', 'Pending', ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, claimNumber);
            pstmt.setInt(2, selectedCustomerId);
            pstmt.setInt(3, currentEmployeeId);  // Use the captured employee ID
            pstmt.setInt(4, selectedServiceId);
            pstmt.setDouble(5, weight);
            pstmt.setDouble(6, servicePrice);
            pstmt.setDouble(7, total);
            pstmt.setString(8, notes);

            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                JOptionPane.showMessageDialog(this,
                    "Order saved successfully!\n\nClaim Number: " + claimNumber
                    + "\nCustomer: " + lblCustomerValue.getText()
                    + "\nEmployee: " + currentEmployeeName
                    + "\nTotal: ₱ " + String.format("%.2f", total),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            }

        } catch (SQLException e) {
            showError("Unable to save order. Please check database connection.");
            e.printStackTrace();
        }
    }

    private double getAdditionalCharges() {
        Object value = spnAdditionalCharges.getValue();
        if (value instanceof Number) {
            double charges = ((Number) value).doubleValue();
            return charges < 0 ? 0 : charges;
        }
        return 0.0;
    }

    // ==================== FORM VALIDATION ====================

    private void validateForm() {
        boolean isValid = true;

        // Check if customer is selected
        if (selectedCustomerId == -1) {
            isValid = false;
        }

        // Check weight
        double weight = getWeight();
        if (weight == -1 || weight <= 0 || weight > 7.0) {
            isValid = false;
        }

        btnSaveOrder.setEnabled(isValid);
    }

    // ==================== FORM CLEAR ====================

    private void clearForm() {
        selectedCustomerId = -1;
        selectedServiceId = -1;
        lblCustomerValue.setText("");
        lblPhoneValue.setText("");
        lblAddressValue.setText("");
        spnWeightKg.setValue(0);
        txtNotes.setText("");
        spnAdditionalCharges.setValue(0);
        lblServiceAmount.setText("₱ 0.00");
        lblAdditonalChargesAmount.setText("₱ 0.00");
        lblTotalAmount.setText("₱ 0.00");
        // ====== FIX: CLEAR SERVICE TYPE LABEL ======
        lblServiceType.setText("");
        // ===========================================
        btnSaveOrder.setEnabled(false);

        if (cboService.getItemCount() > 0) {
            cboService.setSelectedIndex(0);
        }
    }

    // ==================== ERROR HANDLING ====================

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ==================== REFRESH DATA (Required by MainJFrame) ====================

    public void refreshData() {
        loadServices();
        clearForm();
        // ====== REFRESH EMPLOYEE INFO ======
        try {
            currentEmployeeId = mainFrame.getCurrentEmployeeId();
            if (currentEmployeeId != -1) {
                currentEmployeeName = com.mycompany.laundryservice.database.DBConnection.getUserName(currentEmployeeId);
            }
        } catch (Exception e) {
            currentEmployeeId = 1;
            currentEmployeeName = "Unknown";
        }
        if (currentEmployeeId == -1) {
            currentEmployeeId = 1;
            currentEmployeeName = "System";
        }
        // ====== END ======
    }

    // ==================== SETUP LISTENERS ====================

    private void setupListeners() {
        // Weight spinner - validate on change
        spnWeightKg.addChangeListener(e -> validateForm());
        
        // Additional charges spinner - update total on change
        spnAdditionalCharges.addChangeListener(e -> calculateTotal());
    }

    private void setupValidation() {
        // Additional validation can be added here
    }

    // ==================== MAIN METHOD ====================

    public static void main(String[] args) {
        FlatLightLaf.setup();

        JFrame frame = new JFrame();
        frame.add(new NewOrderPanel(null));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jDialog1 = new javax.swing.JDialog();
        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnCustomerList = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblCustomerValue = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblPhoneValue = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblAddressValue = new javax.swing.JLabel();
        btnManageCustomers = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lblTotalAmount = new javax.swing.JLabel();
        btnSaveOrder = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblService = new javax.swing.JLabel();
        lblService1 = new javax.swing.JLabel();
        lblAdditonalChargesAmount = new javax.swing.JLabel();
        lblServiceAmount = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblServiceType = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cboService = new javax.swing.JComboBox<>();
        txtNotes = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        spnWeightKg = new javax.swing.JSpinner();
        spnAdditionalCharges = new javax.swing.JSpinner();

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

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        lblTitle.setBackground(new java.awt.Color(44, 62, 80));
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTitle.setText("Create New Order");

        lblSubtitle.setBackground(new java.awt.Color(127, 140, 141));
        lblSubtitle.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblSubtitle.setText("Complete the details below to process the Laundry Order");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("CUSTOMER INFO");

        btnCustomerList.setBackground(new java.awt.Color(51, 51, 255));
        btnCustomerList.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCustomerList.setForeground(new java.awt.Color(255, 255, 255));
        btnCustomerList.setText("Customer List");
        btnCustomerList.addActionListener(this::btnCustomerListActionPerformed);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("CUSTOMER DETAILS");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("CUSTOMER NAME");

        lblCustomerValue.setBackground(new java.awt.Color(231, 228, 228));
        lblCustomerValue.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblCustomerValue.setText("NAME");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("PHONE NUMBER");

        lblPhoneValue.setBackground(new java.awt.Color(231, 228, 228));
        lblPhoneValue.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblPhoneValue.setText("NUMBER");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("ADDRESS");

        lblAddressValue.setBackground(new java.awt.Color(231, 228, 228));
        lblAddressValue.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblAddressValue.setText("ADDRESS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCustomerValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPhoneValue, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAddressValue, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomerValue, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPhoneValue, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddressValue, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );

        btnManageCustomers.setBackground(new java.awt.Color(52, 152, 219));
        btnManageCustomers.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnManageCustomers.setForeground(new java.awt.Color(255, 255, 255));
        btnManageCustomers.setText("Add New Customer");
        btnManageCustomers.addActionListener(this::btnManageCustomersActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCustomerList, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97)
                        .addComponent(btnManageCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCustomerList, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnManageCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );

        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setText("TOTAL ");

        lblTotalAmount.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        lblTotalAmount.setText("₱ 0.00");

        btnSaveOrder.setBackground(new java.awt.Color(52, 152, 219));
        btnSaveOrder.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnSaveOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveOrder.setText("Save Order");
        btnSaveOrder.addActionListener(this::btnSaveOrderActionPerformed);

        btnCancel.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        lblService.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblService.setText("Additional Charges");

        lblService1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblService1.setText("Service");

        lblAdditonalChargesAmount.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblAdditonalChargesAmount.setText("0.00");

        lblServiceAmount.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblServiceAmount.setText("0.00");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 255));
        jLabel15.setText("Order Summary");

        lblServiceType.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblServiceType.setText("Service Type");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblService1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                                .addComponent(lblTotalAmount)
                                .addGap(17, 17, 17))
                            .addComponent(btnSaveOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(lblService, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(lblServiceType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(63, 63, 63)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblAdditonalChargesAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                    .addComponent(lblServiceAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(27, 27, 27))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblService1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServiceAmount)
                    .addComponent(lblServiceType))
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblService, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAdditonalChargesAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(btnSaveOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setText("ORDER DETAILS"); // NOI18N
        jLabel9.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("SERVICE");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("WEIGHT (kg)");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("ADDITIONAL CHARGES (₱)");

        cboService.setBackground(new java.awt.Color(231, 228, 228));
        cboService.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboService.setForeground(new java.awt.Color(44, 62, 80));
        cboService.addActionListener(this::cboServiceActionPerformed);

        txtNotes.setBackground(new java.awt.Color(231, 228, 228));
        txtNotes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNotes.setForeground(new java.awt.Color(44, 62, 80));
        txtNotes.addActionListener(this::txtNotesActionPerformed);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("NOTES");

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Max 7 kg per load");

        spnWeightKg.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        spnWeightKg.setName(""); // NOI18N

        spnAdditionalCharges.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        spnAdditionalCharges.setToolTipText("");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(spnAdditionalCharges)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNotes)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboService, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1))
                            .addComponent(spnWeightKg))))
                .addGap(66, 66, 66))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboService)
                    .addComponent(spnWeightKg))
                .addGap(28, 28, 28)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNotes, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spnAdditionalCharges)
                .addGap(22, 22, 22))
        );

        spnAdditionalCharges.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSubtitle))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(41, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSubtitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCustomerListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerListActionPerformed
        // TODO add your handling code here:
         openCustomerList();
    }//GEN-LAST:event_btnCustomerListActionPerformed

    private void cboServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboServiceActionPerformed
        // TODO add your handling code here:
        updateTotalAmount();
    }//GEN-LAST:event_cboServiceActionPerformed

    private void txtNotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNotesActionPerformed

    private void btnManageCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageCustomersActionPerformed
        // TODO add your handling code here:
        mainFrame.showCard(AppConstants.CARD_CUSTOMERS);
    }//GEN-LAST:event_btnManageCustomersActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        clearForm();
        mainFrame.showCard("orderListPanel1");
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveOrderActionPerformed
        // TODO add your handling code here:
        saveOrder();
    }//GEN-LAST:event_btnSaveOrderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCustomerList;
    private javax.swing.JButton btnManageCustomers;
    private javax.swing.JButton btnSaveOrder;
    private javax.swing.JComboBox<ServiceItem> cboService;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblAdditonalChargesAmount;
    private javax.swing.JLabel lblAddressValue;
    private javax.swing.JLabel lblCustomerValue;
    private javax.swing.JLabel lblPhoneValue;
    private javax.swing.JLabel lblService;
    private javax.swing.JLabel lblService1;
    private javax.swing.JLabel lblServiceAmount;
    private javax.swing.JLabel lblServiceType;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotalAmount;
    private javax.swing.JSpinner spnAdditionalCharges;
    private javax.swing.JSpinner spnWeightKg;
    private javax.swing.JTextField txtNotes;
    // End of variables declaration//GEN-END:variables
}
