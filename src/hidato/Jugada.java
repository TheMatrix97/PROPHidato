package hidato;

public class Jugada {
    boolean insertar; //si false se vacia
    Celda mod;
    int num;

    Jugada(boolean ins, Celda m, int num){
        this.mod = m;
        this.insertar = ins;
        this.num = num;
        persistir();
    }
    void persistir(){
        if(insertar){
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
