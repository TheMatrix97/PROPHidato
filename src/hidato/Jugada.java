package hidato;

public class Jugada {
    boolean insertar; //si false se vacia
    Celda mod;
    int num;

    public Jugada(Celda m, int num) throws Utils.ExceptionJugadaNoValida { //constructora insertar
        this.mod = m;
        this.insertar = true;
        this.num = num;
        if(m.isValida() && m.isVacia()) {
            persistir();
        }else{
            throw new Utils.ExceptionJugadaNoValida();
        }
    }

    public Jugada(Celda m) throws Utils.ExceptionJugadaNoValida{
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
