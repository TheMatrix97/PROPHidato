package hidato;


import sun.security.krb5.Config;

import java.io.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author marc.catrisse & lluis.marques
 */

public class Tauler implements Serializable {
    private int n, k; //n final
    private SortedSet<Integer> prefixats;
    private boolean[] usats;
    private Celda[][] tauler;

    //Constructora en base un arxiu de la base de dades de Hidato (carreguem un fitxer amb el hidato)
    public Tauler(Tauler c) { //contructora copia
        this.tauler = clone_array(c.getTauler());
        this.prefixats = c.getPrefixats();
        this.n = c.getN();
        this.k = c.getK();

    }

    public Tauler(String idHidato) throws IOException {
        this.prefixats = new TreeSet<>();
        this.tauler = GestorBD.llegir_hidato_bd(idHidato,this.prefixats);
        this.n = this.tauler.length;
        this.k = this.tauler[this.n-1].length;
        System.out.println("n : " + n + "k: " + k);
        Celda c = this.tauler[0][0];
        calcular_usats();
        carregaveins(c.getForma(), c.getAdj()); // todo passar configuracio
    }

    public Tauler(Configuracio conf) {
        genera_tauler(conf);
        carregaveins(conf.getcell(), conf.getAdjacencia());
        //tenim el tauler carregat
    }
    private void calcular_usats(){
        usats = new boolean[this.n*this.k+1];
        for(Celda[] l : tauler){
            for(Celda c : l){
                if(c.isValida() && c.getValor() != 0){
                    usats[c.getValor()] = true;
                }
            }
        }
    }
    private void genera_tauler(Configuracio conf) {
        int last = GetLast(conf.getDificultat());
        this.tauler = new Celda[last][last];
        ini_tauler_vacio(last, this.tauler, conf);
        int i = (int) (Math.random() * last); //i/j del 1;
        int j = (int) (Math.random() * last);
        this.tauler[i][j].setValor(1);
        this.tauler[i][j].setPrefijada();
        //carregaveins(conf.getcell(),conf.getAdjacencia()); //TODO TENER EN cuenta diferentes tipos de triangulos
        int[][] veins = getpossveins(conf.getcell(), conf.getAdjacencia(), j, i);
        int n_vecinos = veins.length;
        double[] probabilidades = new double[n_vecinos];
        int[] movimientos = new int[n_vecinos];
        for (int ia = 0; ia < n_vecinos; ia++) {
            movimientos[ia] = 0;
        }
        boolean fin = false;
        int contador = 2;
        int min = 0;
        int max = 0;
        int iMin, iMax, jMin, jMax;
        iMin = iMax = i; //EMPEZAMOS IGUALANDO to do AL 1
        jMin = jMax = j;
        while (!fin) {
            veins = getpossveins(conf.getcell(), conf.getAdjacencia(), j, i);
            for (int ia = 0; ia < n_vecinos; ia++) {
                probabilidades[ia] = 1; //inicializar
            }
            int contaux = 0;
            boolean first = true;
            for (int[] vf : veins) {
                int iv = vf[0];
                int jv = vf[1];
                if (((i + iv > last - 1 || i + iv < 0) || (jv + j > last - 1 || jv + j < 0)))
                    probabilidades[contaux] = 0;
                else if (!this.tauler[iv + i][jv + j].isVacia()) probabilidades[contaux] = 0;
                else {
                    if (!this.tauler[iv + i][jv + j].isVacia()) probabilidades[contaux] = 0;
                    if (first) {
                        min = movimientos[contaux];
                        first = false;
                    }
                    if (movimientos[contaux] > max) max = movimientos[contaux];
                    if (movimientos[contaux] < min) min = movimientos[contaux];
                }
                contaux++;
            }

            recalcular_probs(movimientos, probabilidades, min, max);
            int seg = 0;
            try {
                seg = calcular_seguent(probabilidades, veins.length);
            } catch (Exception e) {//si no hay camino, vuelve a ejecutar
                genera_tauler(conf);
                break;
            }
            int[] posSeg = veins[seg];
            i += posSeg[0];
            j += posSeg[1];
            //ACTUALIZAMOS/CHECKEAMOS iMin, iMax, jMin, jMax
            if (i < iMin) iMin = i;
            if (i > iMax) iMax = i;
            if (j < jMin) jMin = j;
            if (j > jMax) jMax = j;
            this.tauler[i][j].setValor(contador);
            if (contador == last) fin = true;
            else {
                movimientos[seg]++;
                contador++;
            }
        }
        //System.out.println("FIN? : " + fin);
        //System.out.println("iMin: " + iMin + " iMax: " + iMax + " jMin: " + jMin + " jMax: " +jMax);
        //NO PODEMOS PONER MAS CODIGO AQUI PORQUE SI NO EL BREAK HACE QUE SE VAYA A TOMAR TO-DO POR CULO, A NO SER QUE LO METAMOS DENTRO DE UN IF(FIN)
        if (fin) {
            if (conf.getcell() != 'Q') {
                iMin += ajustesOrientacion(iMin, jMin, jMax);
            }
            this.tauler = retallaTauler(iMin, iMax, jMin, jMax);
            calculaFronteres(last);
            genera_invalides();
            this.n = this.tauler.length;
            this.k = this.tauler[0].length;
            //System.out.println("n: " + this.n + " k: " + this.k);
            //ajustes para que la orientacion sea chachi
            //  System.out.println(b);
        }
    }

