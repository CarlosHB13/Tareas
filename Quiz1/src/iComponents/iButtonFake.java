/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import iComponents.Gaussian.ColorUtils;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

/**
 * La única desventaja de iButtonFake contra iButton
 * es que no puede tener border redondos.
 * ya que al hacer que un boton sea redondo, pierde la caracterísitica
 * de poder usar html tags.
 * @author jorge.vasquez
 */
public class iButtonFake extends JLabel implements MouseListener {

    private String Title, Subtitle;
    private Color ButtonColor, ButtonBackgroundColor, HoverColor, HoverBackgroundColor;
    private GoogleMaterialDesignIcons ico;

    private final ColorUtils cu = new ColorUtils();
    
    private int positon;

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }
    public iButtonFake(
            String Title,
            String Subtitle,
            //--------------------------
            Color ButtonColor,
            Color ButtonBackgroundColor,
            //---------------------------
            Color HoverColor,
            Color HoverBackgroundColor,
            GoogleMaterialDesignIcons ico
    ) {
        initBtnFake(Title, Subtitle, ButtonColor, ButtonBackgroundColor, HoverColor, HoverBackgroundColor, ico);
    }

    public final void initBtnFake(
            String Title,
            String Subtitle,
            //--------------------------
            Color ButtonColor,
            Color ButtonBackgroundColor,
            //---------------------------
            Color HoverColor,
            Color HoverBackgroundColor,
            GoogleMaterialDesignIcons ico) {
        this.Title = Title;
        this.Subtitle = Subtitle;

        this.ButtonColor = ButtonColor;
        this.ButtonBackgroundColor = ButtonBackgroundColor;

        this.HoverColor = HoverColor;
        this.HoverBackgroundColor = HoverBackgroundColor;

        this.ico = ico;

        if (!Subtitle.isEmpty()) 
        {
            setText(""
                    + "<html>"
                    + "<span style='color:" + cu.getColorNameFromColor(this.ButtonColor) + ";'>" + Title + "</span><br/>"
                    + "<span style='font-size:6px; color: #C8C9CB;'>" + Subtitle + "</span>"
                    + "</html>"
            );
        } 
        else 
        {
            setText(""
                    + "<html>"
                    + "<span style='color:" + cu.getColorNameFromColor(this.ButtonColor) + ";'>" + Title + "</span><br/>"
                    + "</html>"
            );
        }
        
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        if (ico != null)
        {
            Icon icon = IconFontSwing.buildIcon(ico, 16, ButtonColor);
            
            setIcon(icon);
            setIconTextGap(10);
        }
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBackground(ButtonBackgroundColor);
        addMouseListener(this);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }
    
    public String getHTMLColor(Color c) 
    {
        return "#"+Integer.toHexString(c.getRGB()).substring(2);
    }

    /**
     * iComponents
     * Cuadrado: un borde cuadrado
     * @param top
     * @param left
     * @param bottom
     * @param right
     * @param borderC
     */
    public void setBorder(int top, int left, int bottom, int right, Color borderC ) 
    {
        setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, borderC));
    }
    
    @Override
    public void mouseClicked(MouseEvent me) { }

    @Override
    public void mousePressed(MouseEvent me) {
        if (!Subtitle.isEmpty()) {
            setText(""
                    + "<html>"
                    + "<span style='color:" + this.getHTMLColor(this.HoverColor) + ";'>" + Title + "</span><br/>"
                    + "<span style='font-size:6px; color: #C8C9CB;'>" + Subtitle + "</span>"
                    + "</html>"
            );
        } else {
            setText(""
                    + "<html>"
                    + "<span style='color:" + this.getHTMLColor(this.HoverColor) + ";'>" + Title + "</span><br/>"
                    + "</html>"
            );
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBackground(HoverBackgroundColor);
        
    }

    @Override
    public void mouseReleased(MouseEvent me) 
    {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (!Subtitle.isEmpty()) {
            setText(""
                    + "<html>"
                    + "<span style='color:" + this.getHTMLColor(this.HoverColor) + ";'>" + Title + "</span><br/>"
                    + "<span style='font-size:6px; color: #C8C9CB;'>" + Subtitle + "</span>"
                    + "</html>"
            );
        } else {
            setText(""
                    + "<html>"
                    + "<span style='color:" + this.getHTMLColor(this.HoverColor) + ";'>" + Title + "</span><br/>"
                    + "</html>"
            );
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBackground(HoverBackgroundColor);
    }

    @Override
    public void mouseExited(MouseEvent me) 
    {
        if (!Subtitle.isEmpty()) 
        {
            setText(""
                    + "<html>"
                    + "<span style='color:" + this.getHTMLColor(this.ButtonColor) + ";'>" + Title + "</span><br/>"
                    + "<span style='font-size:6px; color: #C8C9CB;'>" + Subtitle + "</span>"
                    + "</html>"
            );
        } 
        else 
        {
            setText(""
                    + "<html>"
                    + "<span style='color:" + this.getHTMLColor(this.ButtonColor) + ";'>" + Title + "</span><br/>"
                    + "</html>"
            );
        }
        setBackground(ButtonBackgroundColor);
    }


    

}
