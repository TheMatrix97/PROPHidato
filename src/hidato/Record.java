package hidato;

/* @author lluis.marques */
public class Record{
    private Time time; 
    private String nomJugador;
    
    Record(Time tim3, String nomJ){
        this.time = tim3;
        this.nomJugador = nomJ;
    }
    
    public Time getTime(){ //TODO hace falta clone()?
        return time;
    }

    public String getnomJugador(){
        return nomJugador;
    }
}
