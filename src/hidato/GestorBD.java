package hidato;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;

/**
 * @author Marc.Catrisse
 */
//Class de la capa de dades encargada de gestionar la Base de dades d'hidatos
public abstract class GestorBD {

    //funció encarregada de llegir un hidato de la base de dades donat el nom de l'arxiu
    public static Celda[][] llegir_hidato_bd(String idHidato, SortedSet<Integer> prefixats) throws IOException{
        Celda[][] tauler = null;
        int n = 0;
        int k = 0;
        String cadena;
        String filePath = new File("").getAbsolutePath();
        FileReader f = new FileReader(filePath + "/BaseDadesHidatos/" + idHidato + ".txt");
        BufferedReader b = new BufferedReader(f);
        char tcela = ' ';
        String adj = null;
        boolean first = true;
        int ai = 0;
        while ((cadena = b.readLine()) != null) {
            String[] aux = cadena.split(",");
            if (first) {
                first = false;
                tcela = aux[0].charAt(0);
                adj = aux[1];
                n = Integer.parseInt(aux[2]);
                k = Integer.parseInt(aux[3]);
                if(k <= 0 || n <= 0 || !(tcela == 'T' || tcela == 'Q' || tcela == 'H') || !(adj.equals("C") || adj.equals("CA"))) throw new IOException();
                tauler = new Celda[n][k];
            } else {
                for (int j = 0; j < k; j++) {
                    tauler[ai][j] = obtecelda(aux[j], tcela, adj);
                    if (tauler[ai][j].isPrefijada()) {
                        prefixats.add(tauler[ai][j].getValor());
                    }
                }
                ai++;
                if (ai == n) break;
            }
        }
        return tauler;
    }

    //funció que mapeja els caracters en objectes celda
    private static Celda obtecelda(String aux, char tcela, String adj) {
        Celda c;
        switch (aux) {
            case "*":
                c = new Celda(tcela, adj, false); //no valida i no frontera
                break;
            case "#":
                c = new Celda(tcela, adj, true); //no valida i frontera
                break;
            case "?":
                c = new Celda(true, tcela, adj);
                break;
            default:
                c = new Celda(true, Integer.parseInt(aux), tcela, adj);
                break;
        }
        return c;
    }

    //funció que retorna els noms dels fitxers guardats a BD
    public static ArrayList<String> llista_hidatos_disponibles(){
        ArrayList<String> res = new ArrayList<>();
        String filePath = new File("").getAbsolutePath();
        File aux = new File(filePath + "/BaseDadesHidatos");
        if(aux.exists()){
            File[] ficheros = aux.listFiles();
            if(ficheros == null) return res;
            for(File f : ficheros){
                String name = f.getName();
                if(name.contains("Hidato") && !name.contains("Out")){
                    res.add(name.replaceFirst(".txt", "")); //TODO tenim molta brossa dins de BaseDadesHidatos per fer els tests, a la propera entrega s'hauria de netejar una mica?
                }
            }
        }
        return res;
    }
}
