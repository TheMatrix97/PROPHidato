package hidato;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Partida implements Serializable{
    Tauler encurs;
    Tauler solucio;
    ArrayList<Jugada> jug;
    Configuracio conf;
    Maquina bot;
    public Partida(String id) throws IOException { //crea partida amb tauler de bd
        this.encurs = new Tauler(id);
        this.solucio = new Tauler(id);
        this.bot = new Maquina();
        this.jug = new ArrayList<>();
        //generar solucio
    }
    public Partida(){ //partida sense tauler
        this.encurs = null;
        this.solucio = null;
        this.bot = new Maquina();
        this.jug = new ArrayList<>();
    }
    void Solucionar_bot(){

    }
    public Configuracio getConf(){
        return this.conf;
    }
    public Tauler getTauler(){
        return this.encurs;
    }

}
