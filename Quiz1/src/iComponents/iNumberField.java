/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.awt.event.KeyEvent;

public class iNumberField extends iTextField {

    public iNumberField(String hoverText, int borderRadius) {
        super(hoverText, borderRadius);
    }

    @Override
    public void processKeyEvent(KeyEvent ev) {
        if (Character.isDigit(ev.getKeyChar()) || ev.getKeyCode() == 8 || ev.getKeyCode() == 17 || ev.getKeyCode() == 86) {
            super.processKeyEvent(ev);
        }
        ev.consume();
    }

    /**
     * As the user is not even able to enter a dot ("."), only integers (whole
     * numbers) may be entered.
     *
     * @return
     */
    public Long getNumber() {
        Long result = null;
        String text = getText();
        if (text != null && !"".equals(text)) {
            result = Long.valueOf(text);
        }
        return result;
    }
}
