package hidato;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeTest {
    //Tests que verifiquen la class Time
    @Test
    public void get_time() {
        Time time = new Time();
        time.start_time();
        sleep(1000);
        time.stop_time();
        assertEquals("00:00:01",time.get_time());
    }

    @Test
    public void resume_time() { //Test de start_time / pause_time / resume_time / stop_time, en total deixa passar 2 segons i es verifica al final
        Time time = new Time();
        time.start_time();
        sleep(1000);
        time.pause_time();
        sleep(500);
        time.resume_time();
        sleep(1000);
        time.stop_time();
        System.out.println("millis contados: " + time.get_time_millis() + "\nhe jugado: 2000");
        assertEquals("00:00:02",time.get_time());
    }
    private void sleep(int time){
        try{
            Thread.sleep(time);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
}