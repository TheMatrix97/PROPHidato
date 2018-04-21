package hidato;


import sun.security.krb5.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author marc.catrisse & lluis.marques
 */

public class Tauler implements Serializable{
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
                this.usats = new boolean[n*k+1];
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

    public Tauler (Configuracio conf){
        genera_tauler(conf);
                            //tenim el tauler carregat
    }

    private void genera_tauler(Configuracio conf){
        int last = GetLast(conf.getDificultat());
        this.tauler = new Celda[last][last];
        ini_tauler_vacio(last,this.tauler,conf);
        int i = (int) (Math.random() * last); //i/j del 1;
        int j = (int) (Math.random() * last);
        this.tauler[i][j].setValor(1);
        this.tauler[i][j].setPrefijada();
        //carregaveins(conf.getcell(),conf.getAdjacencia()); //TODO TENER EN cuenta diferentes tipos de triangulos
        int[][] veins = getpossveins(conf.getcell(),conf.getAdjacencia(),i,j);
        int n_vecinos = veins.length;
        double[] probabilidades = new double[n_vecinos];
        int[] movimientos = new int[n_vecinos];
        for(int ia = 0; ia < n_vecinos; ia++){
            movimientos[ia] = 0;
        }
        boolean fin = false;
        Celda c = this.tauler[i][j];
        int contador = 2;
        int min = 0;
        int max = 0;
        int iMin, iMax, jMin, jMax;
        iMin = iMax = i; //EMPEZAMOS IGUALANDO to do AL 1
        jMin = jMax = j;
        while(!fin){
            for(int ia = 0; ia < n_vecinos; ia++){
                probabilidades[ia] = 1; //inicializar
            }
            int contaux = 0;
            boolean first = true;
            for(int[] vf : veins){
                int iv = vf[0];
                int jv = vf[1];
                if (((i+iv > last-1 || i+iv < 0) || (jv+j > last-1 || jv+j < 0))) probabilidades[contaux] = 0;
                else if(!this.tauler[iv+i][jv+j].isVacia()) probabilidades[contaux] = 0;
                else{
                    if(!this.tauler[iv+i][jv+j].isVacia()) probabilidades[contaux] = 0;
                    if(first){
                        min = movimientos[contaux];
                        first = false;
                    }
                    if(movimientos[contaux] > max) max = movimientos[contaux];
                    if(movimientos[contaux] < min) min = movimientos[contaux];
                }
                contaux++;
            }

            recalcular_probs(movimientos,probabilidades, min,max);
            int seg = 0;
            try {
                seg = calcular_seguent(probabilidades, veins.length);
            }catch(Exception e){//si no hay camino, vuelve a ejecutar
                genera_tauler(conf);
                break;
            }
            int[] posSeg = veins[seg];
            i += posSeg[0];
            j += posSeg[1];
            //ACTUALIZAMOS/CHECKEAMOS iMin, iMax, jMin, jMax
            if(i < iMin) iMin = i;
            if(i > iMax) iMax = i;
            if(j < jMin) jMin = j;
            if(j > jMax) jMax = j;
            this.tauler[i][j].setValor(contador);
            if(contador == last)fin = true;
            else {
                movimientos[seg]++;
                contador++;
            }
        }
        //System.out.println("FIN? : " + fin);
        //System.out.println("iMin: " + iMin + " iMax: " + iMax + " jMin: " + jMin + " jMax: " +jMax);
        //NO PODEMOS PONER MAS CODIGO AQUI PORQUE SI NO EL BREAK HACE QUE SE VAYA A TOMAR TO-DO POR CULO, A NO SER QUE LO METAMOS DENTRO DE UN IF(FIN)
        if(fin){
            this.tauler = retallaTauler(iMin, iMax, jMin, jMax);
            calculaFronteres(last);
            this.n = iMax - iMin + 1;
            this.k = jMax - jMin + 1;
            carregaveins(conf.getcell(), conf.getAdjacencia());
            Maquina m = new Maquina();
            boolean b = m.resolHidato(this);
            System.out.println(b);
        }
    }

    private void calculaFronteres(int last){
        this.prefixats = new TreeSet<Integer>();
        this.usats = new boolean[last + 1];
        int rand = (int) (Math.random()*7) + 3; //random de (3..6'99)
        for(Celda[] c : this.tauler){
            for(Celda celda : c){
                if(celda.isVacia()){
                    celda.setFrontera();
                }
                else if(celda.getValor() == 1 || celda.getValor() == last){
                    celda.setPrefijada();
                    celda.setValida();
                    this.prefixats.add(celda.getValor());
                    this.usats[celda.getValor()] = true;
                }
                else{
                    if(celda.getValor() % rand == 0){
                        celda.setPrefijada();
                        celda.setValida();
                        this.prefixats.add(celda.getValor());
                        this.usats[celda.getValor()] = true;
                    }
                    else{
                        celda.vaciar();
                        celda.setValida();
                    }
                }
            }
        }
    }

