package hidato;


import java.util.ArrayList;
/* @author antonio.guilera & marc.blanca */

public class Gestor {
    //Comprova Save
    //Consulta Ranking
    //Guardar Partida
    //Cargar Partida
    private ArrayList<Ranking> rankings;
    private Partida game;
    private static Gestor Gest;

    private Gestor() {};

    public static Gestor getSingletonInstance(String nombre) {
        if (Gest == null){
            Gest = new Gestor();
        }
        else{
            System.out.println("ya existe gestor");
        }

        return Gest;
    }

    public boolean Existe_Partida(){
        if (game == null) {
            return false;
        }
        else return true;
    }

    public Ranking consulta_ranking(Configuracio conf){
        for(Ranking r : this.rankings){
            if(r.getConf().equals(conf)) return r;
        }
        return new Ranking(conf);
    }

   /* public void guardar_partida(){  TODO implementar Serializable
        game = getPartida();
    } */

    public void cargar_partida() throws Exception {
        if (Existe_Partida()){
            //como no tenemos la clase partida no sé como se haría esto xD pero seria llamar a algo con ese game
        }
        else throw new  Exception("No hi ha partida guardada.");
    }



    //La clase Gestor es de tipo Singleton, es decir q hay solo una instancia de gestor
    //Para conseguir que una clase sea de tipo Singleton necesitamos en primer lugar que su constructor sea privado.
    // De esa forma ningún programa será capaz de construir objetos de esta tipo .
    // En segundo lugar necesitaremos disponer de una variable estatica privada que almacene una referencia al objeto que vamos a crear a traves del constructor .
    // Por ultian método estático publico que se encarga de instanciar el objeto la primera vez y almacenarlo en la variable estática.
}


