package hidato;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.SortedSet;

/*@author lluis.marques*/
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
        ss.remove(ss.first());
        dfsRec(ta, p, tB, ss);
    }

    private Pair<Integer, Integer> BuscarN(Celda[][] c, int n) throws Exception {
        for(int i = 0; i < c.length; ++i){
            for(int j = 0; j < c[i].length; ++j){
                if(c[i][j].isPrefijada() && c[i][j].getValor() == n) return new Pair<>(i, j);
            }
        }
        throw new Exception("Celda prefixada not found");
    }

    private void dfsRec(Celda[][] t, Pair<Integer, Integer> p, boolean[][] tB, SortedSet<Integer> ss) throws Exception {
        int i = p.getKey();
        int j = p.getValue();
        if(!tB[i][j]){
            tB[i][j] = true;
            ArrayList<Celda> aux = t[i][j].getVecinos();
            int s = ss.first();
            ss.remove(ss.first());
            for(int c: ss){
                
            }
            ss.add(s);
        }
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
