package hidato;

public class Jugada {
    boolean insertar; //si false se vacia
    Celda mod;
    int num;
    Jugador jg;

    public Jugada(Celda m, int num, Jugador j) throws Utils.ExceptionJugadaNoValida { //constructora insertar si j == null es una jugada d'una máquina
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

    public Jugada(Celda m, Jugador j) throws Utils.ExceptionJugadaNoValida{
        if(j != null) this.jg = j;
        this.insertar = false;
        this.mod = m;
        if(m.isValida() && !m.isPrefijada()){
            persistir();
        }else{
            throw new Utils.ExceptionJugadaNoValida();
        }
    }

    void persistir(){
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