    private int ajustesOrientacion(int iMin, int jMin, int jMax) {
        if (iMin % 2 != 0) { //hay que añadir fila arriba
            //System.out.println("Add fila");
            for (int j = jMin; j <= jMax; j++) {
                Celda c = this.tauler[iMin - 1][j];
                c.setInvalida();
                c.setPrefijada();
            }
            return -1;
        }
        return 0;
    }

    private void calculaFronteres(int last) {
        this.prefixats = new TreeSet<Integer>();
        this.usats = new boolean[last + 1];
        int rand = (int) (Math.random() * 7) + 3; //random de (3..6'99)
        for (Celda[] c : this.tauler) {
            for (Celda celda : c) {
                if (celda.isVacia() && !celda.isPrefijada()) {
                    celda.setFrontera();
                } else if (celda.getValor() == 1 || celda.getValor() == last) {
                    celda.setPrefijada();
                    //celda.setValida();
                    this.prefixats.add(celda.getValor());
                    this.usats[celda.getValor()] = true;
                } else {
                    if (celda.getValor() % rand == 0) {
                        celda.setPrefijada();
                        //celda.setValida();
                        this.prefixats.add(celda.getValor());
                        this.usats[celda.getValor()] = true;
                    } else {
                        celda.vaciar();
                        //celda.setValida();
                    }
                }
            }
        }
    }

    private Celda[][] retallaTauler(int iMin, int iMax, int jMin, int jMax) {
        Celda[][] taulerRetallat = new Celda[iMax - iMin + 1][jMax - jMin + 1];
        for (int i = 0; i < (iMax - iMin + 1); ++i) {
            for (int j = 0; j < (jMax - jMin + 1); ++j) {
                taulerRetallat[i][j] = this.tauler[iMin + i][jMin + j];
            }
        }
        for (int i = 0; i < taulerRetallat.length; i++) {
            int contador = 0;
            int j;
            for (j = 0; j < taulerRetallat[i].length; j++) {
                if (taulerRetallat[i][j].isVacia()) contador++;
                else break;
            }
            // System.out.println("f: " + i + " suma: " + (contador+jMin));
            if ((contador + jMin) % 2 != 0) {
                if (j != 0) { //si hay espacio substituye un # por un *
                    taulerRetallat[i][j - 1].setInvalida();
                    taulerRetallat[i][j - 1].setPrefijada();
                } else {
                    return retallaTauler(iMin, iMax, jMin - 1, jMax); //añade una fila a la izq, para conservar la paridad
                }
            }
        }
        return taulerRetallat;
    }

    private void genera_invalides() {
        Celda[][] tauler = this.tauler;
        for (int i = 0; i < tauler.length; i++) {
            for (int j = 0; j < tauler[i].length; j++) {
                if (tauler[i][j].isFrontera()) {
                    boolean nfind1 = false;
                    boolean nfind2 = false;
                    for (int jaux = j; jaux < tauler[i].length; jaux++) {
                        if (tauler[i][jaux].isValida()) {
                            nfind1 = true;
                            break;
                        }
                    }
                    for (int jaux = j; jaux >= 0; jaux--) {
                        if (tauler[i][jaux].isValida()) {
                            nfind2 = true;
                            break;
                        }
                    }
                    if (nfind1 && nfind2) {
                        tauler[i][j].setInvalida();
                        tauler[i][j].setPrefijada();
                    }
                }
            }
        }
    }

    private int calcular_seguent(double[] prob, int length) throws Exception {
        boolean valido = false;
        int seguent = length - 1;
        boolean stop = false;
        for (double d : prob) {
            if (d != 0.0) {
                stop = true;
                break;
            }
        }
        if (!stop) throw new Exception(); //si no hay mas caminos(nos hemos encerrado tiramos excep)
        while (!valido) {
            double rand = (Math.random() * 2.0);
            seguent = (int) (Math.random() * length);
            //System.out.println("rand: " + rand + " seg: " + prob[seguent]);
            if (prob[seguent] * rand >= 1.0) valido = true;
        }
        return seguent;
    }

