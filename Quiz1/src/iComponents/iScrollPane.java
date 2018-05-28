/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

/**
 *
 * @author jorge.vasquez
 */
public class iScrollPane extends JScrollPane 
{

    private float responsivePercentWidth, responsivePercentHeight;
    private boolean ResponsiveWidth, ResponsiveHeight;
    
    public iScrollPane(Component c, Color col) 
    {
        super(c);
        setOpaque(true);
        getViewport().setBackground(col);
        setBorder(BorderFactory.createLineBorder(new Color(0xeceeef), 1, true));
    }

    public float getresponsivePercentWidth() {
        return responsivePercentWidth;
    }

    public void setresponsivePercentWidth(float responsiveNumWidth) {
        this.responsivePercentWidth = responsiveNumWidth;
    }
    
    public void setresponsivePercentHeight(float responsiveNumWidth) {
        this.responsivePercentHeight = responsiveNumWidth;
    }


    public void setResponsiveHeight(boolean ResponsiveHeight) {
        this.ResponsiveHeight = ResponsiveHeight;
    }

    public float getResponsivePercentHeight() {
        return responsivePercentHeight;
    }

    public boolean isResponsiveHeight() {
        return ResponsiveHeight;
    }
    
    public boolean isResponsiveWidth() {
        return ResponsiveWidth;
    }

    public void setResponsiveWidth(boolean Responsive) {
        this.ResponsiveWidth = Responsive;
    }
    
    
    
}
