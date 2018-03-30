package hidato;

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

    public void resolHidato(Celda[][] t, SortedSet<Integer> pref, int max) throws Exception { //TODO en vez de usar Celda[][] usar Tauler?
        int ini = pref.first();
        if(ini == max) return;
        pref.remove(ini);
        ArrayList<Vector<Celda>> camins = TrobaCaminsValids(ini,pref.first(),t);
        if(camins.size() == 0) return; //hay que deshacer ultimo movimiento, TODO hay que crear jugadas
        for(Vector<Celda> cami : camins){
            int cont = ini;
            for(Celda pas : cami){
                if(!pas.isPrefijada()) pas.setValor(++cont);
            }
            resolHidato(t,pref,max);
        }
    }

    public ArrayList<Vector<Celda>> TrobaCaminsValids(int inici, int fi, Celda[][] t) throws Exception {//public per fer el test
        Stack<Vector<Celda>> s = new Stack<>();
        ArrayList<Vector<Celda>> rutasValidas = new ArrayList<>();
        AbstractMap.SimpleEntry<Integer,Integer> p = BuscarN(t,inici);
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
                        rutasValidas.add(newpath); //llamar recursivamente a troba camins valids???
                    } else if (auxv.size() + 1 < fi - inici + 1) {
                        newpath.add(vei);
                        s.push(newpath);
                    }
                }
            }
        }
        //TODO limpiar caminos no validos, mirando vecinos prefijados, no se si merece la pena
       /* ArrayList<Vector<Celda>> rutasValidadas = new ArrayList<>();
        for(Vector<Celda> cami: rutasValidas){
            int contador = 0;
            for(Celda c : cami){
                ArrayList<Celda> veins = c.getVecinos();
                int anterior = contador+inici+1;
                int seguent = contador+inici-1;
                boolean ant = false;
                boolean seg = false;
                for(Celda vei : veins){
                    if(vei.isValida() && !vei.isVacia() && vei.getValor() != anterior){
                        ant = true; //hay fallo
                        break;
                    }
                    else if(vei.isValida() && !vei.isVacia() && vei.getValor() != seguent){
                        seg = true;
                        break;
                    }
                }
                if(!ant && !seg) rutasValidadas.add(cami);
                contador++;
            }
        }*/
        return rutasValidas;
    }

    private AbstractMap.SimpleEntry<Integer, Integer> BuscarN(Celda[][] c, int n) throws Exception {
        for(int i = 0; i < c.length; ++i){
            for(int j = 0; j < c[i].length; ++j){
                if(c[i][j].isPrefijada() && c[i][j].getValor() == n) return new AbstractMap.SimpleEntry<>(i, j);
            }
        }
        throw new Exception("Celda prefixada not found");
    }



    private AbstractMap.SimpleEntry<Integer, Integer> BuscarCelda(Celda c, Celda[][] t) throws Exception {
        for(int i = 0; i < t.length; ++i){
            for(int j = 0; j < t[i].length; ++j){
                if(c.equals(t[i][j])) return new AbstractMap.SimpleEntry<>(i,j);
            }
        }
        throw new Exception("Celda not found");
    }
}
