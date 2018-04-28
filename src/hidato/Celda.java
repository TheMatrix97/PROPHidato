/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidato;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author marc.catrisse & lluis.marques
 */

public class Celda implements Serializable{
    boolean valida; //true si es valida, se puede usar
    boolean prefijada; //true si es prefijada
    boolean vacia; //true si esta vacia
    boolean frontera; //true si es una frontera #
    int valor;
    private char FormaC;
    private String adj; //SI C = 0 | CA = 1

    private ArrayList<Celda> vecinos;

    //Matriu size vehins segons adjacencies
    /*POSICIONS:
     * FIGURA - COSTAT - COSTAT + VERTEX
     * TRIANGLE   3         12
     * QUADRAT    4         8
     * HEXAGON    6         6*/
    private int[][] sizeadj = {{3, 12}, {4, 8}, {6, 6}};

    //constructores
    public Celda(boolean prefijada, int valor, char TypeS, String adjacencia) { //celda valida con valor prefijada o no
        this.FormaC = TypeS;
        int t = switchType();
        this.adj = adjacencia;
        this.vecinos = new ArrayList<>(sizeadj[t][adjtoint(adj)]);
        this.valida = true;
        this.prefijada = prefijada;
        this.vacia = false;
        this.valor = valor;
        this.frontera = false;

    }

    public Celda(boolean vacia, char TypeS, String adjacencia) { //celda sin valor pero valida
        this.FormaC = TypeS;
        this.adj = adjacencia;
        int t = switchType();
        this.vecinos = new ArrayList<>(sizeadj[t][adjtoint(adj)]);
        this.valida = true;
        this.prefijada = false;
        this.vacia = vacia;
        this.frontera = false;
    }
    
    public Celda(char TypeS, String adjacencia, boolean f) { //celda no valida
        this.FormaC = TypeS;
        this.adj = adjacencia;
        int t = switchType();
        this.vecinos = new ArrayList<>(sizeadj[t][adjtoint(adj)]);
        this.valida = false;
        this.frontera = f;
    }

    public Celda(Celda c){ //constructor copia
        this.FormaC = c.getForma();
        this.adj = c.getAdj();
        this.valida = c.isValida();
        this.prefijada = c.isPrefijada();
        this.frontera = c.isFrontera();
        this.valor = c.getValor();
        this.vecinos = new ArrayList<>(sizeadj[switchType()][adjtoint(this.adj)]);
    }
    private int switchType(){
        switch(this.FormaC){
            case 'T':
                return 0;
            case 'Q':
                return 1;
            default: //hexagon
                return 2;
        }
    }

    private int adjtoint(String adj){
        if(adj.equals("CA")) return 1;
        else return 0;
    }
    //getter
    public boolean isValida() {
        return valida;
    }
    public String getAdj(){return adj;}

    public boolean isPrefijada() {
        return prefijada;
    }

    public int getValor() {
        return valor;
    }
    public boolean isFrontera(){
        return frontera;
    }

    public char getForma(){
        return this.FormaC;
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
        return sizeadj[t][adjtoint(this.adj)];
    }

    public void setValor(int val){
        this.valor = val;
        this.vacia = false;
    }
    public void setPrefijada(){
        this.prefijada = true;
    }

    public void vaciar(){
        this.vacia = true;
    }

    public void setInvalida(){
        this.valida = false;
        this.frontera = false; //ENS ASEGUREM DE QUE ES '*' i no '#'
        this.vacia = true;
    }

    public void setFrontera(){
        this.frontera = true;
        this.valida = false;
    }

    public void setValida(){
        this.valida = true;
    }

    @Override
    public Celda clone(){
        return new Celda(this);
    }

}
