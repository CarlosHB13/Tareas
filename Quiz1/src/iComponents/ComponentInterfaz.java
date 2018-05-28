/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.awt.Color;
import java.awt.Component;

/**
 *
 * @author jorge.vasquez
 */
public interface ComponentInterfaz {
    
    public static final int CENTER = 0;
    public static final int RIGHT = 1;
    public static final int LEFT = 2;
        
    /**
     * termina una fila (row) lo que es similar al LN que ya conocemos.
     */
    public void newLine();
    
    /**
     * termina el iFrame mostrando y aplicando pack y setVisible
     */
    public void finalice();
    
    /**
     * -- Add a component to a Matrix of Object --
     * la misma es sólamente para ser usada en un posible NEWLINE.
     * (recordemos que NEWLINE: alineamiento horizontal de una fila).
     * @param component : Componente a agregar a la matriz de elementos
     * @param width : El ancho del elemento.
     * @param height : El largo del elemento.
     * @param Position: CENTER, RIGHT, LEFT
     * @return 
     */
    public Component AddObject(Component component, int width, int height, int Position);    
    
    /**
     * -- Add a component to a Matrix of Object --
     * la misma es sólamente para ser usada en un posible NEWLINE.
     * Por defecto este método tiene la posición CENTER.
     * (recordemos que NEWLINE: alineamiento horizontal de una fila).
     * @param component : Componente a agregar a la matriz de elementos
     * @param width : El ancho del elemento.
     * @param height : El largo del elemento.
     * @return 
     */    
    public Component AddObject(Component component, int width, int height);

    /**
     * Crea y adhiere un componente al contenedor
     * Por defecto este método tiene la posición CENTER.
     * @param component : Componente a agregar a la matriz de elementos
     * @param width : El ancho del elemento.
     * @param height : El largo del elemento.
     * @param Position: CENTER RIGHT LEFT, O cordenada X (horizontal)
     * @return 
     */        
    public Component AddSingleObject(Component component, int width, int height, int Position);
    
    /**
     * retorna el objeto creado
     * @return
     */
    public Object[][] getObject();
    
    /**
     * Refactoriza el Objecto previamente creado para que no quede en memoria.
     */
    public void deleteObject();
    
    /**
     * createMatteBorder: Syntaxis
     * @param top px
     * @param left px
     * @param bottom px
     * @param right px
     * @param c: color of the component
     */
    public void border(int top, int left, int bottom, int right, Color c);

    /**
     * Crea un espacio entre componentes.
     * @param height
     */
    public void addSpace(int height);
    
    /**
    * elimina todo y resetea las variables
    **/
    public void dispose();
    
    public iBackground setBackgroundImage(String dir);
}