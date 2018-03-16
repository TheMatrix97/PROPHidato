package hidato;

public class Time {
    long time_end;
    long time_start;
    long time;

    Time(){
        time_start = 0;
        time_end = 0;
        time = 0;
    }
    public void start_time(){
        time_start = System.currentTimeMillis();
    }
    public void stop_time(){
        time_end = System.currentTimeMillis();
        time += time_end-time_start;
        time_start = 0;
        time_end = 0;
    }
    public void pause_time(){
        this.time = time_end-time_start;
    }
    public void resume_time(){
        time_start = System.currentTimeMillis();
    }
    public String get_time(){
        int segundos = (int)(time/1000);
        int minutos = (int)(segundos / 60);
        int horas = (int)(minutos/60);
        return String.format("%02d",horas)+':'+String.format("%02d",minutos)+':'+String.format("%02d",segundos);
    }
}
