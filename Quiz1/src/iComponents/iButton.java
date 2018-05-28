/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;

/**
 *
 * @author jorge.vasquez
 */
@SuppressWarnings("LeakingThisInConstructor")
public class iButton extends JButton implements MouseListener {

    private final int radius;
    private int positon;

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }

    public int getRadius() {
        return radius;
    }
    private Color backgroundColor, fontColor;
    
    private final Cursor handCursor, defaultCursor;

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getFontColor() {
        return fontColor;
    }

    /**
     * Crea un boton más personalizado
     * @param Text : el texto a mostrar
     * @param borderRadius : similar a CSS
     * @param backgroundColor : del boton
     * @param fontColor : del texto del boton
     */
    public iButton(String Text, int borderRadius, Color backgroundColor, Color fontColor) 
    {
        super(Text);
        this.radius = borderRadius;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
        setOpaque(true);
        
        this.handCursor = new Cursor(Cursor.HAND_CURSOR);
        this.defaultCursor = this.getCursor();
        addMouseListener(this);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    @Override
    public void paint(Graphics g) {
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);

        Graphics2D g2d = (Graphics2D) g;

        // Anti-aliased lines and text
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        super.paint(g);

        // Background del boton
        g2d.setColor(this.backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), this.radius, this.radius);
        
        // color del borde
        g2d.setColor(this.backgroundColor.darker().darker());
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, this.radius, this.radius);

        // Determine the label size so can center it
        FontRenderContext frc = new FontRenderContext(null, false, false);
        Rectangle2D r = getFont().getStringBounds(getText(), frc);

        float xMargin = (float) (getWidth() - r.getWidth()) / 2;
        float yMargin = (float) (getHeight() - getFont().getSize()) / 2;

        // Draw the text in the center
        g2d.setColor(this.fontColor);
        g2d.drawString(getText(), xMargin,
                (float) getFont().getSize() + yMargin);
        g2d.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        this.backgroundColor = this.backgroundColor.brighter();
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        this.backgroundColor = this.backgroundColor.darker();
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent me) 
    {
        setOpaque(true);
        this.backgroundColor = this.backgroundColor.brighter();
        setCursor(this.handCursor);
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent me) 
    {
        this.setCursor(this.defaultCursor);             
        this.backgroundColor = this.backgroundColor.darker();
        repaint();
    }
}
