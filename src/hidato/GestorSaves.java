package hidato;

import java.io.*;
import java.util.ArrayList;

public class GestorSaves {
    private String filesPath;

    public GestorSaves(){
        this.filesPath = new File("").getAbsolutePath() + "/saves/";
    }
    public void guardarRankings(ArrayList<Ranking> r){ //todo codi repetit
        try{
            ObjectOutputStream oos = getObjectOutputStream( "ranking.hidato");
            oos.writeObject(r);
            oos.close();
        }catch(IOException io) {
            io.printStackTrace();
            System.out.println("No es pot guardar el ranking"); //TODO aixo ho fara capa de presentació
        }
    }
    public ArrayList<Ranking> cargar_ranking() throws Exception{ // todo es gestiona l'excepcio fora... esta bé?
        Object o = existe_obj("rankings");
        ArrayList<?> a =(ArrayList<?>)o;
        ArrayList<Ranking> r = null;
        if(!a.isEmpty() && a.get(0) instanceof Ranking) { //todo da warning pero no se como solucionarlo
            r = (ArrayList<Ranking>)o;
        }else throw new Exception("error al leer");
        return r;
    }


    public void guardar_partida(Partida game){
        try{
            ObjectOutputStream oos = getObjectOutputStream( "save.hidato");
            oos.writeObject(game);
            oos.close();
        }catch(IOException io) {
            System.out.println("No es pot guardar la partida"); //TODO aixo ho fara capa de presentació
        }
    }

    private ObjectOutputStream getObjectOutputStream(String name) throws IOException {
        ObjectOutputStream oss = null;
        try {
            oss = new ObjectOutputStream(new FileOutputStream(filesPath + name));
        }catch(FileNotFoundException e){
            e.printStackTrace();
            File f = new File(filesPath+name);
            if(f.createNewFile()){
                return new ObjectOutputStream(new FileOutputStream(filesPath + name));
            }else throw new IOException("no es pot crear el save");
        }
        return oss;
    }

    public Partida cargar_partida() throws Exception{ // todo es gestiona l'excepcio fora... esta bé?
        Partida pendiente = (Partida)existe_obj("partida");
        vaciarSave("save.hidato");
        return pendiente;
    }

    private void vaciarSave(String filename) throws IOException {
        BufferedWriter wr = new BufferedWriter(new FileWriter(filesPath + filename));
        wr.write("");
        wr.close();
    }

    private Object existe_obj(String type) throws IOException, ClassNotFoundException { //si existe devuelve la partida, sino null
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
}
