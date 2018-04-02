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
    //TODO Eliminar chivatos

    public void resolHidato(Celda[][] t, SortedSet<Integer> pref, int max, ArrayList<ArrayList<Jugada>> jugades) throws Exception { //TODO en vez de usar Celda[][] usar Tauler?
        int ini = pref.first();
        if(ini == max) return;
        pref.remove(ini);
        ArrayList<Vector<Celda>> camins = TrobaCaminsValids(ini,pref.first(),t);
        if(camins.size() == 0){
            if(jugades.size() == 0){
                System.out.println("No te solució!");
                return;
            }
            ArrayList<Jugada> j = jugades.get(jugades.size()-1);
            jugades.remove(j);
            System.out.println("voy a deshacer el ultimo camino loko: ");
            for(Jugada aux : j){
                Celda c = aux.getCelda();
                c.vaciar();
            }
            pref.add(ini); //hay que restaurar el prefijado ini eliminado antes del primer if
            print_tauler_test(t, camins); //chivato
            return; //hay que deshacer ultimo movimiento, TODO hay que crear jugadas
        }
        for(Vector<Celda> cami : camins){
            int cont = ini;
            ArrayList<Jugada> jcami = new ArrayList<>();
            for(Celda pas : cami){
                if(!pas.isPrefijada() && pas.isVacia()){
                    Jugada i = new Jugada(true, pas, ++cont);
                    jcami.add(i);
                }
            }
            if(jcami.size() != 0) jugades.add(jcami);
            print_tauler_test(t, camins);
            resolHidato(t,pref,max,jugades);
        }
    }
    private void print_tauler_test(Celda[][] t, ArrayList<Vector<Celda>>camins){ //TODO ELIMINAR chivato
        for(Celda[] c : t){
            for(Celda celda : c){
                if(celda.isFrontera())System.out.print("# ");
                else if(celda.isVacia()) System.out.print("? ");
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
