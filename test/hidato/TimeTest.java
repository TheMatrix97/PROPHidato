package hidato;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeTest {

    @Test
    public void get_time() {
        Time time = new Time();
        time.start_time();
        sleep(1000);
        time.stop_time();
        assertEquals("00:00:01",time.get_time());
    }

    @Test
    public void resume_time() {
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