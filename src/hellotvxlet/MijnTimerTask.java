/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hellotvxlet;

import java.util.TimerTask;

/**
 *
 * @author student
 */
public class MijnTimerTask extends TimerTask {

    HelloTVXlet xl;
    
    public MijnTimerTask(HelloTVXlet x)
    {
        xl=x;
 
    }
    public void run() {
       xl.callback();
    }
}
