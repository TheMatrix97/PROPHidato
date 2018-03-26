/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidato;

import java.util.ArrayList;

/**
 *
 * @author marc.catrisse & lluis.marques
 */

public class Celda {
    boolean valida; //true si es valida, se puede usar
    boolean prefijada; //true si es prefijada
    boolean vacia; //true si esta vacia
    boolean frontera; //true si es una frontera #
    int valor;
    private char FormaC;
    private int adj; //SI C = 0 | CA = 1

    private ArrayList<Celda> vecinos;

    //Matriu size vehins segons adjacencies
    /*POSICIONS:
     * FIGURA - COSTAT - COSTAT + VERTEX
     * TRIANGLE   3         12
     * QUADRAT    4         8
     * HEXAGON    6         6*/
    int[][] sizeadj = {{3, 12}, {4, 8}, {6, 6}};

    //constructores
    public Celda(boolean prefijada, int valor, char TypeS, String adjacencia) { //celda valida con valor prefijada o no
        this.FormaC = TypeS;
        int adj;
        if(adjacencia.equals("C")) adj = 0;
        else adj = 1;
        int t = switchType();
        this.adj = adj;
        this.vecinos = new ArrayList<Celda>(sizeadj[t][adj]);
        this.valida = true;
        this.prefijada = prefijada;
        this.vacia = false;
        this.valor = valor;
        this.frontera = false;

    }

    public Celda(boolean vacia, char TypeS, String adjacencia) { //celda sin valor pero valida
        this.FormaC = TypeS;
        int adj;
        if(adjacencia.equals("C")) adj = 0;
        else adj = 1; //TODO CONTROLAR SI ADJ != C I CA
        this.adj = adj;
        int t = switchType();
        this.vecinos = new ArrayList<Celda>(sizeadj[t][adj]);
        this.valida = true;
        this.prefijada = false;
        this.vacia = vacia;
        this.frontera = false;
    }
    
    public Celda(char TypeS, String adjacencia, boolean f) { //celda no valida
        this.FormaC = TypeS;
        int adj;
        if(adjacencia.equals("C")) adj = 0;
        else adj = 1;
        this.adj = adj;
        int t = switchType();
        this.vecinos = new ArrayList<Celda>(sizeadj[t][adj]);
        this.valida = false;
        this.frontera = f;
    }
    //TODO Controlar si ens envien un FormaC diferent de T, Q, H.

    private int switchType(){ //TODO sizeadj['T'][0] = 1; usar este template
        switch(this.FormaC){
            case 'T':
                return 0;
            case 'Q':
                return 1;
            default: //hexagon
                return 2;
        }
    }
    //getter
    public boolean isValida() {
        return valida;
    }

    public boolean isPrefijada() {
        return prefijada;
    }

    public int getValor() {
        return valor;
    }
    public boolean isFrontera(){
        return frontera;
    }

    public boolean isVacia() {
        return vacia;
    }

    public ArrayList<Celda> getVecinos() {
        return vecinos;
    }
    
    //Afegir vehins a una cel·la donada
    public void addVecino(Celda vecino) {
        this.vecinos.add(vecino);
    }

    public int getnVecinos(){
        int t = switchType();
        return sizeadj[t][this.adj];
    }
}
