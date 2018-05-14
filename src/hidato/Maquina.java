package hidato;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author marc.catrisse & lluis.marques
 */
//Class abstract que implementa l'algorisme de resolució
public abstract class Maquina implements Serializable{

    //Funció publica per resoldre un Tauler
    public static boolean resolHidato(Tauler t){
        SortedSet<Integer> pref = t.getPrefixats();
        ArrayList<ArrayList<Jugada>> jugades = new ArrayList<>();
        Time time = new Time();
        try {
            time.start_time();
            resolHidatoAlgoritmo(t.getTauler(),pref,pref.last(),jugades, time);
        }catch(Utils.ExceptionHidatoSolucionat e){
            time.stop_time();
            return true;
        }catch(Utils.ExceptionHidatoNoSol | Utils.ExceptionTimeOut e){
            time.stop_time();
            return false;
        }
        return false;
    }

    //algorisme explicat a la documentació
    private static void resolHidatoAlgoritmo(Celda[][] t, SortedSet<Integer> pref, int max, ArrayList<ArrayList<Jugada>> jugades, Time time) throws Utils.ExceptionHidatoNoSol, Utils.ExceptionHidatoSolucionat, Utils.ExceptionTimeOut {
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
            camins = TrobaCaminsValids(ini,seg,t,time);
        }catch(Exception e){
            throw new Utils.ExceptionTimeOut("");
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
            return; //hay que deshacer ultimo movimiento
        }
        for(Vector<Celda> cami : camins){
            if(time.checkTime(System.currentTimeMillis()) ||Thread.currentThread().isInterrupted()){ //timeout per temps o interrupció
                throw new Utils.ExceptionTimeOut("És massa complicat per resoldre en 20s");
            }
            int cont = ini;
            ArrayList<Jugada> jcami = new ArrayList<>();
            for(Celda pas : cami){
                if(!pas.isPrefijada() && pas.isVacia()){
                    Jugada i = null;
                    try{
                        i = new Jugada(pas, ++cont,null);
                    }catch(Utils.ExceptionJugadaNoValida e){
                        e.printStackTrace();
                    }
                    jcami.add(i);
                    if((i != null ? i.getNum() : 0) + 1 == max){
                        throw new Utils.ExceptionHidatoSolucionat("Solucionat!");
                    }
                }
            }
            if(jcami.size() != 0) jugades.add(jcami);
            resolHidatoAlgoritmo(t,pref,max,jugades, time);
        }
        if(jugades.isEmpty()) return;
        ArrayList<Jugada> j = jugades.get(jugades.size()-1);
        jugades.remove(j);
        for(Jugada aux : j){
            Celda c = aux.getCelda();
            c.vaciar();
        }
    }

    //funció auxiliar per calcula el seguent prefixat a tractar
    private static int seguentPref(SortedSet<Integer> s, int last){
        for (Integer l : s) {
            if (l > last) return l;
        }
        return 0;
    }
    //funció on donat 2 prefixats retorna tots els camins de x a y valids (l'utilitza l'algorisme de resolució

    public static ArrayList<Vector<Celda>> TrobaCaminsValids(int inici, int fi, Celda[][] t, Time time) throws Exception {//public per fer el test
        Stack<Vector<Celda>> s = new Stack<>();
        ArrayList<Vector<Celda>> rutasValidas = new ArrayList<>();
        AbstractMap.SimpleEntry<Integer,Integer> p = Utils.BuscarN(t,inici);
        Vector<Celda> v = new Vector<>();
        v.add(t[p.getKey()][p.getValue()]);
        s.push(v);
        while(!s.empty()){
            if(time.checkTime(System.currentTimeMillis()) || Thread.currentThread().isInterrupted()) throw new Exception(); //timeout per temps o interrupció
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
}
