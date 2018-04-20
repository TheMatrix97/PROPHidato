package hidato;



import java.io.*;
import java.util.ArrayList;
/* @author antonio.guilera & marc.blanca */

public class Gestor implements Serializable{
    //Comprova Save
    //Consulta Ranking
    //Guardar Partida
    //Cargar Partida
    private ArrayList<Ranking> rankings;
    private Partida game;
    private static Gestor Gest;

    private Gestor() {
     this.rankings = new ArrayList<>();
    }

    public static Gestor getSingletonInstance() {
        if (Gest == null){
            Gest = new Gestor();
        }
        else{
            System.out.println("ya existe gestor");
        }

        return Gest;
    }

    public Ranking consulta_ranking(Configuracio conf){
        for(Ranking r : this.rankings){
            if(r.getConf().equals(conf)) return r;
        }
        return new Ranking(conf);
    }



    public void setPartida(Partida p){
        this.game = p;
    }
    public Partida getPartida(){
        return this.game;
    }



    //La clase Gestor es de tipo Singleton, es decir q hay solo una instancia de gestor
    //Para conseguir que una clase sea de tipo Singleton necesitamos en primer lugar que su constructor sea privado.
    // De esa forma ningún programa será capaz de construir objetos de esta tipo .
    // En segundo lugar necesitaremos disponer de una variable estatica privada que almacene una referencia al objeto que vamos a crear a traves del constructor .
    // Por ultian método estático publico que se encarga de instanciar el objeto la primera vez y almacenarlo en la variable estática.
}


