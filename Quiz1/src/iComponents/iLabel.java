/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author jorge.vasquez
 */
public class iLabel extends JLabel {
    
    private int positon;

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }
    
    public iLabel(String text) {
        super(text);
        setOpaque(false);

    }
    
    public iLabel(String text, Icon ico) 
    {
        super(ico);
        setText(text);
        setOpaque(false);
    }

    @Override
    public void setBackground(Color color) {
        setOpaque(true);
        super.setBackground(color);
    }
    
    
    
}
