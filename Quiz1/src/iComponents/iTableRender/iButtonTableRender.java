/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents.iTableRender;

import iComponents.iButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author jorge.vasquez
 */
public class iButtonTableRender extends iButton implements TableCellRenderer {

    public iButtonTableRender(String Text, int borderRadius, Color backgroundColor, Color fontColor) 
    {
        super(Text, borderRadius, backgroundColor, fontColor);
        setOpaque(true);
    }
        
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
        return this;
    }
    
}
