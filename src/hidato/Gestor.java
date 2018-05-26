package hidato;



import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author Antonio.Guilera & Marc.Blanca
 */
//Classe singleton
public class Gestor implements Serializable{

    private ArrayList<Ranking> rankings;
    private Partida game;
    private static Gestor Gest;
    private GestorSaves gs;

    //constructora singleton
    private Gestor() {
        this.gs = new GestorSaves();
        try {
            this.rankings = gs.cargar_ranking();
        } catch (Exception e) {
            this.rankings = new ArrayList<>();
        }
    }

    public static Gestor getSingletonInstance() {
        if (Gest == null){
            Gest = new Gestor();
        }
        return Gest;
    }
    //funció per crear una partida sense tauler
    public void crearPartidaBuida(String nomJugador){
        try{
            rankings = gs.cargar_ranking(); //todo controlar en gs
        }catch(Exception e){

        }
        game = new Partida(nomJugador);
    }

    //funció per crear una partida partint d'un tauler de la BD
    public void crearPartidaBD(String nom, String nomJugador) throws Exception{
        try{
            rankings = gs.cargar_ranking();
        }catch(Exception e){

        }
        try{
            game = new Partida(nom,nomJugador);
        }catch(Exception e){
            e.printStackTrace();
            this.game = null;
            throw new Exception();
            //TODO avisar que la partida no es valida
        }
    }

    public void crearPartidaConf(char tcela, String adj, String dificultat, String nomjug){ //funció que crida la capa de presentació amb les opcions seleccionades
        crearPartidaBuida(nomjug);
        Configuracio conf =  new Configuracio(dificultat, adj,tcela);
        this.game.generar_partida_random(conf);
    }
    //funció que utilitza un usuari per fer una jugada d'inserció
    public void ferJugada(int i, int j, int num) throws Utils.ExceptionJugadaNoValida, Utils.ExceptionTaulerResolt {
        System.out.println("Faré una jugada " + game);
        if(game != null) {
            try {
                game.fesJugadaIns(i, j, num);

            }catch(Utils.ExceptionTaulerResolt e){
                Time t = game.getTiempo();
                Jugador jug = game.getJugador();
                System.out.println("jugador " + jug);
                if(jug != null) { //si lo ha hecho una persona
                    Record r = new Record(t, jug.getNom());
                    boolean exists = false;
                    for(Ranking rank : rankings){ //todo testear
                        if(rank.getConf().equals(game.getConf())){
                            System.out.println("Encontrado");
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
    //funció per eliminar un número del tauler
    public void ferJugadaDel(int i, int j) throws Utils.ExceptionJugadaNoValida {
        if(game != null){
            game.fesJugadaDel(i,j);
        }
    }

    //funció que utilitza l'usuari per demanar ajuda
    public void demanarAjuda() throws Utils.ExceptionTaulerResolt { //Si la maquina posa l'últim número no tens record.
        if(game != null){
            game.pedirAyuda();
        }
    }
    //setter
    public void setPartida(Partida p){
        this.game = p;
    }
    // getters
    public Partida getPartida(){
        return this.game;
    }

    public ArrayList<Ranking> getRankings(){
        return this.rankings;
    }

    public boolean partidaGuardada(){ //si hi ha una partida guardada retorna true sino false
        return gs.saveExiste();
    }

    public void guardarPartida(){
        gs.guardar_partida(this.game);
    }

    public void cargarPartida() throws Exception {
        setPartida(gs.cargar_partida());
    }

    public boolean getOrientacioTriangle(int x, int y, char t) {
        return game.getTauler().orientacio(x,y,t);
    }

    public void guardar_rankings(){
        System.out.println("Hay -> " + rankings.size());
        for(Ranking r : rankings){
            ArrayList<Record> aux = r.getRanking();
            for(Record auxr : aux){
                System.out.println("Record -> " + auxr.getnomJugador() + " " + auxr.getTime().get_time());
            }
        }
        gs.guardarRankings(this.rankings);
    }

    public char getTipusCela() {
        return this.game.getConf().getcell();
    }
    public int getN(){
        return this.game.getTauler().getN();
    }
    public int getK(){
        return this.game.getTauler().getK();
    }

    public String getDificultat() {
        return this.game.getConf().getDificultat();
    }
    public String getAdj() {
        return this.game.getConf().getAdjacencia();
    }

    public String getNomJugador() {
        return this.game.getJugador().getNom();
    }

    public Celda[][] getTaulerCeles() {
        return this.game.getTauler().getTauler();
    }

    public String getTempsPartida() {
        return this.game.getTiempo().get_time();
    }

    public int getLast() {
        return game.getTauler().getLast();
    }

    public boolean[] getUsats() {
        return game.getTauler().getUsats();
    }


    //La clase Gestor es de tipo Singleton, es decir q hay solo una instancia de gestor
    //Para conseguir que una clase sea de tipo Singleton necesitamos en primer lugar que su constructor sea privado.
    // De esa forma ningún programa será capaz de construir objetos de esta tipo .
    // En segundo lugar necesitaremos disponer de una variable estatica privada que almacene una referencia al objeto que vamos a crear a traves del constructor .
    // Por ultian método estático publico que se encarga de instanciar el objeto la primera vez y almacenarlo en la variable estática.
}


