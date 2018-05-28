/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * thx to gaussianFilter and taken from
 * https://stackoverflow.com/questions/19105242/undecorated-jframe-shadow?rq=1
 */
package iComponents;

import iComponents.Gaussian.GaussianFilter;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class iShadowPane extends JPanel {

    private BufferedImage shadow;

    public iShadowPane() {
        setOpaque(false);
        setBorder(new EmptyBorder(9, 9, 9, 9));
    }

    @Override
    public void invalidate() {
        shadow = null;
        super.invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Insets insets = getInsets();
        int x = 9;
        int y = 18;
        int width = getWidth() - (insets.left + insets.right);
        int height = getHeight() - (insets.top + insets.bottom);
        if (shadow == null) {
            // Try and "guess" the amount of shadow we can show...
            int shadowWidth = Math.min(Math.min(insets.left, insets.top), Math.min(insets.right, insets.bottom));
            shadow = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = shadow.createGraphics();
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, width, height);
            g2d.dispose();
            shadow = generateShadow(shadow, shadowWidth, Color.BLACK, 0.28f);
        }
        g.drawImage(shadow, 0, 0, this);
        g.setColor(getBackground());
        g.fillRect(x, y, width, height);
    }

    public static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    public static BufferedImage createCompatibleImage(int width, int height) {
        return createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    }

    public static BufferedImage createCompatibleImage(int width, int height, int transparency) {
        BufferedImage image = getGraphicsConfiguration2().createCompatibleImage(width, height, transparency);
        image.coerceData(true);
        return image;
    }

    public static BufferedImage createCompatibleImage(BufferedImage image) {
        return createCompatibleImage(image, image.getWidth(), image.getHeight());
    }

    public static BufferedImage createCompatibleImage(BufferedImage image,
            int width, int height) {
        return getGraphicsConfiguration2().createCompatibleImage(width, height, image.getTransparency());
    }

    public static GraphicsConfiguration getGraphicsConfiguration2() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    public static BufferedImage generateBlur(BufferedImage imgSource, int size, Color color, float alpha) {
        GaussianFilter filter = new GaussianFilter(size);

        int imgWidth = imgSource.getWidth();
        int imgHeight = imgSource.getHeight();

        BufferedImage imgBlur = createCompatibleImage(imgWidth, imgHeight);
        Graphics2D g2 = imgBlur.createGraphics();
        applyQualityRenderingHints(g2);

        g2.drawImage(imgSource, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
        g2.setColor(color);

        g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
        g2.dispose();

        imgBlur = filter.filter(imgBlur, null);

        return imgBlur;
    }

    public static BufferedImage generateShadow(BufferedImage imgSource, int size, Color color, float alpha) {
        int imgWidth = imgSource.getWidth() + (size * 2);
        int imgHeight = imgSource.getHeight() + (size * 2);

        BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight);
        Graphics2D g2 = imgMask.createGraphics();
        applyQualityRenderingHints(g2);

        int x = Math.round((imgWidth - imgSource.getWidth()) / 2f);
        int y = Math.round((imgHeight - imgSource.getHeight()) / 2f);
        g2.drawImage(imgSource, x, y, null);
        g2.dispose();

        // ---- Blur here ---
        BufferedImage imgGlow = generateBlur(imgMask, (size * 2), color, alpha);

        return imgGlow;
    }
}
