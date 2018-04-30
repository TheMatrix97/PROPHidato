package hidato;

import java.io.Serializable;

/**
 *  @author lluis.marques */
public class Record implements Serializable{
    private Time time;  //el temps que ha trigat un jugador en resoldre un Hidato
    private String nomJugador; //el nom del jugador que ha resolt dit Hidato

    //Creadora. Requereix el nom del jugador i el temps que ha trigat en completar el hidato.
    Record(Time tim3, String nomJ){
        this.time = tim3;
        this.nomJugador = nomJ;
    }

    //retorna el temps del record
    public Time getTime(){
        return time;
    }

    //retorna el nom del jugador del record
    public String getnomJugador(){
        return nomJugador;
    }
}
