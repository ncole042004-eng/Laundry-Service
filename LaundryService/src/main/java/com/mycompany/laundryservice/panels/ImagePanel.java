package com.mycompany.laundryservice.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BaseMultiResolutionImage;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private Image image;

    /**
     * Creates an ImagePanel that displays a multi-resolution image.
     *
     * @param path Resource path of the image (e.g. "/images/background.png")
     */
    public ImagePanel(String path) {

        URL url = getClass().getResource(path);

        if (url == null) {
            throw new IllegalArgumentException("Image not found: " + path);
        }

        Image baseImage = new ImageIcon(url).getImage();

        /*
         * Multi-Resolution Image
         * Currently uses the same image for all resolution variants.
         * Higher-resolution images can be added later.
         */
        image = new BaseMultiResolutionImage(
                baseImage,
                baseImage,
                baseImage
        );

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

	java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

	g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        int panelW = getWidth();
        int panelH = getHeight();

        int imgW = image.getWidth(this);
        int imgH = image.getHeight(this);

        double scale = Math.max(
                (double) panelW / imgW,
                (double) panelH / imgH
        );

        int newW = (int) (imgW * scale);
        int newH = (int) (imgH * scale);

        int x = (panelW - newW) / 2;
        int y = (panelH - newH) / 2;

        g.drawImage(image, x, y, newW, newH, this);
    }
}