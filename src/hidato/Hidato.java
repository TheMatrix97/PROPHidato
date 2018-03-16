/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidato;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author marc.catrisse
 */
public class Hidato {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("hello world");
        Time time = new Time();
        time.start_time();
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    
        time.stop_time();
        System.out.println(time.get_time());
    }
    
}
