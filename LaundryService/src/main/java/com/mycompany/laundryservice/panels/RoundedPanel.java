package com.mycompany.laundryservice.panels;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RoundedPanel extends JPanel {
    private final int radius;
    private final Color fillColor;

    public RoundedPanel(int radius, Color fillColor) {
        this.radius = radius;
        this.fillColor = fillColor;
        setOpaque(false);
    }

@Override
protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
    );

    g2.setColor(fillColor);
    g2.fillRoundRect(
        0, 0,
        getWidth() - 1,
        getHeight() - 1,
        radius,
        radius
    );

    g2.dispose();
}}