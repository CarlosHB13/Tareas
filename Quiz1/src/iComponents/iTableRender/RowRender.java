/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents.iTableRender;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author jorge.vasquez
 */
public class RowRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row%2 == 0)
            cellComponent.setBackground(Color.white);
        else
            cellComponent.setBackground(new Color(0xF2F2F2));
        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        return cellComponent;
    }
}
