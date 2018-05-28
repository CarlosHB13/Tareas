/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz1;

import iComponents.iFrame;
import iComponents.iPanel;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author Carlos
 */
public class Debugger
{
    JTextArea text = new JTextArea();

    public Debugger()

    {

        iFrame if_ = new iFrame(80.0f, 80.0f, 0, 0, "PRIMER PANEL", JFrame.EXIT_ON_CLOSE);
        if_.setHeaderBackground(Color.BLACK);
        
        iPanel Main = new iPanel(250, 10, 100.0f, 100.0f, 4, 34, if_);
        Main.setResponsiveWidth(100.0f, 250);
        Main.setBackground(Color.BLUE);
        
        Main.AddSingleObject(text, 100.0F, 200, 0);

        

        if_.finalice();
              
    }
    
    public void log(String txt)
    {
    
        text.append(txt);
    
    }
    
    
}

