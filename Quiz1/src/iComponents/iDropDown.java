package iComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import jiconfont.icons.GoogleMaterialDesignIcons;

public class iDropDown extends JPopupMenu implements MouseListener {

    private final iButtonFake btn;
    private Color DropDownColor, DropDowBackgroundColor;
    private JPopupMenu globalJM;
    public iDropDown(
            String Title,
            String Subtitle,
            //--------------------------
            Color ButtonColor,
            Color ButtonBackgroundColor,
            //---------------------------
            Color HoverColor,
            Color HoverBackgroundColor,
            GoogleMaterialDesignIcons ico,
            Color DropDownColor, 
            Color DropDowBackgroundColor
    ) 
    {
        super();
        globalJM = this;
        
        btn = new iButtonFake (
                Title,
                Subtitle,
                ButtonColor,
                ButtonBackgroundColor,
                HoverColor,
                HoverBackgroundColor,
                ico
        );
        
        this.DropDownColor = DropDownColor;
        this.DropDowBackgroundColor = DropDowBackgroundColor;
        
        setBackground(DropDowBackgroundColor);
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(206,212,218)));
        
        btn.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me) {
                globalJM.setPreferredSize(new Dimension((int)(btn.getWidth() * 1.5),globalJM.getPreferredSize().height));  
                globalJM.show(btn, 0, btn.getHeight());
            }
            
        });
        //setPreferredSize(new Dimension(getWidth(), getHeight()))

    }

    public JMenuItem add(JMenuItem jmi, boolean line) {
        jmi.setBackground(DropDowBackgroundColor);
        jmi.setForeground(DropDownColor);
        jmi.setPreferredSize(new Dimension(jmi.getPreferredSize().width, 30));
        jmi.setBorder(new EmptyBorder(0, 10, 0, 10));
        
        add(jmi);
        
        if (line)
        {
            JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
            sep.setForeground(new Color(206,212,218));
            sep.setBackground(Color.white);
            add(sep);
        }
        return jmi;
    }

    
    
    public iButtonFake getButton() {
        return btn;
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

}
