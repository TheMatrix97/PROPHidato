package hidato;

import java.io.Serializable;

/**
 *
 * @author Antonio.Guilera & Marc.Blanca
 */
public class Configuracio implements Serializable{

    private String dificultat; //Facil, Normal, Dificil
    private String tadjacencia; //C:costats,CA:costats+angles
    private char tcelda; //Q:quadrat,H:hexàgon,T:triangle

    //constructores
    public Configuracio(String dif, String tadj, char tcelda){
        this.dificultat = dif;
        this.tadjacencia = tadj;
        this.tcelda = tcelda;
    }

    public Configuracio(String tadj, char tcelda, int last) throws Exception {
        this.dificultat = calcula_Dificultat(last);
        this.tadjacencia = tadj;
        this.tcelda = tcelda;
    }

    //S'utilitza per calcular la dificultat d'un hidato de la BD, per poder afegir els records al ranking que toca
    private String calcula_Dificultat(int last) throws Exception {
        if (last < 1) throw new  Exception("Tamany no valid.");
        if (last < 30) return "Facil";
        else if (last < 100) return "Normal";
        else return "Dificil";
    }

    //Getters
    public String getDificultat(){
        return dificultat;
    }

    public String getAdjacencia(){
        return tadjacencia; //C:costats,CA:costats+angles
    }

    public char getcell(){
        return tcelda; //Q:quadrat,H:hexàgon,T:triangle
    }

    @Override
    public boolean equals(Object c) {
        return c != null && c instanceof Configuracio
                && this.dificultat.equals(((Configuracio) c).getDificultat())
                && this.tcelda == ((Configuracio) c).getcell()
                && this.tadjacencia.equals(((Configuracio) c).getAdjacencia());
    }
}

