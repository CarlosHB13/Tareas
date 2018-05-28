package iComponents;

import java.awt.Font;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;


public class iClock extends JLabel implements Runnable {

    private String dia, mes, año, hora, minutos, segundos;
    private final Calendar calendario = new GregorianCalendar();
    Thread hilo;

    /**
     * Estudiante Dennis
     * @param horizontal: position X
     * @param vertical: position Y
     * @param w: width
     * @param h: height
     */
    public iClock(int horizontal, int vertical, int w, int h) 
    {
        initClock(horizontal, vertical, w, h);
    }

    public void sleeped() 
    {
        try 
        {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(iClock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() 
    {
        Thread ct = Thread.currentThread();
        while (ct == hilo) 
        {
            actualiza();
            int mesE;
            mesE = Integer.valueOf(mes) + 1;
            setText("<html><center>" + dia + " / " + mesE + " / " + año + "<br>" + hora + ":" + minutos + ":" + segundos + "</html>");
            sleeped();
        }
    }

    public void actualiza() {

        Date fechaHoraActual = new Date();
        calendario.setTime(fechaHoraActual);

        hora = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY));
        minutos = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
        dia = calendario.get(Calendar.DATE) > 9 ? "" + calendario.get(Calendar.DATE) : "0" + calendario.get(Calendar.DATE);
        mes = calendario.get(Calendar.MONTH) > 9 ? "" + calendario.get(Calendar.MONTH) : "0" + calendario.get(Calendar.MONTH);
        año = calendario.get(Calendar.YEAR) > 9 ? "" + calendario.get(Calendar.YEAR) : "0" + calendario.get(Calendar.YEAR);
    }

    private void initClock(int x, int y, int p, int p1) {
        setBounds(x, y, p, p1);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        hilo = new Thread(this);
        hilo.start();
    }
}
