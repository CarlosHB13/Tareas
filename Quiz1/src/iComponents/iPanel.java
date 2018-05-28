/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author jorge.vasquez
 */
@SuppressWarnings("LeakingThisInConstructor")
public class iPanel extends JPanel implements ComponentInterfaz {

    private InitComponents ic = null;
    private boolean 
            ResponsiveWidth, 
            responsiveHeight, 
            responsiveExtendedWidth, 
            responsiveExtendedHeight, 
            responsiveSingleObjectWidth,
            responsiveAlwaysOnBottom,
            responsivePanel;

    private int responsiveExtendedPixelWidth, responsiveExtendedPixelHeight;
    private float responsiveExtendedPercentWidth, responsiveExtendedPercentHeight;

    private iFrame Container;
    
    private int positon;

    public boolean isResponsivePanel() {
        return responsivePanel;
    }

    public void setResponsivePanel(boolean responsivePanel) {
        this.responsivePanel = responsivePanel;
    }
    
    

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }
 
    public int getResponsiveExtendedPixelHeight() {
        return responsiveExtendedPixelHeight;
    }

    public float getResponsiveExtendedPercentHeight() {
        return responsiveExtendedPercentHeight;
    }

    public int getResponsiveExtendedPixelWidth() {
        return responsiveExtendedPixelWidth;
    }

    public float getResponsiveExtendedPercentWidth() {
        return responsiveExtendedPercentWidth;
    }

    public boolean isResponsiveAlwaysOnBottom() {
        return responsiveAlwaysOnBottom;
    }

    public void setResponsiveAlwaysOnBottom(boolean responsiveAlwaysOnBottom) {
        this.responsiveAlwaysOnBottom = responsiveAlwaysOnBottom;
    }
    

    public boolean isResponsiveSingleObjectWidth() {
        return responsiveSingleObjectWidth;
    }
    

    public boolean isResponsiveExtendedWidth() {
        return responsiveExtendedWidth;
    }

    public boolean isResponsiveExtendedHeight() {
        return responsiveExtendedHeight;
    }
    public int horizontal, vertical;

    public int getHorizontal() {
        return horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public boolean isResponsiveWidth() {
        return ResponsiveWidth;
    }

    public boolean isResponsiveHeight() {
        return responsiveHeight;
    }

    /**
     * NanosofotLayOut: By Lic. Isaac Vásquez
     *
     * @param horizontal: Cordenada X
     * @param vertical: Cordenada Y
     * @param w JFrame (Main) Width
     * @param h JFrame (Main) Height
     * @param margin Elements Margin (top-bottom) between them.
     * @param if_
     */
    public iPanel(int horizontal, int vertical, int w, int h, int margin, iFrame if_) {
        initPanel(horizontal, vertical, w, h, margin, 0, if_);
    }

    /**
     *
     * @param horizontal: Cordenada X
     * @param vertical: Cordenada Y
     * @param w JFrame (Main) Width
     * @param h JFrame (Main) Height
     * @param margin Elements Margin (top-bottom) between them.
     * @param InitMarginTop deja un espacio entre el JFrame y el primer
     * componente.
     * @param if_
     */
    public iPanel(int horizontal, int vertical, int w, int h, int margin, int InitMarginTop, iFrame if_) {
        initPanel(horizontal, vertical, w, h, margin, InitMarginTop, if_);
    }

    /**
     * la diferencia es que el width es float lo que significa que es un
     * porcentaje del ancho. ejemplo en vez de poner un valor en pixeles se pone
     * 80.0f que equivale al 80% del ancho del componente.
     *
     * @param horizontal: Cordenada X
     * @param vertical: Cordenada Y
     * @param w JFrame (Main) Width
     * @param h JFrame (Main) Height
     * @param margin Elements Margin (top-bottom) between them.
     * @param InitMarginTop deja un espacio entre el JFrame y el primer
     * componente.
     * @param if_ iFrame to add
     */
    public iPanel(int horizontal, int vertical, float w, int h, int margin, int InitMarginTop, iFrame if_) {
        int width = (int) ((if_.getWidth() * w) / 100);
        initPanel(horizontal, vertical, width, h, margin, InitMarginTop, if_);
        ResponsiveWidth = true;
    }

    /**
     * la diferencia es que el height es float lo que significa que es un
     * porcentaje del largo. ejemplo en vez de poner un valor en pixeles se pone
     * 80.0f que equivale al 80% del largo del componente.
     *
     * @param horizontal: Cordenada X
     * @param vertical: Cordenada Y
     * @param w JFrame (Main) Width
     * @param h JFrame (Main) Height
     * @param margin Elements Margin (top-bottom) between them.
     * @param InitMarginTop deja un espacio entre el JFrame y el primer
     * componente.
     * @param if_ iFrame to add
     */
    public iPanel(int horizontal, int vertical, int w, float h, int margin, int InitMarginTop, iFrame if_) {
        int height = (int) ((if_.getHeight() * h) / 100);
        initPanel(horizontal, vertical, w, height, margin, InitMarginTop, if_);
        responsiveHeight = true;
    }

    /**
     * la diferencia es que el height y el width es float lo que significa que
     * es un porcentaje del largo y el ancho. ejemplo en vez de poner un valor
     * en pixeles se pone 80.0f,80.0f que equivale al 80% del largo del
     * componente y del ancho del mismo
     *
     * @param horizontal: Cordenada X
     * @param vertical: Cordenada Y
     * @param w JFrame (Main) Width
     * @param h JFrame (Main) Height
     * @param margin Elements Margin (top-bottom) between them.
     * @param InitMarginTop deja un espacio entre el JFrame y el primer
     * componente.
     * @param if_ iFrame to add
     */
    public iPanel(int horizontal, int vertical, float w, float h, int margin, int InitMarginTop, iFrame if_) {
        int height = (int) ((if_.getHeight() * h) / 100);
        int width = (int) ((if_.getWidth() * w) / 100);
        initPanel(horizontal, vertical, width, height, margin, InitMarginTop, if_);
        ResponsiveWidth = responsiveHeight = true;
    }
    
    public iPanel(int horizontal, int vertical, int w, int h, int margin, int InitMarginTop) {
        initPanel(horizontal, vertical, w, h, margin, InitMarginTop, null);
        ResponsiveWidth = responsiveHeight = true;
    }

    public final void initPanel(int horizontal, int vertical, int w, int h, int margin, int InitMarginTop, iFrame if_) {
        
        ic = new InitComponents(w, h, margin, InitMarginTop);

        this.horizontal = horizontal;
        this.vertical = vertical;
        
        setLayout(null);
        setPreferredSize(new Dimension(w-horizontal, h));
        setBounds(horizontal, vertical, w-horizontal, h);

        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        if (if_ != null)
        {
            Container = if_;
            Container.add(this);
        }
    }

    @Override
    public void newLine() {
        ic.newLine();
        for (Object[] element : this.getObject()) {
            if (element[1] != null && element[2] != null) {
                Component comp = (Component) element[0];
                add((Component) comp);
            }
        }
        ic.deleteObject();
    }

    @Override
    public Component AddObject(Component component, int width, int height) {
        setResponsiveByiComponent(component, CENTER);
        return ic.AddObject(component, width, height);
    }

    @Override
    public Component AddObject(Component component, int width, int height, int Position) {
        setResponsiveByiComponent(component, Position);
        return ic.AddObject(component, width, height, Position);
    }

    @Override
    public Object[][] getObject() {
        return ic.getObject();
    }

    @Override
    public void deleteObject() {
        ic.deleteObject();
    }

    @Override
    public void finalice() {
        setVisible(true);
    }

    @Override
    public void border(int top, int left, int bottom, int right, Color c) {
        setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, c));
    }

    @Override
    public void dispose() {
        ic.dispose();

        removeAll();
        revalidate();
        repaint();
    }

    @Override
    public Component AddSingleObject(Component component, int width, int height, int Position) {
        setResponsiveByiComponent(component, Position);
        return add(ic.AddSingleObject(component, width, height, Position));
    }

    /**
     * la diferencia es que el width es float lo que significa que es un
     * porcentaje del ancho. ejemplo en vez de poner un valor en pixeles se pone
     * 80.0f que equivale al 80% del ancho del componente.
     *
     * @param component
     * @param width
     * @param height
     * @param Position
     * @return
     */
    public Component AddSingleObject(Component component, float width, int height, int Position) 
    {
        responsiveSingleObjectWidth = true;
        //lista de componentes que pueden ser responsive.
        if (component instanceof iScrollPane)
        {
            ((iScrollPane) component).setResponsiveWidth(true);
            ((iScrollPane) component).setresponsivePercentWidth(width);
        }
        setResponsiveByiComponent(component, Position);

        return add(ic.AddSingleObject(component, (int) ((this.getWidth() * width) / 100), height, Position));
    }

    /**
     * la diferencia es que el width es float lo que significa que es un
     * porcentaje del ancho y el largo. ejemplo en vez de poner un valor en
     * pixeles se pone 80.0f, 80.0f que equivale al 80% del ancho del componente
     * y un 80% del largo del componente
     *
     * @param component
     * @param width
     * @param height
     * @param Position
     * @return
     */
    public Component AddSingleObject(Component component, float width, float height, int Position) {
        if (component instanceof iScrollPane)
        {
            ((iScrollPane) component).setResponsiveWidth(true);
            ((iScrollPane) component).setresponsivePercentWidth(width);
            ((iScrollPane) component).setResponsiveHeight(true);
            ((iScrollPane) component).setresponsivePercentHeight(height);
        }
        setResponsiveByiComponent(component, Position);
        
        return add(ic.AddSingleObject(component, (int) ((this.getWidth() * width) / 100), (int) ((this.getHeight() * height) / 100), Position));
    }
    
    
    public void setResponsiveByiComponent(Component c, int position) 
    {
        if (c instanceof iTextField) 
           ((iTextField) c).setPositon(position);
        else if (c instanceof iPasswordField)
           ((iPasswordField) c).setPositon(position);
        else if (c instanceof iButton)
            ((iButton) c).setPositon(position);
        else if (c instanceof iCalendar)
            ((iCalendar) c).setPositon(position);
        else if (c instanceof iLabel)
            ((iLabel) c).setPositon(position);
        else if (c instanceof iButtonFake)
            ((iButtonFake) c).setPositon(position);
        else if (c instanceof iComboCheckBox)
            ((iComboCheckBox) c).setPositon(position);
        else if (c instanceof iPanel)
            ((iPanel) c).setPositon(position);
    }

    @Override
    public void addSpace(int height) {
        ic.addSpace(height);
    }

    /**
     * Jorge Isaac calcula el valor del porcentaje necesitado a veces se
     * utilizan varios iPanels, y es necesario este método para calcular su
     * largo o ancho.
     *
     * @param percent
     * @param pixel
     */
    public void setResponsiveWidth(float percent, int pixel) {
        responsiveExtendedPixelWidth = pixel;
        responsiveExtendedPercentWidth = percent;
        
        int tmpWidth = (int) (100 * (Container.getWidth() - pixel) / percent);
        setComponentWidth(tmpWidth);
        
        setBounds(
                getHorizontal(),
                getVertical(),
                tmpWidth,
                getHeight()
        );
        responsiveExtendedWidth = true;
    }
    
    public int calcWidth(float percent, int pixel) {
        return (int) ((getWidth()* percent) / 100) - pixel;
    }
    
    public int calcHeigth(float percent, int pixel) {
        return (int) ((getHeight() * percent) / 100) - pixel;
    }

    public int calcHeigth(float percent) {
        return (int) (getHeight() * percent) / 100;
    }
    /**
     * Jorge Isaac calcula el valor del porcentaje necesitado a veces se
     * utilizan varios iPanels, y es necesario este método para calcular su
     * largo o ancho.
     *
     * @param percent
     * @param pixel
     */
    public void setResponsiveHeight(float percent, int pixel) {
        responsiveExtendedPixelHeight = pixel;
        responsiveExtendedPercentHeight = percent;
        
        int tmpHeight = (int) (100 * (Container.getHeight() - pixel) / percent);
        setComponentHeight(tmpHeight);
        
        setBounds(
                getHorizontal(),
                getVertical(),
                getWidth(),
                tmpHeight
        );
        responsiveExtendedHeight = true;
    }

    public void setComponentWidth(int componentWidth) {
        ic.setComponentWidth(componentWidth);
    }

    public void setComponentHeight(int componentHeight) {
        ic.setComponentHeight(componentHeight);
    }

    @Override
    public iBackground setBackgroundImage(String dir) {
        setBorder(ic.setBackgroundImage(dir));
        return null;
    }

}
