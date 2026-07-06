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

/**
 *
 * @author Cral
 */
public class NewOrderPanel extends javax.swing.JPanel {

    private final MainJFrame mainFrame;
    private int selectedCustomerId = -1;
    private double servicePrice = 0.0;
    private int selectedServiceId = -1;

    /**
     * Creates new form NewOrderPanel
     *
     * @param mainFrame
     */
    public NewOrderPanel(MainJFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        initializePanel();
    }

    private void initializePanel() {
        loadServices();
        clearForm();
        setupListeners();
        setupValidation();
        updateTotalAmount();
        btnSaveOrder.setEnabled(false);
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
            calculateTotal();
        }
    }

    private void calculateTotal() {
        try {
            double additionalCharges = 0.0;
            String chargesText = txtAdditionalCharges.getText().trim();
            if (!chargesText.isEmpty()) {
                additionalCharges = Double.parseDouble(chargesText);
                if (additionalCharges < 0) {
                    additionalCharges = 0;
                    txtAdditionalCharges.setText("0.00");
                }
            }

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
            validateForm();
        }
    }

    // ==================== WEIGHT VALIDATION ====================

    private double getWeight() {
        try {
            String weightText = txtWeightKg.getText().trim();
            if (weightText.isEmpty()) {
                return -1;
            }
            double weight = Double.parseDouble(weightText);
            if (weight < 0) {
                return -1;
            }
            return weight;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private boolean validateWeight(double weight) {
        if (weight == -1) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid weight.",
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

        String sql = "SELECT MAX(CAST(SUBSTRING(claim_number, 10, 3) AS UNSIGNED)) as max_num "
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

        // ====== EMPLOYEE ID HANDLING ======
        int employeeId;
        try {
            employeeId = mainFrame.getCurrentEmployeeId();
        } catch (Exception e) {
            employeeId = 1; // Default employee ID for testing
        }
        if (employeeId == -1) {
            employeeId = 1; // Default employee ID for testing
        }
        // ====== END EMPLOYEE ID HANDLING ======

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
            pstmt.setInt(3, employeeId);
            pstmt.setInt(4, selectedServiceId);
            pstmt.setDouble(5, weight);
            pstmt.setDouble(6, servicePrice);      // price_at_order
            pstmt.setDouble(7, total);             // total_amount
            pstmt.setString(8, notes);

            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                JOptionPane.showMessageDialog(this,
                    " Order saved successfully!\n\nClaim Number: " + claimNumber
                    + "\nCustomer: " + lblCustomerValue.getText()
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
        try {
            String chargesText = txtAdditionalCharges.getText().trim();
            if (chargesText.isEmpty()) {
                return 0.0;
            }
            double charges = Double.parseDouble(chargesText);
            return charges < 0 ? 0 : charges;
        } catch (NumberFormatException e) {
            return 0.0;
        }
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
        txtWeightKg.setText("");
        txtNotes.setText("");
        txtAdditionalCharges.setText("");
        lblServiceAmount.setText("₱ 0.00");
        lblAdditonalChargesAmount.setText("₱ 0.00");
        lblTotalAmount.setText("₱ 0.00");
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
    }

    // ==================== SETUP LISTENERS ====================

    private void setupListeners() {
        // Weight field validation on each keystroke
        txtWeightKg.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateForm();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateForm();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateForm();
            }
        });

        // Additional charges - update total on each keystroke
        txtAdditionalCharges.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateTotal();
            }
        });
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
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lblTotalAmount = new javax.swing.JLabel();
        btnSaveOrder = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblCustomerValue1 = new javax.swing.JLabel();
        lblService = new javax.swing.JLabel();
        lblService1 = new javax.swing.JLabel();
        lblAdditonalChargesAmount = new javax.swing.JLabel();
        lblServiceAmount = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cboService = new javax.swing.JComboBox<>();
        txtWeightKg = new javax.swing.JTextField();
        txtNotes = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtAdditionalCharges = new javax.swing.JTextField();
        btnManageCustomers = new javax.swing.JButton();

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

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("CUSTOMER INFO");

        btnCustomerList.setBackground(new java.awt.Color(52, 152, 219));
        btnCustomerList.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCustomerList.setForeground(new java.awt.Color(255, 255, 255));
        btnCustomerList.setText("Customer List");
        btnCustomerList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnCustomerList.addActionListener(this::btnCustomerListActionPerformed);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("CUSTOMER DETAILS");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("CUSTOMER NAME");

        lblCustomerValue.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("PHONE NUMBER");

        lblPhoneValue.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCustomerValue, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPhoneValue, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomerValue)
                    .addComponent(lblPhoneValue))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerList, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCustomerList, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setText("TOTAL AMOUNT");

        lblTotalAmount.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTotalAmount.setText("0000.00");

        btnSaveOrder.setBackground(new java.awt.Color(52, 152, 219));
        btnSaveOrder.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnSaveOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveOrder.setText("Save Order");
        btnSaveOrder.addActionListener(this::btnSaveOrderActionPerformed);

        btnCancel.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        lblCustomerValue1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblService.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblService.setText("Additional Charges");

        lblService1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblService1.setText("Service");

        lblAdditonalChargesAmount.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblAdditonalChargesAmount.setText("0.00");

        lblServiceAmount.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblServiceAmount.setText("0.00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(btnSaveOrder))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(lblCustomerValue1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblService1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(88, 88, 88)))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(lblService, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblServiceAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAdditonalChargesAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(lblCustomerValue1)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblService1)
                    .addComponent(lblServiceAmount))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblService)
                    .addComponent(lblAdditonalChargesAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setText("ORDER DETAILS"); // NOI18N
        jLabel9.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("SERVICE");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("WEIGHT (kg)");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("ADDITIONAL CHARGES");

        cboService.setBackground(new java.awt.Color(231, 228, 228));
        cboService.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboService.setForeground(new java.awt.Color(44, 62, 80));
        cboService.addActionListener(this::cboServiceActionPerformed);

        txtWeightKg.setBackground(new java.awt.Color(231, 228, 228));
        txtWeightKg.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtWeightKg.setForeground(new java.awt.Color(44, 62, 80));
        txtWeightKg.addActionListener(this::txtWeightKgActionPerformed);

        txtNotes.setBackground(new java.awt.Color(231, 228, 228));
        txtNotes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNotes.setForeground(new java.awt.Color(44, 62, 80));
        txtNotes.addActionListener(this::txtNotesActionPerformed);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("NOTES");

        txtAdditionalCharges.setBackground(new java.awt.Color(231, 228, 228));
        txtAdditionalCharges.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtAdditionalCharges.addActionListener(this::txtAdditionalChargesActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(200, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(cboService, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(txtWeightKg, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAdditionalCharges, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNotes))
                        .addGap(66, 66, 66))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboService, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWeightKg))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNotes, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAdditionalCharges, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        btnManageCustomers.setBackground(new java.awt.Color(52, 152, 219));
        btnManageCustomers.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnManageCustomers.setForeground(new java.awt.Color(255, 255, 255));
        btnManageCustomers.setText("Add New Customer");
        btnManageCustomers.addActionListener(this::btnManageCustomersActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSubtitle, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnManageCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnManageCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSubtitle)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
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

    private void txtWeightKgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWeightKgActionPerformed
        // TODO add your handling code here:
          validateForm();
    }//GEN-LAST:event_txtWeightKgActionPerformed

    private void txtNotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNotesActionPerformed

    private void btnSaveOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveOrderActionPerformed
        // TODO add your handling code here:
          saveOrder();
    }//GEN-LAST:event_btnSaveOrderActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        clearForm();
        mainFrame.showCard("orderListPanel1");
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtAdditionalChargesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdditionalChargesActionPerformed
        // TODO add your handling code here:
        calculateTotal();
    }//GEN-LAST:event_txtAdditionalChargesActionPerformed

    private void btnManageCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageCustomersActionPerformed
        // TODO add your handling code here:
        mainFrame.showCard(AppConstants.CARD_CUSTOMERS);
    }//GEN-LAST:event_btnManageCustomersActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCustomerList;
    private javax.swing.JButton btnManageCustomers;
    private javax.swing.JButton btnSaveOrder;
    private javax.swing.JComboBox<ServiceItem> cboService;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JLabel lblCustomerValue;
    private javax.swing.JLabel lblCustomerValue1;
    private javax.swing.JLabel lblPhoneValue;
    private javax.swing.JLabel lblService;
    private javax.swing.JLabel lblService1;
    private javax.swing.JLabel lblServiceAmount;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotalAmount;
    private javax.swing.JTextField txtAdditionalCharges;
    private javax.swing.JTextField txtNotes;
    private javax.swing.JTextField txtWeightKg;
    // End of variables declaration//GEN-END:variables
}
