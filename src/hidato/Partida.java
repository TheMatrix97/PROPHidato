package hidato;

import java.io.IOException;
import java.util.ArrayList;

public class Partida {
    Tauler encurs;
    Tauler solucio;
    ArrayList<Jugada> jug;
    Configuracio conf;
    Maquina bot;
    Partida(String id) throws IOException { //crea partida amb tauler de bd
        this.encurs = new Tauler(id);
        this.solucio = new Tauler(id);
        this.bot = new Maquina();
        this.jug = new ArrayList<>();
        //generar solucio
    }
    void Solucionar_bot(){

    }
    public Configuracio getConf(){
        return this.conf;
    }

}
