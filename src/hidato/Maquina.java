package hidato;

import javafx.util.Pair;

import java.util.*;

/**
 *
 * @author marc.catrisse & lluis.marques
 */
public class Maquina extends Jugador{
    
    public Maquina()
    {
        super("nom:reservat:maquina");
    }
    //TODO Algorismes resolucio de hidato automatitzats

    public void resolHidato(Tauler t) throws Exception {
        //Només tindrem el tauler t, inicialitzar amb #, *, numeros i ?, llest per resoldre
        Celda[][] ta = t.getTauler(); //c es una copia de l'objecte del tauler a resoldre
        Pair<Integer, Integer> p = BuscarN(ta, 1);
        boolean[][] tB = new boolean[ta.length][ta[0].length]; //matriu de posicions visitades
        SortedSet<Integer> ss = t.getPrefixats();
    }



    public ArrayList<Vector<Celda>> TrobaCaminsValids(int inici, int fi, Celda[][] t) throws Exception {//public per fer el test
        Stack<Vector<Celda>> s = new Stack<>();
        ArrayList<Vector<Celda>> rutasValidas = new ArrayList<>();
        Pair<Integer,Integer> p = BuscarN(t,inici);
        Vector<Celda> v = new Vector<>();
        v.add(t[p.getKey()][p.getValue()]);
        s.push(v);
        while(!s.empty()){
            Vector<Celda> auxv = s.pop();
            Celda node = auxv.lastElement();
            ArrayList<Celda> veins = node.getVecinos();
            for(Celda vei: veins){
                if(!auxv.contains(vei) && vei.isValida() && (vei.isVacia() || vei.getValor() == fi)) {
                    Vector<Celda> newpath = new Vector<>(auxv);
                    if (vei.getValor() == fi && vei.isPrefijada() && auxv.size() + 1 == fi - inici + 1) {
                        newpath.add(vei);
                        rutasValidas.add(newpath);
                    } else if (auxv.size() + 1 < fi - inici + 1) {
                        newpath.add(vei);
                        s.push(newpath);
                    }
                }
            }
        }
        return rutasValidas;
    }

    private Pair<Integer, Integer> BuscarN(Celda[][] c, int n) throws Exception {
        for(int i = 0; i < c.length; ++i){
            for(int j = 0; j < c[i].length; ++j){
                if(c[i][j].isPrefijada() && c[i][j].getValor() == n) return new Pair<>(i, j);
            }
        }
        throw new Exception("Celda prefixada not found");
    }



    private Pair<Integer, Integer> BuscarCelda(Celda c, Celda[][] t) throws Exception {
        for(int i = 0; i < t.length; ++i){
            for(int j = 0; j < t[i].length; ++j){
                if(c.equals(t[i][j])) return new Pair<>(i,j);
            }
        }
        throw new Exception("Celda not found");
    }
}
