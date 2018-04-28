package hidato;

import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;

public class Partida implements Serializable{
    private Tauler encurs;
    private Tauler solucio;
    private ArrayList<Jugada> jug;
    private Configuracio conf;
    private Jugador jugador;

    public Partida(String id) throws Exception { //crea partida amb tauler de bd
        this.encurs = new Tauler(id);
        this.solucio = new Tauler(id);
        this.jug = new ArrayList<>();
        Maquina.resolHidato(this.solucio);
        int n = this.encurs.getN();
        int k = this.encurs.getK();
        char tcela = this.encurs.getTauler()[0][0].getForma();
        String s = this.encurs.getTauler()[0][0].getAdj();
        this.conf = new Configuracio(s, tcela, n, k);

    }
    //TODO assegurar caso generar partida haga timeout
    public Partida(){ //partida sense tauler
        this.encurs = null;
        this.solucio = null;
        this.jug = new ArrayList<>();
    }

    public void generar_partida_random(Configuracio conf){
        this.conf = conf;
        this.encurs = new Tauler(conf);
        this.solucio = new Tauler(encurs);
        Maquina.resolHidato(this.solucio);

    }
    public void fesJugadaIns(int i, int j, int num) throws Utils.ExceptionJugadaNoValida, Utils.ExceptionTaulerResolt { //ho fa un jugador
        Celda c = null;
        try {
            c = encurs.getCelda(i,j);
            if(c.isValida() && c.isVacia()) encurs.addUsat(num);
        } catch (Utils.ExceptionPosicioNoValida e) {
            throw new Utils.ExceptionJugadaNoValida();
        }
        Jugada jug = new Jugada(c,num,this.jugador);
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
        Jugada jug = new Jugada(c,this.jugador);
        this.jug.add(jug);
    }

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




    public Configuracio getConf(){
        return this.conf;
    }
    public Tauler getTauler(){
        return this.encurs;
    }
    public Tauler getTaulerSolucio(){
        return this.solucio;
    }

}
