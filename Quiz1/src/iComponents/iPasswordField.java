/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JPasswordField;

/**
 *
 * @author jorge.vasquez
 */
@SuppressWarnings("LeakingThisInConstructor")
public class iPasswordField extends JPasswordField implements FocusListener {
    private Shape shape;
    private final int radius;
    private Color backgroundColor, fontColor;
    private final String HoverText;
    
    private Color borderDefaultState, borderColorFocus = new Color(0x80bdff), borderColor = new Color(206,212,218);
    private int positon;

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }
    

    /**
     * Crea un boton más personalizado
     *
     * @param HoverText: el texto a mostrar
     * @param borderRadius : similar a CSS
     */
    public iPasswordField(String HoverText, int borderRadius) 
    {
        this.radius = borderRadius;
        this.backgroundColor = Color.WHITE;
        this.fontColor = new Color(73, 80, 87);
        setOpaque(false);
        setForeground(fontColor);
        this.HoverText = HoverText;
        borderDefaultState = borderColor;
        setText(HoverText);
        setEchoChar((char) 0);
        setForeground(this.fontColor.brighter().brighter());
        addFocusListener(this);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
    }

        @Override
    public void setBackground(Color color) {
        this.backgroundColor = color;
        repaint();
    }


    @Override
    public void paint(Graphics g) 
    {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
        g2d.setColor(this.backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), this.radius, this.radius);

        g2d.setColor(borderDefaultState);
        g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, this.radius, this.radius);
  
         if (shape == null || !shape.getBounds().equals(getBounds())) 
         {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, this.radius, this.radius);
         }
         
        // Draw the text in the center
        g2d.setColor(this.fontColor);        
        super.paintComponent(g2d);
        g2d.dispose();
    }    

    @Override
    public void focusGained(FocusEvent fe) {
        String txtpass = new String (getPassword());
        if (txtpass.equals(this.HoverText))
        {
            setText("");
            setForeground(this.fontColor.darker().darker());
            setEchoChar('\u2022');
        }
        borderDefaultState = borderColorFocus;
        repaint();
    }

    @Override
    public void focusLost(FocusEvent fe) {
        String txtpass = new String (getPassword());
        if (txtpass.isEmpty())
        {
            setText(this.HoverText);
            setEchoChar((char) 0);
            setForeground(this.fontColor.brighter().brighter());  
        }
        borderDefaultState = borderColor;
        repaint();
    }
}
