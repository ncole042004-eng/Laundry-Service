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
    public Dimension getPreferredSize() {
        return new Dimension(224, 224);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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