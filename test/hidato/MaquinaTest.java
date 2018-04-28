package hidato;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import static org.junit.Assert.*;

public class MaquinaTest {

    @Test
    public void TestRandomHCA() throws Exception {
        Tauler t = new Tauler("HCAHidato"); //Hidato sobre el que buscará
        Maquina m = new Maquina();
        if (m.resolHidato(t)) {
            System.out.println(recorreCeldas(t.getTauler()).toString());
            String res = "H,CA,11,14\n" + recorreCeldas(t.getTauler()).toString();
            String out = LectorTxt.llegirFile("HCAHidatoOut");
            assertEquals(out,res);
        } else assert (false);
    }
    @Test
    public void TestRandomQCA() throws Exception {
        Tauler t = new Tauler("QCAHidato"); //Hidato sobre el que buscará
        Maquina m = new Maquina();
        if (m.resolHidato(t)) {
            System.out.println(recorreCeldas(t.getTauler()).toString());
            String res = "Q,CA,10,11\n" + recorreCeldas(t.getTauler()).toString();
            String out = LectorTxt.llegirFile("QCAHidatoOut");
            assertEquals(out,res);
        } else assert (false);
    }
    @Test
    public void TestRandomTCA() throws Exception {
        Tauler t = new Tauler("TCAHidato"); //Hidato sobre el que buscará
        Maquina m = new Maquina();
        if (m.resolHidato(t)) {
            System.out.println(recorreCeldas(t.getTauler()).toString());
            String res = "T,CA,10,12\n" + recorreCeldas(t.getTauler()).toString();
            String out = LectorTxt.llegirFile("TCAHidatoOut");
            assertEquals(out,res);
        } else assert (false);
    }
    @Test
    public void TestRandomQC() throws Exception {
        Tauler t = new Tauler("QCHidato"); //Hidato sobre el que buscará
        Maquina m = new Maquina();
        if (m.resolHidato(t)) {
            System.out.println(recorreCeldas(t.getTauler()).toString());
            String res = "Q,C,9,12\n" + recorreCeldas(t.getTauler()).toString();
            String out = LectorTxt.llegirFile("QCHidatoOut");
            assertEquals(out,res);
        } else assert (false);
    }
    @Test
    public void TestRandomTC() throws Exception {
        Tauler t = new Tauler("TCHidato"); //Hidato sobre el que buscará
        Maquina m = new Maquina();
        if (m.resolHidato(t)) {
            System.out.println(recorreCeldas(t.getTauler()).toString());
            String res = "T,C,11,11\n" + recorreCeldas(t.getTauler()).toString();
            String out = LectorTxt.llegirFile("TCHidatoOut");
            assertEquals(out,res);
        } else assert (false);
    }


    @Test
    public void TrobaCaminsValids() throws Exception { //PARA TESTEAR!!
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
    public void ResolHidatoEnunciatQCA() throws Exception { //PARA TESTEAR!!
        ResolHidatoGen("QCA", "Q,CA,5,5\n", "Enunciat");
    }
    @Test
    public void ResolHidatoEnunciatTC() throws Exception { //PARA TESTEAR!!
        ResolHidatoGen("TC", "T,C,5,7\n","Enunciat");
    }
    @Test
    public void ResolHidatoEnunciatHC() throws Exception { //No te solució!
        ResolHidatoGen("HC", "H,C,3,4\n","Enunciat");
    }
    private void ResolHidatoGen(String type, String capcelera, String next ) throws Exception {
        Tauler t = new Tauler(type+next); //Hidato sobre el que buscará
        Maquina m = new Maquina();
        if (m.resolHidato(t)) {
            System.out.println(recorreCeldas(t.getTauler()).toString());
            String res = capcelera + recorreCeldas(t.getTauler()).toString();
            String out = LectorTxt.llegirFile(type+"EnunciatOut");
            assertEquals(res,out);
        } else{
            System.out.println(recorreCeldas(t.getTauler()).toString());
            if((type+next).equals("HCEnunciat")) assert (true);
            else assert (false);
        }
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
                else if(!celda.isValida()) res.append("*");
                else if(celda.isVacia() && celda.isValida()) res.append("?");
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