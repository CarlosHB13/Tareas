/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 *
 * @author jorge.vasquez
 */
public class InitComponents implements ComponentInterfaz 
{

    /**
     * Lic. Vásquez Valenciano Jorge Isaac Universidad Latina de Costa Rica
     * Variables utilizadas para la construcción del iFrame o iPanel
     *
     */
    private int componentWidth,
            componentHeight,
            marginBetweenElements,
            firstMarginTop;

    /**
     * Lic. Vásquez Valenciano Jorge Isaac Universidad Latina de Costa Rica
     * Variables utilizadas por el método getXCenter() y getYCenter().
     *
     */
    private int getCenteredHeight,
            getCenteredWidth,
            tmpWidth,
            tmpHeight;

    /**
     * Lic. Vásquez Valenciano Jorge Isaac Universidad Latina de Costa Rica
     * Variables utilizadas por el método getXRightAlignment().
     *
     */
    private int getXRightAlignmentWidth;

    /**
     * Lic. Vásquez Valenciano Jorge Isaac Universidad Latina de Costa Rica
     * Variables utilizadas por el método getXLeftAlignment().
     *
     */
    private int getXLeftAlignmentWidth,
            tmpXLeftAlignment;

    // ????
    private boolean isRow, firstObjectinMarginTop;
    private final boolean haveMarginTop;
    private int totalWidth, currentRow;

    /**
     * Lic. Vásquez Valenciano Jorge Isaac Universidad Latina de Costa Rica
     * Variables necesarias para el manejo de objetos en matriz
     *
     */
    private Object[][] object = new Object[80][80];
    private int MatrixSize;
    
    public InitComponents(int w, int h, int margin, int objectMarginTop) {
        this.componentHeight = h;
        this.componentWidth = w;
        this.marginBetweenElements = margin;
        this.isRow = false;
        this.totalWidth = 0;
        this.currentRow = 0;
        this.firstMarginTop = objectMarginTop;
        this.haveMarginTop = (objectMarginTop > 0);
        this.firstObjectinMarginTop = haveMarginTop;
        this.MatrixSize = 0;
    }

    public void setComponentWidth(int componentWidth) {
        this.componentWidth = componentWidth;
    }

    public void setComponentHeight(int componentHeight) {
        this.componentHeight = componentHeight;
    }

    
    
    
    public Dimension setComponentDimension() {
        return new Dimension(this.componentWidth, this.componentHeight);
    }

    public LayoutManager getLayOut() 
    {
        return null;
    }
    
    public int getAlignment(int position, int element_width)
    {
        switch (position) 
        {
            case CENTER:
            default:
                if ((this.getRowState() && this.currentRow == 1))
                {
                    this.getCenteredWidth = ((this.componentWidth / 2) - (this.totalWidth / 2));
                    this.tmpWidth = element_width;
                    return this.getCenteredWidth;
                }
                else if (!this.getRowState() && this.currentRow > 0)
                {
                    this.getCenteredWidth += this.tmpWidth;// + this.object_margin;
                    this.tmpWidth = element_width;
                    return this.getCenteredWidth;
                }
                else 
                {
                    return (this.componentWidth / 2) - element_width / 2;
                }
            
            case RIGHT:
                if ((this.getRowState() && (this.currentRow == 1 || this.getXRightAlignmentWidth == 0)) || !this.getRowState()) 
                    this.getXRightAlignmentWidth = (this.componentWidth - this.marginBetweenElements) - element_width;
                else
                    this.getXRightAlignmentWidth -= element_width;

                return this.getXRightAlignmentWidth;
                
            case LEFT:
                if ((this.getRowState() && (this.currentRow == 1 || this.tmpXLeftAlignment == 0)) || !this.getRowState()) 
                {
                    this.tmpXLeftAlignment = element_width;
                    this.getXLeftAlignmentWidth = this.marginBetweenElements;
                }
                else
                {
                    this.getXLeftAlignmentWidth += this.tmpXLeftAlignment + this.marginBetweenElements;
                    this.tmpXLeftAlignment = element_width;
                }
                return this.getXLeftAlignmentWidth;
        }
    }

