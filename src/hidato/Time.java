package hidato;

import java.io.Serializable;

/**
 * author Marc.Catrisse
 */
//Class cronòmetre per calcular el temps de partida.
public class Time implements Serializable{
    private long time_end;
    private long time_start;
    private long time;
    private int penalitzacio; //en seg

    public Time(){
        time_start = 0;
        time_end = 0;
        time = 0;
        penalitzacio = 0;
    }
    public Time(long t){
        this.time = t;
    }
    public void start_time(){
        time_start = System.currentTimeMillis();
    }
    public void stop_time(){
        time_end = System.currentTimeMillis();
        time += time_end-time_start;
        time_start = 0;
        time_end = 0;
        penalitzacio = 0;
    }
    public void pause_time(){
        time_end = System.currentTimeMillis();
        time += time_end-time_start;
    }
    public void resume_time(){
        time_start = System.currentTimeMillis();
    }

    public String get_time(){
        int segundos = (int)(time/1000) + penalitzacio;
        int minutos = segundos / 60;
        int horas = minutos/60;
        System.out.println("Time => " + horas + ":" + minutos + ":" + segundos);
        return String.format("%02d",horas)+':'+String.format("%02d",minutos%60)+':'+String.format("%02d",segundos%60);
    }
    public void add_penalitzacio(){
        penalitzacio += 10;
    }

    public void actualitzaTime(){
        time = System.currentTimeMillis() - time_start;
    }

    //Si el temps t - temps d'inici es superior a 20 retorna true, (timeout)
    public boolean checkTime(long t){
        return ((int) ((t-time_start)/1000)) >= 20;
    }
    public long get_time_millis(){
        return time + penalitzacio*1000;
    }
}