    private Celda[][] retallaTauler(int iMin, int iMax, int jMin, int jMax){
        Celda[][] taulerRetallat = new Celda[iMax-iMin + 1][jMax-jMin + 1];
        for(int i = 0; i < (iMax-iMin+1); ++i){
            for(int j = 0; j < (jMax-jMin+1); ++j){
                taulerRetallat[i][j] = this.tauler[iMin+i][jMin+j];
            }
        }
        return taulerRetallat;
    }

    private int calcular_seguent(double[] prob, int length) throws Exception {
        boolean valido = false;
        int seguent = length-1;
        boolean stop = false;
        for(double d : prob){
            if(d != 0.0){
                stop = true;
                break;
            }
        }
        if(!stop) throw new Exception(); //si no hay mas caminos(nos hemos encerrado tiramos excep)
        while(!valido) {
            double rand = (Math.random() * 2.0);
            seguent = (int)(Math.random() * length);
            //System.out.println("rand: " + rand + " seg: " + prob[seguent]);
            if(prob[seguent]*rand >= 1.0) valido = true;
        }
        return seguent;
    }

    private void recalcular_probs(int[] movimientos, double[] probabilidades, int min, int max){
        int size = movimientos.length;
        //System.out.println("min: " + min + " max: " + max);
        for(int i = 0; i < size; i++){
            if(probabilidades[i] != 0) {
                int mov = movimientos[i];
                if (min == max) {
                    probabilidades[i] = 1;
                }
                else if(mov == max){
                    probabilidades[i] = 0.55; //tenemos un 18% de volver a hacer el mismo movimiento (patron)
                }
                else if(min == mov){
                    probabilidades[i] = 2; //tenemos un 75% de seguir el patron menos usado
                }
                else{
                    probabilidades[i] = 1;
                }
            }
        }
    }

    private void ini_tauler_vacio(int last, Celda[][] tauler_gen, Configuracio c){
        for(int i = 0; i < last; i++){
            for(int j = 0; j < last; j++){
                tauler_gen[i][j] = new Celda(true,c.getcell(),c.getAdjacencia()); //llenamos el tablero de celdas vacias
            }
        }

    }

    private int GetLast(String typedif) {  //Función para generar un tablero
        switch (typedif){
            case "Dificil":
                return (int) (Math.random() * 40) + 69;
            case "Normal":
                return (int) (Math.random() * 30) + 38;
            default:
                return (int) (Math.random() * 20) + 17;
        }
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
                        //System.out.println("Hem afegit el vei");
                    }
                }
            }
        }
        /*
        for(Celda[] c: this.tauler){
            for(Celda celda : c){
                ArrayList<Celda> result = celda.getVecinos();
                System.out.println("ESTEM A LA CEL·LA " + celda.getValor() + " i els seus veins son: ");
                for(Celda k: result){
                    System.out.println(k.getValor());
                }
            }
        }*/
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
            boolean c = orientacio_triangle(i,j); //true, mira cap adalt, sino mira cap abaix
            if (adj.equals("C")) {
                if (c) aux = new int[][]{{1, 0}, {0, 1}, {0, -1}};
                else aux = new int[][] {{-1, 0}, {0, 1}, {0, -1}};
            } else {
                if (c) aux = new int[][] {{1, 0}, {0, 1}, {0, -1}, {-1,0}, {-1,-1}, {-1,1}, {1,-1}, {1,-2}, {1,1}, {1,2}, {0,2}, {0,-2}};
                else aux = new int[][] {{-1, 0}, {0, 1}, {0, -1},{1,0}, {1,-1}, {1,1}, {-1,-1}, {-1,-2}, {-1,1}, {-1,2}, {0,2}, {0,-2}};
            }
        }
        return aux;
    }
    private boolean orientacio_triangle(int i, int j){ //TODO falla
        int contador = 0;
        for(Celda c : this.tauler[0]){
            if(c.isFrontera()) contador++;
        }
        Celda[] aux2 = this.tauler[i];
        int contador2 = 0;
        for(Celda c : aux2){
            if(c.equals(this.tauler[i][j])) break;
            if(!c.isFrontera())contador2++;
        }
        if(contador == this.tauler[0].length){
            return(contador2 % 2 == 0);
        }else{
            if(i % 2 == 0){
                return(contador2 % 2 == 0);
            }else{
                return(contador2 % 2 != 0);
            }
        }
    }

    private boolean esvalida(int i, int j){
        return n > i && k > j && i >= 0 && j >= 0;
    }

    public SortedSet<Integer> getPrefixats(){
        return this.prefixats;
    }

    public int getNPrefixats(){
        return this.prefixats.size();
    }

    public Celda getUsat(int i){
        for(Celda[] c: this.tauler){
            for(Celda c2: c){
                if(c2.getValor() == i) return c2;
            }
        }
        return null;
    }
}