    private void recalcular_probs(int[] movimientos, double[] probabilidades, int min, int max) {
        int size = movimientos.length;
        //System.out.println("min: " + min + " max: " + max);
        for (int i = 0; i < size; i++) {
            if (probabilidades[i] != 0) {
                int mov = movimientos[i];
                if (min == max) {
                    probabilidades[i] = 1;
                } else if (mov == max) {
                    probabilidades[i] = 0.55; //tenemos un 9% de volver a hacer el mismo movimiento (patron)
                } else if (min == mov) {
                    probabilidades[i] = 2; //tenemos un 75% de seguir el patron menos usado
                } else {
                    probabilidades[i] = 1;
                }
            }
        }
    }

    private void ini_tauler_vacio(int last, Celda[][] tauler_gen, Configuracio c) {
        for (int i = 0; i < last; i++) {
            for (int j = 0; j < last; j++) {
                tauler_gen[i][j] = new Celda(true, c.getcell(), c.getAdjacencia()); //llenamos el tablero de celdas vacias
            }
        }

    }

    private int GetLast(String typedif) {  //Función para generar un tablero
        switch (typedif) {
            case "Dificil":
                return (int) (Math.random() * 40) + 69;
            case "Normal":
                return (int) (Math.random() * 30) + 38;
            default:
                return (int) (Math.random() * 20) + 17;
        }
    }

    private void carregaveins(char tcela, String adj) { //genera enllaços entre veins segons configuracio
        int nvecinos = this.tauler[0][0].getnVecinos(); //TODO petará, si .txt esta vacio?
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.k; j++) {
                int[][] aux = getpossveins(tcela, adj, j, i);
                for (int l = 0; l < nvecinos; l++) {
                    if (esvalida(aux[l][0] + i, aux[l][1] + j)) {
                        tauler[i][j].addVecino(tauler[aux[l][0] + i][aux[l][1] + j]);
                        //System.out.println("Hem afegit el vei");
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
        boolean c = true;
        if (tcela == 'Q') {
            if (adj.equals("C")) aux = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
            else aux = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        } else if (tcela == 'H') { //TODO revisar que funciona bé
            if (j % 2 == 0) aux = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 1}};
            else aux = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}};

        } else { //triangle
            c = orientacio_triangle(i, j); //true, mira cap adalt, sino mira cap abaix
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
        //System.out.println("i: " + i + " j:" + j + "bool: " + c);
        return aux;
    }

    private boolean orientacio_triangle(int i, int j) { //TODO falla
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


    public boolean getUsat(int n){
        return this.usats[n];
    }

    public void addUsat(int n) throws Utils.ExceptionJugadaNoValida {
        if(n >= this.prefixats.last() || n < 1 || this.usats[n]){
            throw new Utils.ExceptionJugadaNoValida();
        }else {
           // System.out.println(n + " ha estat usat!");
            this.usats[n] = true;
        }
    }
    public void delUsat(int n) throws Utils.ExceptionJugadaNoValida {
        if(n >= this.prefixats.last() || n < 1){
            throw new Utils.ExceptionJugadaNoValida();
        }else{
            this.usats[n] = false;
        }
    }

    public int getN() {
        return this.n;
    }

    public int getK() {
        return this.k;
    }
    private Celda[][] clone_array(Celda[][] c){
        Celda[][] aux = new Celda[c.length][c[0].length];
        for(int i = 0; i < c.length; i++){
            for(int j = 0; j < c[i].length; j++){
                Celda caux = new Celda(c[i][j]);
                aux[i][j] = caux;
            }
        }
        return aux;
    }

    public Celda getCelda(int i, int j) throws Utils.ExceptionPosicioNoValida {
        if((i >= 0 && i < this.n) && (j >= 0 && j < this.k)){
            return this.tauler[i][j];
        }else{
            throw new Utils.ExceptionPosicioNoValida();
        }
    }

    public boolean validador_tauler(){
        AbstractMap.SimpleEntry<Integer,Integer> p;
        Celda c = null;
        try{
            p = Utils.BuscarN(this.tauler,1);
            c = getCelda(p.getKey(), p.getValue());
        }catch(Exception e){
            return false;
        }
        boolean found = false;
        int contador = 1;
        while(!found){
           // System.out.println("Num: " + c.getValor());
            ArrayList<Celda>vecinos = c.getVecinos();
            boolean cont = false;
            //System.out.println("Vecinos: ");
            for(Celda aux : vecinos){
             //   System.out.println(aux.getValor() + ", ");
                if(aux.isValida() && !aux.isVacia() && aux.getValor() == contador + 1){
                    contador++;
                    c = aux;
                    cont = true;
                    break;
                }
            }
            if(!cont) return false;
            if(contador == this.prefixats.last()) return true;
        }
        return false;
    }
}
