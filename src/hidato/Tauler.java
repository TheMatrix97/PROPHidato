package hidato;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Tauler {
    int i, j;
    int[] prefixats;
    int[] invalides;
    boolean[] usats;
    Celda[][] tauler;

    //Constructora en base un arxiu de la base de dades de Hidato (carreguem un fitxer amb el hidato)
    public Tauler(String idHidato) throws IOException {
        String cadena;
        String filePath = new File("").getAbsolutePath();
        FileReader f = new FileReader(filePath+ "/BaseDadesHidatos/" + idHidato + ".txt");
        BufferedReader b = new BufferedReader(f);
        boolean first = true;
        while((cadena = b.readLine()) != null){
            String[] aux = cadena.split(",");
            if(first){
                first = false;
                Configuracio c = new Configuracio()

            }
            Time t = new Time(Long.parseLong(aux[0]));
            Record r = new Record(t,aux[1]);
            leaderboard.addRecord(r);
        }
    }

    //Crea un tauler random en base una configuració donada
    public Tauler(Configuracio c){
        switch (c.getDificultat()){
            case "Facil":
            case "Normal":
            case default:
        }
    }
}
