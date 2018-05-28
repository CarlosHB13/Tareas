/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.util.concurrent.Callable;
/**
 *
 * @author jorge.vasquez
 */
public class iThread<T> extends Thread {

    private final Callable function;
    private final int miliseconds;
    private boolean state;

    public boolean getstate() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    
    
    public iThread(Callable<T> func, int miliseconds) 
    {
        System.out.println("enter");
        this.function = func;
        this.miliseconds = miliseconds;
        Thread th = new Thread(this);
        th.start();
    }

    public void sleeped() {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException ex) {
        }
    }
    
    public void end() {
        setState(false);
    }
    
    @Override
    public void run() 
    {
        while (getstate()) {
            try {
                System.out.println("thread working");
                function.call();
                sleeped();
            } catch (Exception ex) {
                setState(false);
                System.out.println(ex.getMessage());
            }
        }
    }
    
    
}
