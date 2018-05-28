package iComponents;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;

/**
 *
 * @author Jostin Barrantes Ramírez
 */
public class iBackground implements Border {
     
   private BufferedImage bImagen = null;
    
    /**
     * Constructor, indicamos la imagen que queremos que se redimensione
     * @param Imagen ImageIO.read(new File(ruta imagen))
     */
    public iBackground(BufferedImage Imagen) {
        bImagen = Imagen;       
    }
     
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }
     
    @Override
    public boolean isBorderOpaque() {
        return true;
    }
    
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (bImagen != null) {
            g.drawImage(bImagen, 0, 0, width, height, null);
        }
    }

}