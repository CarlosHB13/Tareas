/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class iComboCheckBox extends JComboBox {

    static class ComboBoxUI extends WindowsComboBoxUI {
        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup bcp = (BasicComboPopup) super.createPopup();
            bcp.setBorder(BorderFactory.createLineBorder(new Color(206, 212, 218), 1));
            return bcp;
        }
    }
    private int positon;
    
    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }

    public iComboCheckBox() {
        init();

    }

    public iComboCheckBox(JCheckBox[] items) {
        super(items);
        init();

    }

    public iComboCheckBox(Vector items) {
        super(items);
        init();
    }

    public iComboCheckBox(ArrayList items) {
        super(items.toArray());
        init();
    }

    public iComboCheckBox(ComboBoxModel aModel) {
        super(aModel);
        init();
    }

    private void init() {
        setRenderer(new ComboBoxRenderer());
        addActionListener((ActionEvent ae) -> {
            itemSelected();
        });
        setUI(new ComboBoxUI());
    }

    private void itemSelected() {
        if (getSelectedItem() instanceof JCheckBox) {
            JCheckBox jcb = (JCheckBox) getSelectedItem();
            jcb.setSelected(!jcb.isSelected());
        }
    }

    class ComboBoxRenderer implements ListCellRenderer {

        private JLabel label;
        private final Border insetBorder;
        private final DefaultListCellRenderer defaultRenderer;

        public ComboBoxRenderer() {
            setOpaque(true);
            this.insetBorder = new EmptyBorder(0, 10, 0, 0);
            this.defaultRenderer = new DefaultListCellRenderer();
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            if (value instanceof JCheckBox) {
                JCheckBox c = (JCheckBox) value;
                c.setPreferredSize(new Dimension(c.getPreferredSize().width, 34));

                if (isSelected) {
                    c.setBackground(list.getSelectionBackground());
                    c.setForeground(list.getSelectionForeground());
                } else {
                    c.setBackground(Color.white);
                    c.setBorderPainted(true);
                }

                c.setBorder(BorderFactory.createCompoundBorder(
                        new MatteBorder(0, 0, 1, 0, new Color(206, 212, 218)),
                        new EmptyBorder(0, 10, 0, 0)
                )
                );

                return c;
            } else {
                if (label == null) {
                    label = new JLabel(value.toString());
                } else {
                    label.setText(value.toString());
                    label.setPreferredSize(new Dimension(label.getPreferredSize().width, 34));
                    label.setBackground(Color.white);
                    //label.setBorder(BorderFactory.createLineBorder(Color.yellow));

                }
                return label;
            }
        }
    }
}
