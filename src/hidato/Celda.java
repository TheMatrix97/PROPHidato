/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidato;

import org.omg.CORBA.SetOverrideType;

import java.util.ArrayList;

/**
 *
 * @author marc.catrisse
 */

public class Celda {
    boolean valida; //true si es valida, se puede usar
    boolean prefijada; //true si es prefijada
    boolean vacia; //true si esta vacia
    int valor;

    enum Type{
        Triangle, Quadrat, Hexagon
    }
    private Type FormaC;

    private ArrayList<Celda> vecinos;

    //Matriu size vehins segons adjacencies
    /*POSICIONS:
     * FIGURA - COSTAT - COSTAT + VERTEX
     * TRIANGLE   3         12
     * QUADRAT    4         8
     * HEXAGON    6         6*/
    int[][] sizeadj = {{3, 12}, {4, 8}, {6, 6}};

    //constructores
    public Celda(boolean prefijada, int valor, String TypeS, boolean adjacencia) { //celda valida con valor prefijada o no
        SetTypeCela(TypeS);
        int adj = (adjacencia) ? 1 : 0;
        int t = switchType();
        this.vecinos = new ArrayList<Celda>(sizeadj[t][adj]);
        this.valida = true;
        this.prefijada = prefijada;
        this.vacia = false;
        this.valor = valor;

    }

    public Celda(boolean vacia, String TypeS, boolean adjacencia) { //celda sin valor pero valida
        SetTypeCela(TypeS);
        int adj = (adjacencia) ? 1 : 0;
        int t = switchType();
        this.vecinos = new ArrayList<Celda>(sizeadj[t][adj]);

        this.valida = true;
        this.prefijada = false;
        this.vacia = vacia;
    }
    
    public Celda(String TypeS, boolean adjacencia) { //celda no valida
        SetTypeCela(TypeS);
        int adj = (adjacencia) ? 1 : 0;
        int t = switchType();
        this.vecinos = new ArrayList<Celda>(sizeadj[t][adj]);
        this.valida = false;
    }

    //SELECCIONADOR DE TIPUS DE CEL·LA
    public void SetTypeCela(String TypeS){
        //Seleccionem el tipus de cel·la
        if(TypeS.equals("Triangle")) FormaC = Type.Triangle;
        else if(TypeS.equals("Quadrat")) FormaC = Type.Quadrat;
        else FormaC = Type.Hexagon;
    }

    private int switchType(){
        switch(this.FormaC){
            case Triangle:
                return 0;
            case Quadrat:
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
}
