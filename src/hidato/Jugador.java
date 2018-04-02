package hidato;

import java.io.Serializable;

/* @author lluis.marques */
public class Jugador implements Serializable{
    private String nom;

    public Jugador(String nom){
        this.nom = nom;
    }
    public String getNom(){
        return nom;
    }

}