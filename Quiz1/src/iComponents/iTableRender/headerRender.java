/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents.iTableRender;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author jorge.vasquez
 */
public class headerRender extends JLabel implements TableCellRenderer {

    public headerRender() 
    {
        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setOpaque(true);
        setBackground(new Color(0, 127, 112));
        setForeground(Color.WHITE);
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xeceeef)));
        setHorizontalAlignment(CENTER);
        setPreferredSize(new Dimension(getPreferredSize().width,34));
    }
    
    public String getFormatedText(String unformated) {
        return Character.toUpperCase(unformated.charAt(0)) + unformated.substring(1).replaceAll("_", " ");
    }
     
    @Override
    public Component getTableCellRendererComponent(
            JTable table, 
            Object value,
            boolean isSelected, 
            boolean hasFocus, 
            int row, 
            int column) 
    {
        setText(getFormatedText(value.toString()));
        return this;
    }
    
}
