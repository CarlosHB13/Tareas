/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class iCalendar extends JTextField  
{

    private Shape shape;
    private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final int DIALOG_WIDTH = 263;
    private static final int DIALOG_HEIGHT = 240;

    private SimpleDateFormat dateFormat;
    private DatePanel datePanel = null;
    private JDialog dateDialog = null;
    
    private int positon;

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }

    public iCalendar() {
        this(new Date());
        setOpaque(false);
    }

    public iCalendar(String dateFormatPattern, Date date) {
        this(date);
        DEFAULT_DATE_FORMAT = dateFormatPattern;
        setOpaque(false);
        setForeground(new Color(73, 80, 87).brighter().brighter());
        setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
    }

    public iCalendar(Date date) 
    {
        setDate(date);
        setEditable(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addListeners();
        setOpaque(false);
        setForeground(new Color(73, 80, 87).brighter().brighter());
        setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
       
    }
    
    

    private void addListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent paramMouseEvent) {
                if (datePanel == null) {
                    datePanel = new DatePanel();
                }
                Point point = getLocationOnScreen();
                point.y = point.y + 20;
                showDateDialog(datePanel, point);
            }
        });
    }

    private void showDateDialog(DatePanel dateChooser, Point position) {
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(iCalendar.this);
        if (dateDialog == null || dateDialog.getOwner() != owner) {
            dateDialog = createDateDialog(owner, dateChooser);
        }
        dateDialog.setLocation(getAppropriateLocation(owner, position));
        dateDialog.setVisible(true);
    }

    private JDialog createDateDialog(Frame owner, JPanel contentPanel) {
        JDialog dialog = new JDialog(owner, "Date Selected", true);
        dialog.setUndecorated(true);
        dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        return dialog;
    }

    private Point getAppropriateLocation(Frame owner, Point position) 
    {
        Point result = new Point(position);
        Point p = owner.getLocation();
        int offsetX = (position.x + DIALOG_WIDTH) - (p.x + owner.getWidth());
        int offsetY = (position.y + DIALOG_HEIGHT) - (p.y + owner.getHeight());

        if (offsetX > 0) {
            result.x -= offsetX;
        }

        if (offsetY > 0) {
            result.y -= offsetY;
        }

        return result;
    }

    private SimpleDateFormat getDefaultDateFormat() {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        }
        return dateFormat;
    }

    public void setText(Date date) {
        setDate(date);
    }

    public void setDate(Date date) {
        super.setText(getDefaultDateFormat().format(date));
    }

    public Date getDate() {
        try {
            return getDefaultDateFormat().parse(getText());
        } catch (ParseException e) {
            return new Date();
        }
    }
    
    @Override
    public void paint(Graphics g) 
    {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
        g2d.setColor(Color.white);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);

        g2d.setColor(new Color(206,212,218));
        g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 4, 4);
  
         if (shape == null || !shape.getBounds().equals(getBounds())) 
         {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 4, 4);
         }
         
        // Draw the text in the center
        g2d.setColor(new Color(73, 80, 87));        
        super.paintComponent(g2d);
    }

    private class DatePanel extends JPanel implements ChangeListener 
    {
        int startYear = 2018;
        int lastYear = 2100;

        Color backGroundColor = Color.white;
        Color palletTableColor = Color.white;
        Color todayBackColor = Color.orange;
        Color weekFontColor = Color.black;
        Color dateFontColor = Color.black;
        Color weekendFontColor = new Color(0xda4e44);

        Color controlLineColor = new Color(73, 80, 87);
        Color controlTextColor = Color.white;

        JSpinner yearSpin;
        JButton[][] daysButton = new JButton[6][7];

        DatePanel() 
        {
            setLayout(new BorderLayout());
            setBorder(new LineBorder(new Color(0xeeeeee), 1));
            setBackground(Color.white);

            JPanel topYearAndMonth = createYearAndMonthPanal();
            add(topYearAndMonth, BorderLayout.NORTH);
            JPanel centerWeekAndDay = createWeekAndDayPanal();
            add(centerWeekAndDay, BorderLayout.CENTER);

            dayColorReset();
        }

        public String getCalendarName(int Month) 
        {
            switch (Month) 
            {
                case 1:          return "January";
                case 2:          return "February";
                case 3:          return "March";
                case 4:          return "April";
                case 5:          return "May";
                case 6:          return "June";
                case 7:          return "July";
                case 8:          return "August";
                case 9:          return "September";
                case 10:         return "October";
                case 11:         return "November";
                case 12:         return "Dicember";
            }
            return "January";
        }
        
        
        public void monthSpin(JLabel lblDinamicMonth, Calendar cal) 
        {
            String m = ""; // proximo mes
            int i = 0;
                     
            switch (lblDinamicMonth.getText().toLowerCase()) 
            {
                case "dicember":        m = "january";      i = 1;      break;
                case "january":         m = "February";     i = 2;      break;
                case "february":        m = "March";        i = 3;      break;
                case "march":           m = "April";        i = 4;      break;
                case "april":           m = "May";          i = 5;      break;
                case "may":             m = "June";         i = 6;      break;
                case "june":            m = "July";         i = 7;      break;
                case "july":            m = "August";       i = 8;      break;
                case "august":          m = "September";    i = 9;      break;
                case "september":       m = "October";      i = 10;     break;
                case "october":         m = "November";     i = 11;     break;
                case "november":        m = "Dicember";     i = 12;     break;
            }        
            lblDinamicMonth.setText(m);
            
            // setea el mes y el día 1 al refrescar.
            cal.set(Calendar.MONTH, i - 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            setDate(cal.getTime());
            
            dayColorReset();
            dayColorUpdate(true);        
        }
        
        private JPanel createYearAndMonthPanal() 
        {
            Calendar cal = getCalendar();
            int currentYear = cal.get(Calendar.YEAR);
            int currentMonth = cal.get(Calendar.MONTH) + 1;

            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            panel.setBackground(Color.WHITE);
            panel.setForeground(Color.BLACK);
            panel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            
            JLabel lblDinamicMonth = new JLabel(getCalendarName(currentMonth));
            
            lblDinamicMonth.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) 
                {
                    monthSpin(lblDinamicMonth, cal);
                }
                
            });
            
            yearSpin = new JSpinner(new SpinnerNumberModel(currentYear, startYear, lastYear, 1));
            yearSpin.setPreferredSize(new Dimension(56, 20));
            yearSpin.setName("Year");
            yearSpin.setEditor(new JSpinner.NumberEditor(yearSpin, "####"));
            yearSpin.addChangeListener(this);
            panel.add(yearSpin);

            JLabel yearLabel = new JLabel("Year");
            yearLabel.setForeground(Color.BLACK);
            panel.add(yearLabel);

            panel.add(lblDinamicMonth);

            return panel;
        }

        private JPanel createWeekAndDayPanal() 
        {
            
            String colname[] = { "dom.", "lun.", "mar.", "mie.", "jue.", "vie.", "sáb." };
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(7, 7));
            panel.setBackground(Color.white);
            panel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            for (int i = 0; i < 7; i++) 
            {
                JLabel cell = new JLabel(colname[i]);
                cell.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                cell.setHorizontalAlignment(JLabel.CENTER);
                cell.setForeground(new Color(0x495057));
                panel.add(cell);
            }

            int actionCommandId = 0;
            // vertical
            for (int i = 0; i < 6; i++)
            {
                // Horizontal
                for (int j = 0; j < 7; j++) 
                {
                    JButton numBtn = new JButton();
                    numBtn.setBorder(null);
                    numBtn.setHorizontalAlignment(SwingConstants.CENTER);
                    numBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                    
                    numBtn.setActionCommand(String.valueOf(actionCommandId));

                    numBtn.addActionListener((ActionEvent event) -> {
                        JButton source = (JButton) event.getSource();
                        int x = Integer.parseInt(numBtn.getActionCommand()) / 7;
                        if (
                                source.getText().length() == 0 || 
                                (Integer.parseInt(source.getText()) < 10 && x > 1) 
                            ) 
                            return;
                        
                        dayColorReset();
                        
                        // debo hacer el proceso para ver si 
                        // el numero marcado es de otra fecha.
                        
                        source.setBackground(new Color(0x337ab7));
                        source.setForeground(Color.white);
                        
                        int newDay = Integer.parseInt(source.getText());
                        Calendar cal = getCalendar();
                        cal.set(Calendar.DAY_OF_MONTH, newDay);
                        setDate(cal.getTime());
                        
                        dateDialog.setVisible(false);
                    });
                    
                    daysButton[i][j] = numBtn;
                    panel.add(numBtn);
                    actionCommandId++;
                }
            }

            return panel;
        }

        private Calendar getCalendar() 
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getDate());
            return calendar;
        }

        private int getSelectedYear() 
        {
            return ((Integer) yearSpin.getValue());
        }
        
        private void dayColorReset() 
        {
            Calendar cal = getCalendar();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            
            // 1 mes menos del calendario actual
            Calendar calBefore = getCalendar();
            calBefore.set(Calendar.DAY_OF_MONTH, -1);           

            int maxDayNo = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int maxDayNoBefore = calBefore.getActualMaximum(Calendar.DAY_OF_MONTH); 
            
            int dayNo = 2 - cal.get(Calendar.DAY_OF_WEEK);
            for (int i = 0; i < 6; i++) 
            {
                for (int j = 0; j < 7; j++) 
                {
                    String s = "";
                    
                    if (dayNo >= 1 && dayNo <= maxDayNo)
                    {
                        s = String.valueOf(dayNo);
                        if (j == 0 || j == 6)
                            daysButton[i][j].setForeground(weekendFontColor);
                        else
                            daysButton[i][j].setForeground(weekFontColor);
                    }
                    else if (dayNo < 0)
                    {
                        s = String.valueOf(maxDayNoBefore + dayNo);
                        daysButton[i][j].setForeground(weekendFontColor);
                    }
                    else if (dayNo > maxDayNo) 
                    {
                        s = String.valueOf(dayNo - maxDayNo);
                        daysButton[i][j].setForeground(weekendFontColor);
                    }
                    else if (dayNo == 0)
                    {
                        s = String.valueOf(maxDayNoBefore);
                        daysButton[i][j].setForeground(weekendFontColor);
                    }
                    
                    daysButton[i][j].setText(s);
                    daysButton[i][j].setBackground(Color.WHITE);
                    dayNo++;
                }
            }
        }

        private void dayColorUpdate(boolean isOldDay) 
        {
            Calendar cal = getCalendar();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            int actionCommandId = day - 2 + cal.get(Calendar.DAY_OF_WEEK);
            int i = actionCommandId / 7;
            int j = actionCommandId % 7;
            if (isOldDay) 
            {
                daysButton[i][j].setBackground(Color.white);
                daysButton[i][j].setForeground(Color.black);
            } 
            else 
            {
                daysButton[i][j].setBackground(new Color(0x337ab7));
                daysButton[i][j].setForeground(Color.white);
            }
        }

        @Override
        public void stateChanged(ChangeEvent e) 
        {
            dayColorUpdate(true);

            JSpinner source = (JSpinner) e.getSource();
            Calendar cal = getCalendar();
            if (source.getName().equals("Year"))
                cal.set(Calendar.YEAR, getSelectedYear());

            setDate(cal.getTime());
            dayColorReset();
        }
    }
}