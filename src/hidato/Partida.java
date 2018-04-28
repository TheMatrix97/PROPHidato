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
        bot.resolHidato(this.solucio);
        //generar solucio
    }
    public Partida(){ //partida sense tauler
        this.encurs = null;
        this.solucio = null;
        this.bot = new Maquina();
        this.jug = new ArrayList<>();
    }

    public void generar_partida_random(Configuracio conf){
        this.conf = conf;
        this.encurs = new Tauler(conf);
        this.solucio = new Tauler(encurs);
        bot.resolHidato(this.solucio);

    }
    public void fesJugadaIns(int i, int j, int num) throws Utils.ExceptionJugadaNoValida, Utils.ExceptionTaulerResolt {
        Celda c = null;
        try {
            c = encurs.getCelda(i,j);
            if(c.isValida() && c.isVacia()) encurs.addUsat(num);
        } catch (Utils.ExceptionPosicioNoValida e) {
            throw new Utils.ExceptionJugadaNoValida();
        }
        Jugada jug = new Jugada(c,num);
        this.jug.add(jug);
        if(encurs.validador_tauler()) throw new Utils.ExceptionTaulerResolt();
    }
    public void fesJugadaDel(int i, int j) throws Utils.ExceptionJugadaNoValida{
        Celda c = null;
        try{
            c = encurs.getCelda(i,j);
            if(!c.isVacia() && c.isValida()) encurs.delUsat(c.getValor());
        }catch (Utils.ExceptionPosicioNoValida e){
            throw new Utils.ExceptionJugadaNoValida();
        }
        Jugada jug = new Jugada(c);
        this.jug.add(jug);
    }

    public Configuracio getConf(){
        return this.conf;
    }
    public Tauler getTauler(){
        return this.encurs;
    }
    public Tauler getTaulerSolucio(){
        return this.solucio;
    }
    public Maquina getBot(){
        return this.bot;
    }

}
