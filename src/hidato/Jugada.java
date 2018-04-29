package hidato;

/**
 * author per determinar
 */
public class Jugada {
    private boolean insertar; //si false la cela s'ha de buidar
    private Celda mod; //celda a modificar amb la jugada
    private int num; //en cas de jugada d'inserció conté el núm a insertar
    private Jugador jg; //jugador que ha fet la jugada, per si en un futur ho utilitzem

    Jugada(Celda m, int num, Jugador j) throws Utils.ExceptionJugadaNoValida { //constructora insertar si j == null es una jugada d'una máquina
        if(j != null) this.jg = j;
        this.mod = m;
        this.insertar = true;
        this.num = num;
        if(m.isValida() && m.isVacia()) {
            persistir();
        }else{
            throw new Utils.ExceptionJugadaNoValida();
        }
    }

    Jugada(Celda m, Jugador j) throws Utils.ExceptionJugadaNoValida{
        if(j != null) this.jg = j;
        this.insertar = false;
        this.mod = m;
        if(m.isValida() && !m.isPrefijada()){
            persistir();
        }else{
            throw new Utils.ExceptionJugadaNoValida();
        }
    }

    private void persistir(){
        if(this.insertar){
            mod.setValor(num);
        }
        else{
            mod.vaciar();
        }
    }
    Celda getCelda(){
        return this.mod;
    }
    int getNum(){
        return this.num;
    }

}
