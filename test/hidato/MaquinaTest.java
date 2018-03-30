package hidato;

import javafx.util.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.Vector;

import static org.junit.Assert.*;

public class MaquinaTest {

    @Test
    public void trobaCaminsValids() throws Exception { //PARA TESTEAR!!
        Tauler t = new Tauler("QCAEnunciat"); //Hidato sobre el que buscará
        Maquina m = new Maquina();
        ArrayList<Vector<Celda>> aux = m.TrobaCaminsValids(3, 8, t.getTauler()); //AÑADE EN INICI Y EN FI LOS PREFIJADOS.
        for (Vector<Celda> a : aux) {
            System.out.print("cami 1: ");
            for (Celda b : a) {
                Pair<Integer, Integer> p = BuscarCelda(b, t.getTauler());
                System.out.print("(" + p.getKey() + "," + p.getValue() + ")");
            }
            System.out.print("\n");
        }
    }
    @Test
    public void ResolHidato() throws Exception { //PARA TESTEAR!!
        Tauler ta = new Tauler("QCAEnunciat"); //Hidato sobre el que buscará
        Maquina m = new Maquina();
        SortedSet<Integer> pref = ta.getPrefixats();
        Celda[][] t = ta.getTauler();
        SortedSet<Integer> prefixats = ta.getPrefixats();
        m.resolHidato(t,pref,pref.last());
        for(Celda[] c : t){
            for(Celda celda : c){
                if(celda.isFrontera())System.out.print("# ");
                else if(celda.isVacia()) System.out.print("? ");
                else if(!celda.isValida()) System.out.print("* ");
                else System.out.print(celda.getValor() + " ");
            }
            System.out.print('\n');
        }
    }

    private Pair<Integer, Integer> BuscarCelda(Celda c, Celda[][] t) throws Exception {
        for (int i = 0; i < t.length; ++i) {
            for (int j = 0; j < t[i].length; ++j) {
                if (c.equals(t[i][j])) return new Pair<>(i, j);
            }
        }
        throw new Exception("Celda not found");
    }

}