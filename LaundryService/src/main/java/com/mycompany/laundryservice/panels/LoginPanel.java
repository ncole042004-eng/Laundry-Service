package com.mycompany.laundryservice.panels;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.laundryservice.MainJFrame;
import java.awt.Color;
import java.awt.Font;

public class LoginPanel extends javax.swing.JPanel {

	private MainJFrame mainFrame;

	/**
	 * Creates new form LoginPanel
	 *
	 * @param mainFrame
	 */
	public LoginPanel(MainJFrame mainFrame) {
    this.mainFrame = mainFrame;
    initComponents();

    // Fonts
    LAUNDRYSERVICEPOS.setFont(new Font("Playfair Display", Font.BOLD, 30));
    employeelogin.setFont(new Font("Inter", Font.PLAIN, 16));
    jLabel1.setFont(new Font("Inter", Font.PLAIN, 14));

    txtUsername.setFont(new Font("Inter", Font.PLAIN, 16));
    txtPassword.setFont(new Font("Inter", Font.PLAIN, 16));

    btnLogin.setFont(new Font("Inter", Font.BOLD, 16));

    // Placeholders
    txtUsername.putClientProperty("JTextField.placeholderText", "Username");
    txtPassword.putClientProperty("JTextField.placeholderText", "********");

    txtUsername.setText("Username");

    txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (txtUsername.getText().equals("Username")) {
                txtUsername.setText("");
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {
            if (txtUsername.getText().isEmpty()) {
                txtUsername.setText("Username");
            }
        }
    });

    txtPassword.setText("Password");
    txtPassword.setEchoChar((char) 0);

    txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (String.valueOf(txtPassword.getPassword()).equals("Password")) {
                txtPassword.setText("");
                txtPassword.setEchoChar('•');
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {
            if (txtPassword.getPassword().length == 0) {
                txtPassword.setEchoChar((char) 0);
                txtPassword.setText("Password");
            }
        }
    });

    // FlatLaf Button
    btnLogin.putClientProperty("JButton.buttonType", "roundRect");
    btnLogin.setBackground(new Color(66, 133, 244));
    btnLogin.setForeground(Color.WHITE);
    btnLogin.setFocusPainted(false);
    btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            btnLogin.setBackground(new Color(90, 155, 255));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            btnLogin.setBackground(new Color(66, 133, 244));
        }
    });

    // Transparent text fields
    txtUsername.setOpaque(false);
    txtUsername.setBackground(new Color(0, 0, 0, 0));
    txtUsername.setBorder(null);

    txtPassword.setOpaque(false);
    txtPassword.setBackground(new Color(0, 0, 0, 0));
    txtPassword.setBorder(null);

    // Press Enter to Login
    txtUsername.addActionListener(e -> btnLogin.doClick());
    txtPassword.addActionListener(e -> btnLogin.doClick());

    setLayout(new java.awt.GridLayout(1, 2));

    add(pnlLeft);
    add(jPanel2);

    pnlLeft.setLayout(new java.awt.BorderLayout());
    pnlLeft.add(new ImagePanel("/loginImage.png"), java.awt.BorderLayout.CENTER);

    pnlLogo.setLayout(new java.awt.BorderLayout());
    pnlLogo.add(new ImagePanel("/iconLogo.png", new java.awt.Dimension(110, 110)), java.awt.BorderLayout.CENTER);

    javax.swing.GroupLayout jPanel2LayoutV =
            (javax.swing.GroupLayout) jPanel2.getLayout();

    jPanel2LayoutV.setVerticalGroup(
        jPanel2LayoutV.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(12)
            .addComponent(LAUNDRYSERVICEPOS)
            .addGap(6)
            .addComponent(jLabel1)
            .addGap(28)
            .addComponent(employeelogin)
            .addGap(40)
            .addComponent(txtUsername,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 48,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(4)
            .addComponent(jSeparator1,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 16,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(16)
            .addComponent(txtPassword,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 48,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(4)
            .addComponent(jSeparator2,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 16,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(20)
            .addComponent(btnLogin,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 56,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout jPanel2Layout =
            (javax.swing.GroupLayout) jPanel2.getLayout();

    javax.swing.GroupLayout.ParallelGroup contentGroup =
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(pnlLogo,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(LAUNDRYSERVICEPOS)
                .addComponent(jLabel1)
                .addComponent(employeelogin)
                .addComponent(txtUsername,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 420,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jSeparator1,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 420,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtPassword,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 420,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jSeparator2,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 420,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnLogin,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 420,
                        javax.swing.GroupLayout.PREFERRED_SIZE);

    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(contentGroup)
            .addGap(0, 0, Short.MAX_VALUE)
    );
}

	public void refreshData() {

		txtUsername.setText("Username");
		txtPassword.setEchoChar((char) 0);
		txtPassword.setText("Password");
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLeft = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        LAUNDRYSERVICEPOS = new javax.swing.JLabel();
        employeelogin = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        pnlLogo = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(800, 420));
        setLayout(new java.awt.GridLayout(1, 0));

        pnlLeft.setBackground(new java.awt.Color(249, 249, 249));
        pnlLeft.setPreferredSize(new java.awt.Dimension(620, 740));

        javax.swing.GroupLayout pnlLeftLayout = new javax.swing.GroupLayout(pnlLeft);
        pnlLeft.setLayout(pnlLeftLayout);
        pnlLeftLayout.setHorizontalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlLeftLayout.setVerticalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        add(pnlLeft);

        jPanel2.setBackground(new java.awt.Color(249, 249, 249));
        jPanel2.setForeground(new java.awt.Color(51, 0, 204));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(620, 740));

        LAUNDRYSERVICEPOS.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LAUNDRYSERVICEPOS.setForeground(new java.awt.Color(0, 0, 51));
        LAUNDRYSERVICEPOS.setText("LAUNDRY SERVICE ");

        employeelogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        employeelogin.setText("Enter your login information to continue");

        txtUsername.setBackground(new java.awt.Color(249, 249, 249));
        txtUsername.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        txtUsername.addActionListener(this::txtUsernameActionPerformed);

        txtPassword.setBackground(new java.awt.Color(249, 249, 249));
        txtPassword.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(this::btnLoginActionPerformed);

        jLabel1.setText("Ligao City, Albay");

        jSeparator1.setForeground(new java.awt.Color(51, 0, 204));

        jSeparator2.setForeground(new java.awt.Color(51, 0, 204));

        javax.swing.GroupLayout pnlLogoLayout = new javax.swing.GroupLayout(pnlLogo);
        pnlLogo.setLayout(pnlLogoLayout);
        pnlLogoLayout.setHorizontalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );
        pnlLogoLayout.setVerticalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(95, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LAUNDRYSERVICEPOS)
                    .addComponent(employeelogin)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(LAUNDRYSERVICEPOS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(employeelogin)
                .addGap(52, 52, 52)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
	    // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
	    String username = txtUsername.getText().trim();
	    String password = new String(txtPassword.getPassword());

	    if (username.equals("Username") || password.equals("Password")) {
		    javax.swing.JOptionPane.showMessageDialog(this, "Please enter your username and password.");
		    return;
	    }

	    int employeeId = com.mycompany.laundryservice.database.DBConnection.authenticateEmployee(username, password);

	    if (employeeId != -1) {
		    mainFrame.onLoginSuccess(employeeId);
	    } else {
		    javax.swing.JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", javax.swing.JOptionPane.ERROR_MESSAGE);
	    }
    }//GEN-LAST:event_btnLoginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LAUNDRYSERVICEPOS;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel employeelogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
