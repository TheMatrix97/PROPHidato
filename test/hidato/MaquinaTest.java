package hidato;

import javafx.util.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import static org.junit.Assert.*;

public class MaquinaTest {

    @Test
    public void testSolver1() throws Exception {
        Tauler t = new Tauler("HCHidato2"); //Hidato sobre el que buscará
        Maquina m = new Maquina();
        ArrayList<ArrayList<Jugada>> j = new ArrayList<>();
        Integer a = 0;
        SortedSet<Integer> pref = new TreeSet<>(t.getPrefixats());
        int ini = pref.first();
        pref.remove(ini);
        int seg = pref.first();
        m.resolHidato(t.getTauler(),t.getPrefixats(),t.getPrefixats().last(),j);
        System.out.println(recorreCeldas(t.getTauler()).toString());
    }

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
    public void ResolHidatoQCA() throws Exception { //PARA TESTEAR!!
        ResolHidatoGen("QCA", "Q,CA,5,5\n");
    }
    @Test
    public void ResolHidatoTC() throws Exception { //PARA TESTEAR!!
        ResolHidatoGen("TC", "T,C,5,7\n");
    }
    private void ResolHidatoGen(String type, String capcelera) throws Exception {
        Tauler ta = new Tauler(type+"Enunciat"); //Hidato sobre el que buscará
        Maquina m = new Maquina();
        SortedSet<Integer> pref = ta.getPrefixats();
        Celda[][] t = ta.getTauler();
        ArrayList<ArrayList<Jugada>> j = new ArrayList<>();
        boolean or = false;
        Integer a = 0;
        SortedSet<Integer> prefaux = new TreeSet<>(ta.getPrefixats());
        int ini = prefaux.first();
        prefaux.remove(ini);
        int seg = prefaux.first();
        m.resolHidato(t,pref,pref.last(),j);
        String res = capcelera + recorreCeldas(t).toString();
        String out = LectorTxt.llegirFile(type+"EnunciatOut");
        assertEquals(res,out);
    }
    public static StringBuilder recorreCeldas(Celda[][] t){
        StringBuilder res = new StringBuilder();
        boolean first;
        for(Celda[] c : t){
            first = true;
            for(Celda celda : c){
                if(!first) res.append(",");
                else first = false;
                if(celda.isFrontera())res.append("#");
                else if(celda.isVacia()) res.append("?");
                else if(!celda.isValida()) res.append("*");
                else res.append(celda.getValor());
            }
            res.append('\n');
        }
        return res;
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