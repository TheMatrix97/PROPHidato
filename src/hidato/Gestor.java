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

    public void crearPartidaBuida(String nomJugador){
        game = new Partida(nomJugador);
    }
    public void crearPartidaBD(String nom, String nomJugador){
        try{
            game = new Partida(nom,nomJugador);
        }catch(Exception e){
            this.game = null;
            //TODO avisar que la partida no es valida
        }
    }

    public void ferJugada(int i, int j, int num) throws Utils.ExceptionJugadaNoValida, Utils.ExceptionTaulerResolt {
        if(game != null) {
            try {
                game.fesJugadaIns(i, j, num);

            }catch(Utils.ExceptionTaulerResolt e){
                Time t = game.getTiempo();
                Jugador jug = game.getJugador();
                if(jug != null) { //si lo ha hecho una persona
                    Record r = new Record(t, jug.getNom());
                    boolean exists = false;
                    for(Ranking rank : rankings){ //todo testear
                        if(rank.getConf().equals(game.getConf())){
                            rank.addRecord(r);
                            exists = true;
                            break;
                        }
                    }
                    if(!exists){
                        Ranking rank = new Ranking(game.getConf());
                        rank.addRecord(r);
                        rankings.add(rank);

                    }

                }
                throw new Utils.ExceptionTaulerResolt();

            }
        }
    }
    public void ferJugadaDel(int i, int j) throws Utils.ExceptionJugadaNoValida {
        if(game != null){
            game.fesJugadaDel(i,j);
        }
    }
    public void demanarAjuda() throws Utils.ExceptionTaulerResolt { //Si la maquina posa l'últim número no tens record.
        if(game != null){
            game.pedirAyuda();
        }
    }
    public void setPartida(Partida p){
        this.game = p;
    }
    public Partida getPartida(){
        return this.game;
    }
    public ArrayList<Ranking> getRankings(){
        return this.rankings;
    }



    //La clase Gestor es de tipo Singleton, es decir q hay solo una instancia de gestor
    //Para conseguir que una clase sea de tipo Singleton necesitamos en primer lugar que su constructor sea privado.
    // De esa forma ningún programa será capaz de construir objetos de esta tipo .
    // En segundo lugar necesitaremos disponer de una variable estatica privada que almacene una referencia al objeto que vamos a crear a traves del constructor .
    // Por ultian método estático publico que se encarga de instanciar el objeto la primera vez y almacenarlo en la variable estática.
}


