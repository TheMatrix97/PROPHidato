package hidato;

public class Time {
    private long time_end;
    private long time_start;
    private long time;

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
        time_end = System.currentTimeMillis();
        time += time_end-time_start;
    }
    public void resume_time(){
        time_start = System.currentTimeMillis();
    }
    public String get_time(){
        int segundos = (int)(time/1000);
        int minutos = segundos / 60;
        int horas = minutos/60;
        return String.format("%02d",horas)+':'+String.format("%02d",minutos%60)+':'+String.format("%02d",segundos%60);
    }
    public long get_time_millis(){
        return time;
    }
}
