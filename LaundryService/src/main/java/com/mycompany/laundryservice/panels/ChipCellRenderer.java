/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laundryservice.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 *
 * @author Cral
 */
public class ChipCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        String status = String.valueOf(value);
        switch (status) {
            case "Pending" -> {
                label.setBackground(new Color(0xff, 0xf3, 0xe0));
                label.setForeground(new Color(0xef, 0x6c, 0x00));
            }
            case "Processing", "Ready" -> {
                label.setBackground(new Color(0x3b, 0xd0, 0xfd, 77)); // 77 = ~30% opacity
                label.setForeground(new Color(0x00, 0x56, 0x6c));
            }
            case "Claimed", "Paid" -> {
                label.setBackground(new Color(0xe8, 0xf5, 0xe9));
                label.setForeground(new Color(0x2e, 0x7d, 0x32));
            }
            case "Unpaid" -> {
                label.setBackground(new Color(0xff, 0xda, 0xd6));
                label.setForeground(new Color(0xba, 0x1a, 0x1a));
            }
            default -> {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
        }
        return label;
    }
}