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
 * @author Lluis.Marquès & Marc.Catrisse
 */

public class Celda implements Serializable {
    private boolean valida; //true si es valida, se puede usar
    private boolean prefijada; //true si es prefijada
    private boolean vacia; //true si esta vacia
    private boolean frontera; //true si es una frontera #
    private int valor; //valor de la celda si es vàlida
    private char FormaC; //forma: T,Q o H
    private String adj; //adjacencia C o CA
    private ArrayList<Celda> vecinos;

    //Matriu size veins segons adjacencies
    /*POSICIONS:
     * FIGURA - COSTAT - COSTAT + VERTEX
     * TRIANGLE   3         12
     * QUADRAT    4         8
     * HEXAGON    6         6*/
    private int[][] sizeadj = {{3, 12}, {4, 8}, {6, 6}};

    //Constructora
    public Celda(boolean prefijada, int valor, char TypeS, String adjacencia) { //celda valida amb valor prefixada o no
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

    public Celda(boolean vacia, char TypeS, String adjacencia) { //celda sense valor pero vàlida
        this.FormaC = TypeS;
        this.adj = adjacencia;
        int t = switchType();
        this.vecinos = new ArrayList<>(sizeadj[t][adjtoint(adj)]);
        this.valida = true;
        this.prefijada = false;
        this.vacia = vacia;
        this.frontera = false;
    }

    public Celda(char TypeS, String adjacencia, boolean f) { //celda no vàlida
        this.FormaC = TypeS;
        this.adj = adjacencia;
        int t = switchType();
        this.vecinos = new ArrayList<>(sizeadj[t][adjtoint(adj)]);
        this.valida = false;
        this.frontera = f;
    }

    public Celda(Celda c) { //constructor copia
        this.FormaC = c.getForma();
        this.adj = c.getAdj();
        this.valida = c.isValida();
        this.prefijada = c.isPrefijada();
        this.frontera = c.isFrontera();
        this.valor = c.getValor();
        this.vecinos = new ArrayList<>(sizeadj[switchType()][adjtoint(this.adj)]);
    }

    private int switchType() { //S'utilitza per obtenir el size maxim dels veins (consultant a la matriu sizeadj
        switch (this.FormaC) {
            case 'T':
                return 0;
            case 'Q':
                return 1;
            default:
                return 2;
        }
    }

    private int adjtoint(String adj) { //S'utilitza per obtenir el size maxim dels veins (consultant a la matriu sizeadj
        if (adj.equals("CA")) return 1;
        else return 0;
    }

    //Afegir veins a una cel·la donada
    public void addVecino(Celda vecino) {
        this.vecinos.add(vecino);
    }


    @Override
    public Celda clone() {
        return new Celda(this);
    }
    //Setters
    public void setValor(int val) {
        this.valor = val;
        this.vacia = false;
    }

    public void setPrefijada() {
        this.prefijada = true;
    }

    public void vaciar() {
        this.vacia = true;
    }

    public void setInvalida() {
        this.valida = false;
        this.frontera = false; //ENS ASEGUREM DE QUE ES '*' i no '#'
        this.vacia = true;
    }

    public void setFrontera() {
        this.frontera = true;
        this.valida = false;
    }

    //Getters
    public boolean isValida() {
        return valida;
    }

    public String getAdj() {
        return adj;
    }

    public boolean isPrefijada() {
        return prefijada;
    }

    public int getValor() {
        return valor;
    }

    public boolean isFrontera() {
        return frontera;
    }

    public char getForma() {
        return this.FormaC;
    }

    public boolean isVacia() {
        return vacia;
    }

    public int getnVecinos() {
        int t = switchType();
        return sizeadj[t][adjtoint(this.adj)];
    }

    public ArrayList<Celda> getVecinos() {
        return vecinos;
    }
}