package hidato;


import sun.security.krb5.Config;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author marc.catrisse & lluis.marques
 */

public class Tauler implements Serializable {
    private int n, k; //n final
    private SortedSet<Integer> prefixats;
    private int[] invalides; //TODO no se usa
    private boolean[] usats;
    private Celda[][] tauler;

    //Constructora en base un arxiu de la base de dades de Hidato (carreguem un fitxer amb el hidato)
    public Tauler(String idHidato) throws IOException {
        this.prefixats = new TreeSet<Integer>();
        String cadena;
        String filePath = new File("").getAbsolutePath();
        FileReader f = new FileReader(filePath + "/BaseDadesHidatos/" + idHidato + ".txt"); //TODO Control de errores
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
                this.usats = new boolean[n * k + 1];
                this.tauler = new Celda[this.n][this.k];
            } else {
                for (int j = 0; j < this.k; j++) {
                    tauler[ai][j] = obtecelda(aux[j], tcela, adj);
                    if (tauler[ai][j].isPrefijada()) {
                        prefixats.add(tauler[ai][j].getValor());
                        usats[tauler[ai][j].getValor()] = true;
                    }
                }
                ai++;
                if (ai == n) break;
            }
        }
        carregaveins(tcela, adj); // todo passar configuracio
    }

    public Tauler(int last, Configuracio conf) {
        ArrayList<ArrayList<Celda>> Mat = new ArrayList<>(); //MATRIU QUE CONTÉ ELS ARRAY LIST QUE FORMEN CADA FILA.
        for (int i = 0; i < last; ++i) {
            Mat.add(new ArrayList<Celda>(last));
        } //Matriz last x last creada(?)
        int i, j; //Posició random del 1 en la matrix.
        i = (int) Math.random() * last;
        j = (int) Math.random() * last;
        int s = conf.getAdjacencia(); //Tadj
        char c = conf.getcell(); //TCel·la
        Mat.set(i).set(j) = new Celda(true, 1, c, s);
        ArrayList<Integer> vMov = new ArrayList<Integer>(Celda.getnVecinosType(c, s));
        int max = 0;
        int min = 0;
        for (int x = 0; x < vMov.size(); ++x) {
            vMov.add(min);
        }
    }

    private Celda obtecelda(String aux, char tcela, String adj) {
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

    private void carregaveins(char tcela, String adj) { //genera enllaços entre veins segons configuracio
        int nvecinos = this.tauler[0][0].getnVecinos(); //TODO petará, si .txt esta vacio?
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.k; j++) {
                int[][] aux = getpossveins(tcela, adj, j, i);
                for (int l = 0; l < nvecinos; l++) {
                    if (esvalida(aux[l][0] + i, aux[l][1] + j)) {
                        tauler[i][j].addVecino(tauler[aux[l][0] + i][aux[l][1] + j]);
                    }
                }
            }
        }
    }

    public Celda[][] getTauler() {
        Celda[][] c = tauler.clone();
        return c;
    }

    private int[][] getpossveins(char tcela, String adj, int j, int i) {
        int aux[][];
        if (tcela == 'Q') {
            if (adj.equals("C")) aux = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
            else aux = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        } else if (tcela == 'H') { //TODO revisar que funciona bé
            if (j % 2 == 0) aux = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 1}};
            else aux = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}};

        } else { //triangle
            boolean c = orientacio_triangle(i, j); //true, mira cap adalt, sino mira cap abaix
            if (adj.equals("C")) {
                if (c) aux = new int[][]{{1, 0}, {0, 1}, {0, -1}};
                else aux = new int[][]{{-1, 0}, {0, 1}, {0, -1}};
            } else {
                if (c)
                    aux = new int[][]{{1, 0}, {0, 1}, {0, -1}, {-1, 0}, {-1, -1}, {-1, 1}, {1, -1}, {1, -2}, {1, 1}, {1, 2}, {0, 2}, {0, -2}};
                else
                    aux = new int[][]{{-1, 0}, {0, 1}, {0, -1}, {1, 0}, {1, -1}, {1, 1}, {-1, -1}, {-1, -2}, {-1, 1}, {-1, 2}, {0, 2}, {0, -2}};
            }
        }
        return aux;
    }

    private boolean orientacio_triangle(int i, int j) {
        int contador = 0;
        for (Celda c : this.tauler[0]) {
            if (c.isFrontera()) contador++;
        }
        Celda[] aux2 = this.tauler[i];
        int contador2 = 0;
        for (Celda c : aux2) {
            if (c.equals(this.tauler[i][j])) break;
            if (!c.isFrontera()) contador2++;
        }
        if (contador == this.tauler[0].length) {
            return (contador2 % 2 == 0);
        } else {
            if (i % 2 == 0) {
                return (contador2 % 2 == 0);
            } else {
                return (contador2 % 2 != 0);
            }
        }
    }

    private boolean esvalida(int i, int j) {
        return n > i && k > j && i >= 0 && j >= 0;
    }

    public SortedSet<Integer> getPrefixats() {
        return this.prefixats;
    }

    public int getNPrefixats() {
        return this.prefixats.size();
    }

    public Celda getUsat(int i) {
        for (Celda[] c : this.tauler) {
            for (Celda c2 : c) {
                if (c2.getValor() == i) return c2;
            }
        }
        return null;
    }

    public Tauler(Configuracio conf) {
        int last = GetLast(conf);
        Tauler t = new Tauler(last,conf);
    }



    public int GetLast (Configuracio conf) {  //Función para generar un tablero
        int numeroCeldas = 0;
        if (conf.getDificultat().equals("Facil")) {
            numeroCeldas = (int) (Math.random() * 20) + 16;
                    }
        if (conf.getDificultat().equals("Normal")) {
            numeroCeldas = (int) (Math.random() * 30) + 37;
                    }
        if (conf.getDificultat().equals("Dificil")) {
            numeroCeldas = (int) (Math.random() * 40) + 68;
        }
    }
}

