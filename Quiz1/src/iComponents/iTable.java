/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import iComponents.iTableRender.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author jorge.vasquez
 */
@SuppressWarnings("UseOfObsoleteCollectionType")
public class iTable extends JTable {

    private final ArrayList<String> columnas;
    iTextField txt;
    private int rollOverRowIndex = -1;
    
    private iScrollPane isp;
    
    public class TBM extends DefaultTableModel
{
   public TBM(Object[] tableData, int colNames) {
      super(tableData, colNames);
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      return false;
   }
}


    public iTable(ArrayList<String> cols) {
        setModel(new TBM(cols.toArray(), 0));
        this.columnas = cols;
        setRowSorter(new TableRowSorter<>(getModel()));
        getTableHeader().setDefaultRenderer(new headerRender());

        setShowGrid(false);
        setShowVerticalLines(false);
        setRowHeight(34);

        RollOverListener lst = new RollOverListener();
        addMouseMotionListener(lst);
        addMouseListener(lst);
    }

    public iTable(ArrayList<String> cols, iTextField txt) {
        setModel(new TBM(cols.toArray(), 0));
        this.columnas = cols;

        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(getModel());
        setRowSorter(sorter);

        txt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                if (txt.getText().isEmpty() || txt.getText().equals(txt.getHover())) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(txt.getText()));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                if (txt.getText().isEmpty() || txt.getText().equals(txt.getHover())) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(txt.getText()));
                }

            }

            @Override
            public void changedUpdate(DocumentEvent de) {

            }
        });
        getTableHeader().setDefaultRenderer(new headerRender());
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setShowGrid(false);
        setShowVerticalLines(false);
        setRowHeight(34);

        RollOverListener lst = new RollOverListener();
        addMouseMotionListener(lst);
        addMouseListener(lst);
        setFillsViewportHeight(true);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (row == rollOverRowIndex) {
            c.setForeground(getSelectionForeground());
            c.setBackground(getSelectionBackground());
        } else if (row % 2 == 0) {
            c.setBackground(Color.white);
        } else {
            c.setBackground(new Color(0xF2F2F2));
        }
        return c;
    }

    public JScrollPane getScrollTable(Color col) {
         isp = new iScrollPane(this, col);
         return isp;
    }

    /**
     * Añade una fila a una tabla determinada.
     *
     * @param obj Arreglo con datos de fila, debe ser constante el numero
     * objetos con el numero de columnas.
     */
    public void addrow(Object[] obj) {
        DefaultTableModel tb = (DefaultTableModel) getModel();

        tb.addRow(obj);
        for (int i = 0; i < getColumnCount(); i++) 
        {
            if (!(obj[i] instanceof iButton) && !(obj[i] instanceof JButton)) {
                getColumnModel().getColumn(i).setCellRenderer(new RowRender());
            } 
            else 
            {
                // is a button
                iButton btn = (iButton) obj[i];
                getColumnModel().getColumn(i).setCellRenderer(
                        new iButtonTableRender(
                                btn.getText(),
                                btn.getRadius(),
                                btn.getBackgroundColor(),
                                btn.getFontColor()
                        )
                );
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                        int column = getColumnModel().getColumnIndexAtX(me.getX());
                        int row = me.getY() / getRowHeight();


                        if (row < getRowCount() && row >= 0 && column < getColumnCount() && column >= 0) 
                        {
                            Object value = getValueAt(row, column);
                            if (value instanceof iButton) {
                                ((iButton) value).doClick();
                            }

                        }
                    }

                });
            }
        }
    }

    private class RollOverListener extends MouseInputAdapter {

        @Override
        public void mouseExited(MouseEvent e) {
            rollOverRowIndex = -1;
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int row = rowAtPoint(e.getPoint());
            if (row != rollOverRowIndex) {
                rollOverRowIndex = row;
                repaint();
            }
        }
    }

}
