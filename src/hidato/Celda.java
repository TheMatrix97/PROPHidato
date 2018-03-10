/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidato;

/**
 *
 * @author marc.catrisse
 */
public class Celda {
    boolean valida; //true si es valida, se puede usar
    boolean prefijada; //true si es prefijada
    boolean vacia; //true si esta vacia
    int valor;
    Celda[] vecinos;
    //constructores
    public Celda(boolean prefijada, int valor) { //celda valida con valor prefijada o no
        this.valida = true;
        this.prefijada = prefijada;
        this.vacia = false;
        this.valor = valor;
        
    }

    public Celda(boolean vacia) { //celda sin valor pero valida
        this.valida = true;
        this.prefijada = false;
        this.vacia = vacia;
    }
    
    public Celda() { //celda no valida
        this.valida = false;
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

    public Celda[] getVecinos() {
        return vecinos;
    }
    
    //setter
    public void setVecinos(Celda[] vecinos) {
        this.vecinos = vecinos;
    }
    
}
