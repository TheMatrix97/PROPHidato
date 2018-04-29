package hidato;

import java.io.*;
import java.util.ArrayList;

/**
    @author Marc.Catrisse
 */

public class GestorSaves {
    private String filesPath;

    //constructora
    public GestorSaves(){
        this.filesPath = new File("").getAbsolutePath() + "/saves/";
    }

    //funció encarregada de guardar l'array de ranking del paràmetre, al guardar es sobreescriu el contingut
    public void guardarRankings(ArrayList<Ranking> r){
        try{
            ObjectOutputStream oos = getObjectOutputStream( "ranking.hidato");
            oos.writeObject(r);
            oos.close();
        }catch(IOException io) {
            io.printStackTrace();
            System.out.println("No es pot guardar el ranking"); //TODO aixo ho fara capa de presentació
        }
    }

    //funció encarregada de carregar el ranking
    public ArrayList<Ranking> cargar_ranking() throws Exception{
        Object o = existe_obj("rankings");
        ArrayList<?> a =(ArrayList<?>)o;
        ArrayList<Ranking> r;
        if(!a.isEmpty() && a.get(0) instanceof Ranking) { //todo da warning pero no se como solucionarlo
            r = (ArrayList<Ranking>)o;
        }else throw new Exception("error al leer");
        return r;
    }

    //funció per guardar l'objecte partida, funciona
    public void guardar_partida(Partida game){
        try{
            ObjectOutputStream oos = getObjectOutputStream( "save.hidato");
            oos.writeObject(game);
            oos.close();
        }catch(IOException io) {
            System.out.println("No es pot guardar la partida"); //TODO aixo ho fara capa de presentació
        }
    }

    //funció encarregada de carregar la partida guardada a si existeix
    public Partida cargar_partida() throws Exception{
        Partida pendiente = (Partida)existe_obj("partida");
        vaciarSave("save.hidato");
        return pendiente;
    }

    //funció publica per buidar saves
    public boolean eliminar_data(String type){
        String filename;
        if(type.equals("partida")){
            filename = "save.hidato";
        }else{
            filename = "ranking.hidato";
        }
        try{
            vaciarSave(filename);
        }catch(Exception e){
            return false;
        }
        return true;
    }


    //funció auxiliar per obrir els saves .hidato, si no el troba el crea
    private ObjectOutputStream getObjectOutputStream(String name) throws IOException {
        ObjectOutputStream oss;
        try {
            oss = new ObjectOutputStream(new FileOutputStream(filesPath + name));
        }catch(FileNotFoundException e){
            File f = new File(filesPath+name);
            if(f.createNewFile()){
                return new ObjectOutputStream(new FileOutputStream(filesPath + name));
            }else throw new IOException("no es pot crear el save");
        }
        return oss;
    }

    //buida el save, s'utilitza al càrregar una partida, ja que la càrrega d'una partida es destructiva per especificació
    private void vaciarSave(String filename) throws IOException {
        BufferedWriter wr = new BufferedWriter(new FileWriter(filesPath + filename));
        wr.write("");
        wr.close();
    }

    //funció privada auxiliar per obtenir objectes guardats
    private Object existe_obj(String type) throws IOException, ClassNotFoundException { //si existeix torna l'objecte, sino null
        String nameFile;
        if(type.equals("partida")) nameFile = "save.hidato";
        else nameFile = "ranking.hidato";
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filesPath + nameFile));
        Object aux = ois.readObject();
        if((aux instanceof Partida && type.equals("partida")) || (aux instanceof ArrayList<?> && type.equals("rankings"))){
            return aux;
        }
        else{
            throw new ClassNotFoundException("Class incorrecta");
        }
    }
}
