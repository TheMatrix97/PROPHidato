package hidato;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.SortedSet;

/**
 *
 * @author marc.catrisse & lluis.marques
 */

public class Tauler {
    private int n, k; //n final
    private SortedSet<Integer> prefixats;
    private int[] invalides; //TODO no se usa
    private boolean[] usats;
    private Celda[][] tauler;

    //Constructora en base un arxiu de la base de dades de Hidato (carreguem un fitxer amb el hidato)
    public Tauler(String idHidato) throws IOException {
        String cadena;
        this.usats = new boolean[n*k];
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
                //TODO WRAP configuracio en un objecte configuracio
                tcela = aux[0].charAt(0);
                adj = aux[1];
                this.n = Integer.parseInt(aux[2]);
                this.k = Integer.parseInt(aux[3]);
                this.tauler = new Celda[this.n][this.k];
            } else {
                for(int j = 0; j < this.k; j++){
                    tauler[ai][j] = obtecelda(aux[j],tcela,adj);
                    if(tauler[ai][j].isPrefijada()){
                        prefixats.add(tauler[ai][j].getValor());
                        usats[tauler[ai][j].getValor()] = true;
                    }
                }
                ai++;
                if(ai == n) break;
            }
        }
        carregaveins(tcela, adj); // todo passar configuracio
    }

    private Celda obtecelda(String aux, char tcela, String adj){
        Celda c;
        switch (aux) {
            case "*":
                c = new Celda(tcela, adj,false); //no valida i no frontera
                break;
            case "#":
                c = new Celda(tcela, adj,true); //no valida i frontera
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
    private void carregaveins(char tcela, String adj){ //genera enllaços entre veins segons configuracio
        int nvecinos = this.tauler[0][0].getnVecinos(); //TODO petará, si .txt esta vacio?
        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.k; j++){
                int[][] aux = getpossveins(tcela,adj,j,i);
                for(int l = 0; l < nvecinos; l++){
                    if(esvalida(aux[l][0] + i,aux[l][1]+j)){
                        tauler[i][j].addVecino(tauler[aux[l][0] + i][aux[l][1] + j]);
                    }
                }
            }
        }
    }
    public Celda[][] getTauler(){
        Celda[][] c = tauler.clone();
        return c;
    }

    private int[][] getpossveins(char tcela, String adj , int j, int i) {
        int aux[][];
        if (tcela == 'Q') {
            if (adj.equals("C")) aux = new int[][] {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
            else aux = new int[][] {{-1, 0}, {1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        } else if (tcela == 'H') { //TODO revisar que funciona bé
            if (j % 2 == 0) aux = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 1}};
            else aux = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}};

        } else { //triangle
            Celda[] aux2 = this.tauler[i]; //funcio per saber si el triangle te la punta superior a dalt o abaix
            int contador = 0;
            for(Celda c : aux2){
                if(c.equals(this.tauler[i][j])) break;
                if(!c.isFrontera())contador++;
            }
            if (adj.equals("C")) {
                if (contador % 2 == 0) aux = new int[][]{{1, 0}, {0, 1}, {0, -1}};
                else aux = new int[][] {{-1, 0}, {0, 1}, {0, -1}};
            } else {
                if (contador % 2 == 0) aux = new int[][] {{1, 0}, {0, 1}, {0, -1}, {-1,0}, {-1,-1}, {-1,1}, {1,-1}, {1,-2}, {1,1}, {1,2}, {0,2}, {0,-2}};
                else aux = new int[][] {{-1, 0}, {0, 1}, {0, -1},{1,0}, {1,-1}, {1,1}, {-1,-1}, {-1,-2}, {-1,1}, {-1,2}, {0,2}, {0,-2}};
            }
        }
        return aux;
    }

    private boolean esvalida(int i, int j){
        return n > i && k > j && i >= 0 && j >= 0;
    }

    public SortedSet<Integer> getPrefixats(){
        return this.prefixats;
    }
}
