package com.mycompany.laundryservice.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.mycompany.laundryservice.MainJFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LoginPanel extends javax.swing.JPanel {

    private final MainJFrame mainFrame;
    
private FlatSVGIcon createDarkIcon(String path, int width, int height) {
    FlatSVGIcon icon = new FlatSVGIcon(path, width, height);
    icon.setColorFilter(new FlatSVGIcon.ColorFilter(color -> new Color(55, 65, 81)));
    return icon;
}
    public LoginPanel(MainJFrame mainFrame) {

        this.mainFrame = mainFrame;

        initComponents();
pnlLogo.setOpaque(false);
        initializePanels();
        
        initializeDesign();

        initializePlaceholders();

        initializeButton();

        initializeLogo();
 
        txtUsername.addActionListener(e -> txtPassword.requestFocusInWindow());

        txtPassword.addActionListener(e -> btnLogin.doClick());
        txtUsername.addActionListener(e -> {
    txtPassword.requestFocusInWindow();
    txtPassword.selectAll();
});
    }

    private void initializeDesign() {

    // Caption labels — color + correct weight
lblLoginText.setForeground(new Color(0x1a, 0x1c, 0x1c));
lblLoginText.setFont(new Font("Inter 18pt Medium", Font.PLAIN, 14));

lblPassword.setForeground(new Color(0x1a, 0x1c, 0x1c));
lblPassword.setFont(new Font("Inter 18pt Medium", Font.PLAIN, 14));

// Subtitle
employeelogin.setForeground(new Color(0x43, 0x46, 0x54));
employeelogin.setFont(new Font("Inter 18pt", Font.PLAIN, 14));

// Field borders — replace the old gray with the mockup's #c3c6d7
txtUsername.setBorder(new javax.swing.border.LineBorder(new Color(0xc3, 0xc6, 0xd7), 1, true));
txtPassword.setBorder(new javax.swing.border.LineBorder(new Color(0xc3, 0xc6, 0xd7), 1, true));
LAUNDRYSERVICEPOS.setText("Laundry Service");
    


    //-------------------------
    // Fonts
    //-------------------------

    LAUNDRYSERVICEPOS.setFont(
            new Font("Playfair Display", Font.BOLD, 36));

    lblLocation.setFont(
            new Font("Inter 18pt", Font.PLAIN, 14));

    lblLoginText.setFont(
            new Font("Inter 18pt", Font.PLAIN, 15));

    lblPassword.setFont(
            new Font("Inter 18pt", Font.BOLD, 14));

    txtUsername.setFont(
            new Font("Inter 18pt", Font.PLAIN, 15));

    txtPassword.setFont(
            new Font("Inter 18pt", Font.PLAIN, 15));

    btnLogin.setFont(
            new Font("Inter 18pt", Font.BOLD, 15));


    //-------------------------
    // Colors
    //-------------------------

    LAUNDRYSERVICEPOS.setForeground(
            new Color(0, 24, 73));


    //-------------------------
    // FlatLaf Icons
    //-------------------------

 txtUsername.putClientProperty(
        "JTextField.leadingIcon",
        createDarkIcon("icons/account_circle.svg", 20, 20));

txtPassword.putClientProperty(
        "JTextField.leadingIcon",
        createDarkIcon("icons/lock.svg", 20, 20));

lblLocation.setIcon(
        createDarkIcon("icons/location_on.svg", 16, 16));

    //-------------------------
    // Placeholders
    //-------------------------

    txtUsername.putClientProperty(
            "JTextField.placeholderText",
            "Username");

    txtPassword.putClientProperty(
            "JTextField.placeholderText",
            "Password");


    //-------------------------
    // Button
    //-------------------------

    btnLogin.putClientProperty(
            "JButton.buttonType",
            "roundRect");

    btnLogin.setCursor(
            Cursor.getPredefinedCursor(
                    Cursor.HAND_CURSOR));


    //-------------------------
    // Location Icon
    //-------------------------

    FlatSVGIcon locationIcon = new FlatSVGIcon("icons/location_on.svg", 16, 16);
    locationIcon.setColorFilter(new FlatSVGIcon.ColorFilter(c -> new Color(0x73, 0x76, 0x86)));
    lblLocation.setIcon(locationIcon);


    //-------------------------
    // Status
    //-------------------------

    updateSystemStatus();
}

private void updateSystemStatus() {
    boolean isOperational = com.mycompany.laundryservice.database.DBConnection.canConnectToDB();
    if (isOperational) {
        lblStatus.setText("<html><span style='color:#22c55e;'>●</span> SYSTEM OPERATIONAL</html>");
    } else {
        lblStatus.setText("<html><span style='color:#ba1a1a;'>●</span> SYSTEM OFFLINE</html>");
    }
}

    private void initializeLogo() {

        pnlLogo.setLayout(new BorderLayout());

        pnlLogo.add(
                new ImagePanel(
                        "/iconLogo.png",
                        new Dimension(110,110)),
                BorderLayout.CENTER);

        pnlLeft.setLayout(new BorderLayout());
        pnlLeft.add(
            new ImagePanel("/loginImage.png"),
            BorderLayout.CENTER);


            }

    private void initializeButton() {

        btnLogin.setBackground(
                new Color(38,85,189));

        btnLogin.setForeground(Color.WHITE);

        btnLogin.setFocusPainted(false);

        btnLogin.addMouseListener(
                new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(
                    java.awt.event.MouseEvent e) {

                btnLogin.setBackground(
                        new Color(60,110,220));

            }

            @Override
            public void mouseExited(
                    java.awt.event.MouseEvent e) {

                btnLogin.setBackground(
                        new Color(38,85,189));

            }

        });

    }
            private void initializePanels() {

    ImagePanel background = new ImagePanel("/righsideImage.png");
background.setOverlayColor(new Color(255, 255, 255, 80));
background.setLayout(new BorderLayout());
    
    RoundedPanel roundedCard = new RoundedPanel(30, Color.WHITE);
    roundedCard.setLayout(new BorderLayout());
    roundedCard.add(pnlCard, BorderLayout.CENTER);
    pnlCard.setOpaque(false);

    pnlBadge.setOpaque(true); // Fix 6

    JPanel centerWrapper = new JPanel(new java.awt.GridBagLayout());
    centerWrapper.setOpaque(false);
    centerWrapper.add(roundedCard);

    JPanel badgeWrapper = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 16, 16));
    badgeWrapper.setOpaque(false);
    badgeWrapper.add(pnlBadge);

    background.add(badgeWrapper, BorderLayout.NORTH);
    background.add(centerWrapper, BorderLayout.CENTER);

    pnlRight.removeAll();
    pnlRight.setLayout(new BorderLayout());
    pnlRight.add(background, BorderLayout.CENTER);
    pnlRight.revalidate();
    pnlRight.repaint();
}
    private void initializePlaceholders() {

        txtUsername.setText("");

        txtPassword.setText("");

    }

    public void refreshData() {

        txtUsername.setText("");
        txtPassword.setText("");
        txtPassword.setEchoChar('•');
        updateSystemStatus();
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLeft = new javax.swing.JPanel();
        pnlRight = new javax.swing.JPanel();
        pnlBG = new javax.swing.JPanel();
        pnlBadge = new RoundedPanel(20, new Color(255, 255, 255, 180));
        lblStatus = new javax.swing.JLabel();
        pnlCard = new javax.swing.JPanel();
        pnlLogo = new javax.swing.JPanel();
        LAUNDRYSERVICEPOS = new javax.swing.JLabel();
        employeelogin = new javax.swing.JLabel();
        lblLocation = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        lblLoginText = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();

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
            .addGap(0, 1038, Short.MAX_VALUE)
        );

        add(pnlLeft);

        pnlRight.setBackground(new java.awt.Color(214, 228, 249));
        pnlRight.setForeground(new java.awt.Color(51, 0, 204));
        pnlRight.setOpaque(false);
        pnlRight.setPreferredSize(new java.awt.Dimension(620, 740));

        pnlBG.setPreferredSize(new java.awt.Dimension(620, 740));

        pnlBadge.setBackground(new java.awt.Color(243, 243, 244));
        pnlBadge.setName(""); // NOI18N
        pnlBadge.setPreferredSize(new java.awt.Dimension(175, 30));

        lblStatus.setText("● SYSTEM OPERATIONAL");

        javax.swing.GroupLayout pnlBadgeLayout = new javax.swing.GroupLayout(pnlBadge);
        pnlBadge.setLayout(pnlBadgeLayout);
        pnlBadgeLayout.setHorizontalGroup(
            pnlBadgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBadgeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStatus)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        pnlBadgeLayout.setVerticalGroup(
            pnlBadgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBadgeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 30, 30, 30));
        pnlCard.setPreferredSize(new java.awt.Dimension(430, 540));

        pnlLogo.setPreferredSize(new java.awt.Dimension(110, 110));

        javax.swing.GroupLayout pnlLogoLayout = new javax.swing.GroupLayout(pnlLogo);
        pnlLogo.setLayout(pnlLogoLayout);
        pnlLogoLayout.setHorizontalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );
        pnlLogoLayout.setVerticalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        LAUNDRYSERVICEPOS.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LAUNDRYSERVICEPOS.setForeground(new java.awt.Color(0, 0, 51));
        LAUNDRYSERVICEPOS.setText("LAUNDRY SERVICE ");

        employeelogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        employeelogin.setText("Enter your login information to continue");

        lblLocation.setText("Ligao City, Albay");

        txtUsername.setBackground(new java.awt.Color(249, 249, 249));
        txtUsername.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        txtUsername.addActionListener(this::txtUsernameActionPerformed);

        txtPassword.setBackground(new java.awt.Color(249, 249, 249));
        txtPassword.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        lblLoginText.setText("Username");

        lblPassword.setText("Password");

        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(this::btnLoginActionPerformed);

        javax.swing.GroupLayout pnlCardLayout = new javax.swing.GroupLayout(pnlCard);
        pnlCard.setLayout(pnlCardLayout);
        pnlCardLayout.setHorizontalGroup(
            pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(lblLocation)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(LAUNDRYSERVICEPOS)
                        .addComponent(employeelogin))
                    .addGroup(pnlCardLayout.createSequentialGroup()
                        .addGroup(pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLoginText)
                            .addComponent(lblPassword))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        pnlCardLayout.setVerticalGroup(
            pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LAUNDRYSERVICEPOS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLocation)
                .addGap(31, 31, 31)
                .addComponent(employeelogin)
                .addGap(27, 27, 27)
                .addComponent(lblLoginText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlBGLayout = new javax.swing.GroupLayout(pnlBG);
        pnlBG.setLayout(pnlBGLayout);
        pnlBGLayout.setHorizontalGroup(
            pnlBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBGLayout.createSequentialGroup()
                .addContainerGap(131, Short.MAX_VALUE)
                .addGroup(pnlBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBGLayout.createSequentialGroup()
                        .addComponent(pnlBadge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBGLayout.createSequentialGroup()
                        .addComponent(pnlCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109))))
        );
        pnlBGLayout.setVerticalGroup(
            pnlBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBGLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pnlBadge, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 350, Short.MAX_VALUE)
                .addComponent(pnlCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );

        javax.swing.GroupLayout pnlRightLayout = new javax.swing.GroupLayout(pnlRight);
        pnlRight.setLayout(pnlRightLayout);
        pnlRightLayout.setHorizontalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(pnlBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 329, Short.MAX_VALUE))
        );
        pnlRightLayout.setVerticalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(pnlRight);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
	    // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
		 boolean systemOnline = false;   // Change to true when the system is online
    
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
    private javax.swing.JLabel lblLocation;
    private javax.swing.JLabel lblLoginText;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel pnlBG;
    private javax.swing.JPanel pnlBadge;
    private javax.swing.JPanel pnlCard;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JPanel pnlRight;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