    public int getYCenter(int element_height) 
    {
        if (this.firstObjectinMarginTop && this.haveMarginTop) 
        {
            this.firstObjectinMarginTop = false;
            this.getCenteredHeight += this.firstMarginTop;
        }
        if ((this.getRowState() && this.currentRow == 1) || !this.getRowState()) 
        {
            this.getCenteredHeight += element_height + this.marginBetweenElements;
        }

        // ya que hace una suma antes de retornar el cálculo, debemos arreglarlo.
        return this.getCenteredHeight - element_height;
    }

    public Rectangle getRectangle(int width, int height) {
        if (this.getRowState()) {
            throw new UnsupportedOperationException("getRectangle(): You can´t cast this method inside a Row");
        }

        return new Rectangle (
            this.getAlignment(CENTER, width),
            this.getYCenter(height),
            width,
            height
        );
    }

    public boolean getRowState() {
        return this.isRow;
    }

    @Override
    public void newLine() 
    {
        this.isRow = true;
        for (Object[] object1 : this.getObject()) 
        {
            if (object1[1] != null)
                this.totalWidth += (int) object1[1];
        }

        if (this.totalWidth > this.componentWidth)
            throw new ClassFormatError("setRow(): elements width is bigger than main component.(" + this.totalWidth + "/" + this.componentWidth + ")");

        for (Object[] element : this.getObject()) 
        {
            if (element[1] == null || element[2] == null || element[3] == null)
                continue;

            this.currentRow++;
            AddSingleObject((Component) element[0], (int) element[1], (int) element[2], (int) element[3]);
        }

        this.isRow = false;
        this.totalWidth = 0;
        this.tmpWidth = 0;
        this.tmpHeight = 0;
        this.currentRow = 0;
        this.tmpHeight = 0;

        this.getXLeftAlignmentWidth = 0;
        this.getXRightAlignmentWidth = 0;
        this.getCenteredWidth = 0;
        this.tmpXLeftAlignment = 0;
        this.MatrixSize = 0;
    }

    @Override
    public void finalice() {}

    @Override
    public Component AddObject(Component component, int width, int height, int Position) 
    {
        this.object[MatrixSize][0] = component;
        this.object[MatrixSize][1] = width;
        this.object[MatrixSize][2] = height;
        this.object[MatrixSize][3] = Position;
        this.MatrixSize++;
        return component;
    }

    @Override
    public Component AddObject(Component component, int width, int height) 
    {
        return this.AddObject(component, width, height, CENTER);
    }

    @Override
    public Object[][] getObject() 
    {
        return this.object;
    }

    @Override
    public void deleteObject() {
        this.object = new Object[40][40];
    }

    @Override
    public void border(int top, int left, int bottom, int right, Color c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dispose() 
    {
        this.isRow = false;
        this.totalWidth = 0;
        this.tmpWidth = 0;
        this.tmpHeight = 0;
        this.currentRow = 0;
        this.tmpHeight = 0;

        this.getXLeftAlignmentWidth = 0;
        this.getXRightAlignmentWidth = 0;
        this.getCenteredWidth = 0;
        this.tmpXLeftAlignment = 0;

        this.firstObjectinMarginTop = true;
        this.getCenteredHeight = 0;

        this.MatrixSize = 0;
    }

    @Override
    public Component AddSingleObject(Component component, int width, int height, int Position) 
    {
        switch (Position) 
        {
            case CENTER:
                component.setBounds(
                    this.getAlignment(CENTER, width),
                    this.getYCenter(height),
                    width,
                    height
                );
                break;

            case RIGHT:
                component.setBounds(
                    this.getAlignment(RIGHT, width),
                    this.getYCenter(height),
                    width,
                    height
                );
                break;

            case LEFT:
                component.setBounds(
                    this.getAlignment(LEFT, width),
                    this.getYCenter(height),
                    width,
                    height
                );
                break;

            default:
                component.setBounds(
                    Position,
                    this.getYCenter(height),
                    width,
                    height
                );
                break;
        }
        this.tmpHeight = height;
        return component;
    }

    @Override
    public void addSpace(int height) {
        AddSingleObject(new JLabel(""), 10, height, CENTER);
    }

    @Override
    public iBackground setBackgroundImage(String dir) {
        try {   
            return new iBackground(ImageIO.read(new File(dir)));
        } catch (IOException ex) {
            return null;
        }
    }
    
    
}
