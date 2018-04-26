package hidato;

import java.io.Serializable;
import java.net.CacheRequest;
import java.util.*;

/**
 *
 * @author marc.catrisse & lluis.marques
 */
public class Maquina extends Jugador implements Serializable{
    
    public Maquina()
    {
        super("nom:reservat:maquina");
    }
    //TODO Eliminar chivatos

    public boolean resolHidato(Tauler t){
        SortedSet<Integer> pref = t.getPrefixats();
        ArrayList<ArrayList<Jugada>> jugades = new ArrayList<>();
        try {
            resolHidatoAlgorito(t.getTauler(),pref,pref.last(),jugades);
        }catch(Utils.ExceptionHidatoSolucionat e){
            return true;
        }catch(Utils.ExceptionHidatoNoSol e){
            return false;
        }
        return false;
    }

    private void resolHidatoAlgorito(Celda[][] t, SortedSet<Integer> pref, int max, ArrayList<ArrayList<Jugada>> jugades) throws Utils.ExceptionHidatoNoSol, Utils.ExceptionHidatoSolucionat { //TODO en vez de usar Celda[][] usar Tauler?
        int ini,seg;
        if(jugades.size() == 0){
            ini = 1;
            seg = seguentPref(pref,ini);
        }else {
            ArrayList<Jugada> aux = jugades.get(jugades.size()-1);
            int last = aux.get(aux.size() - 1).getNum();
            ini = seguentPref(pref, last);
            seg = seguentPref(pref, ini);
        }
        while(seg - ini == 1){
            ini = seg;
            seg = seguentPref(pref, ini);
        }
        if(ini == max) throw new Utils.ExceptionHidatoSolucionat("Solucionat!");
        ArrayList<Vector<Celda>> camins;
        try{
            camins = TrobaCaminsValids(ini,seg,t);
        }catch(Exception e){
            throw new Utils.ExceptionHidatoNoSol("Hidato no solucionat!");
        }
        if(camins.size() == 0){
            if(jugades.size() == 0){
                throw new Utils.ExceptionHidatoNoSol("Hidato sense solucio");
            }
            ArrayList<Jugada> j = jugades.get(jugades.size()-1);
            jugades.remove(j);
            for(Jugada aux : j){
                Celda c = aux.getCelda();
                c.vaciar();
            }
            pref.add(ini); //hay que restaurar el prefijado ini eliminado antes del primer if
            return; //hay que deshacer ultimo movimiento, TODO hay que crear jugadas
        }
        for(Vector<Celda> cami : camins){
            int cont = ini;
            ArrayList<Jugada> jcami = new ArrayList<>();
            for(Celda pas : cami){
                if(!pas.isPrefijada() && pas.isVacia()){
                    Jugada i = null;
                    try{
                        i = new Jugada(pas, ++cont);
                    }catch(Utils.ExceptionJugadaNoValida e){
                        e.printStackTrace();
                    }
                    jcami.add(i);
                    if(i.getNum() + 1 == max){
                        throw new Utils.ExceptionHidatoSolucionat("Solucionat!");
                    }
                }
            }
            if(jcami.size() != 0) jugades.add(jcami);
            resolHidatoAlgorito(t,pref,max,jugades);
        }
        if(jugades.isEmpty()) return;
        ArrayList<Jugada> j = jugades.get(jugades.size()-1);
        jugades.remove(j);
        for(Jugada aux : j){
            Celda c = aux.getCelda();
            c.vaciar();
        }
    }
    private int CalculaIniSeg(SortedSet<Integer> s, int last){
        int max = 0;
        Iterator<Integer> aux = s.iterator();
        while(aux.hasNext()){
            int aux2 = aux.next();
            if(aux2 > last && aux2 > max) max = aux2;
        }
        return max;
    }
    private int seguentPref(SortedSet<Integer> s, int last){
        Iterator<Integer> aux = s.iterator();
        while(aux.hasNext()){
            int l = aux.next();
            if(l > last) return l;
        }
        return 0;
    }

    private void print_jugades(ArrayList<ArrayList<Jugada>> jug){
        for(ArrayList<Jugada> aux : jug){
            for(Jugada j : aux){
                System.out.print(j.getCelda().getValor() + ",");
            }
            System.out.print("\n");

        }
    }
    private void print_tauler_test(Celda[][] t, ArrayList<Vector<Celda>>camins){ //TODO ELIMINAR chivato
        for(Celda[] c : t){
            for(Celda celda : c){
                if(celda.isFrontera())System.out.print("# ");
                else if(celda.isVacia() && celda.isValida()) System.out.print("? ");
                else if(!celda.isValida()) System.out.print("* ");
                else System.out.print(celda.getValor() + " ");
            }
            System.out.print('\n');
        }
        System.out.print("-----------\n");
    }

    public ArrayList<Vector<Celda>> TrobaCaminsValids(int inici, int fi, Celda[][] t) throws Exception {//public per fer el test
        Stack<Vector<Celda>> s = new Stack<>();
        ArrayList<Vector<Celda>> rutasValidas = new ArrayList<>();
        AbstractMap.SimpleEntry<Integer,Integer> p = Utils.BuscarN(t,inici);
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


    private AbstractMap.SimpleEntry<Integer, Integer> BuscarNnopref(Celda[][] c, int n) throws Exception {
        for(int i = 0; i < c.length; ++i){
            for(int j = 0; j < c[i].length; ++j){
                if(c[i][j].getValor() == n) return new AbstractMap.SimpleEntry<>(i, j);
            }
        }
        throw new Exception("Celda not found");
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
