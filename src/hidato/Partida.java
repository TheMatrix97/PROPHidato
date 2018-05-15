package hidato;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;

/**
 *
 * @author marc.catrisse & lluis.marques & antonio.guilera & marc.blanca
 */

public class Partida implements Serializable{
    private Tauler encurs; //Tauler sobre el que es juga i el seu estat actual
    private Tauler solucio; //Tauler que conte la solucio del Tauler encurs
    private ArrayList<Jugada> jug; //Llista de jugades que s'han fet en el tauler encurs
    private Configuracio conf; //Tipus de celda, Tipus d'adjacencia i dificultat de la partida
    private Jugador jugador; //Jugador que esta resolent el tauler encurs
    private Time tiempo; //Indica el temps que porta el jugador en la partida encurs

    //CONSTRUCTORES
    Partida(String id, String j) throws Exception { //crea partida amb tauler de bd
        this.encurs = new Tauler(id);
        this.solucio = new Tauler(id);
        this.jug = new ArrayList<>();
        if(!Maquina.resolHidato(this.solucio)){
            throw new Exception();
        }
        int n = this.encurs.getN();
        int k = this.encurs.getK();
        char tcela = this.encurs.getTauler()[0][0].getForma();
        String s = this.encurs.getTauler()[0][0].getAdj();
        this.conf = new Configuracio(s, tcela, n, k);
        this.tiempo = new Time();
        this.jugador = new Jugador(j);
        this.tiempo.start_time();

    }

    //TODO assegurar caso generar partida haga timeout
    //Crea una partida sense tauler
    public Partida(String j){
        this.encurs = null;
        this.solucio = null;
        this.jug = new ArrayList<>();
        this.tiempo = new Time();
        this.jugador = new Jugador(j);
        this.tiempo.start_time();
    }

    //Crea un tauler en base a la configuració donada. La nostra màquina el resol per a poder donar pistes si el jugador ho demana.
    public void generar_partida_random(Configuracio conf){
        this.conf = conf;
        this.encurs = new Tauler(conf);
        this.solucio = new Tauler(encurs);
        Maquina.resolHidato(this.solucio);
    }

    //Jugada insertar d'un jugador, sobre una posicio i,j del tauler
    public void fesJugadaIns(int i, int j, int num) throws Utils.ExceptionJugadaNoValida, Utils.ExceptionTaulerResolt {
        Celda c;
        try {
            c = encurs.getCelda(i,j);
            if(c.isValida() && (c.isVacia() || !c.isPrefijada())) encurs.addUsat(num);
        } catch (Utils.ExceptionPosicioNoValida e) {
            throw new Utils.ExceptionJugadaNoValida();
        }
        Jugada jug = new Jugada(c,num,this.jugador);
        this.jug.add(jug);
        if(encurs.validador_tauler()){
            this.tiempo.stop_time();
            throw new Utils.ExceptionTaulerResolt();
        }
    }

    //Jugada esborrar d'un jugador, sobre una posició i,j del tauler
    public void fesJugadaDel(int i, int j) throws Utils.ExceptionJugadaNoValida{
        Celda c;
        try{
            c = encurs.getCelda(i,j);
            if(!c.isVacia() && c.isValida()) encurs.delUsat(c.getValor());
        }catch (Utils.ExceptionPosicioNoValida e){
            throw new Utils.ExceptionJugadaNoValida();
        }
        Jugada jug = new Jugada(c,this.jugador);
        this.jug.add(jug);
    }

    //Retorna el valor de la proxima cel·la sense resoldre del tauler, en la posició correcta segons la solució obtinguda per la nostra màquina
    public void pedirAyuda() throws Utils.ExceptionTaulerResolt {
        boolean[] usados = this.encurs.getUsats();
        for(int i = 1; i < usados.length; ++i){
                if(!usados[i]){
                    try {
                        AbstractMap.SimpleEntry<Integer,Integer> aux = this.solucio.buscarCeldaPerValor(i);
                        fesJugadaIns(aux.getKey(),aux.getValue(),i);
                    }
                    catch (Utils.ExceptionCeldaNotFound | Utils.ExceptionJugadaNoValida  e){
                        return;
                    }
                    break;
                }
        }
    }

    //Retorna la configuració de la partida
    public Configuracio getConf(){
        return this.conf;
    }
    //Retorna el tauler de la partida en curs.
    public Tauler getTauler(){
        return this.encurs;
    }

    //retorna el tauler solució de la partida, generat per la màquina
    public Tauler getTaulerSolucio(){
        return this.solucio;
    }

    //retorna el temps de la partida.
    public Time getTiempo(){
        return this.tiempo;
    }

    //retorna el nom del jugador
    public Jugador getJugador(){
        return this.jugador;
    }

}
