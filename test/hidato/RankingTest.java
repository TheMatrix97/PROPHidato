package hidato;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RankingTest {
    //Test get ranking
    @Test
    public void getRanking() throws IOException{
        Ranking leaderboard = new Ranking(new Configuracio("Facil","C", 'Q'));
        llegir_rank_fitxer(leaderboard);
        ArrayList<Record> lista = leaderboard.getRanking();
        for(Record a : lista){
            System.out.println(a.getTime().get_time() + ' ' + a.getnomJugador());
        }
    }
    public static void llegir_rank_fitxer(Ranking leaderboard) throws IOException {
        String cadena;
        String filePath = new File("").getAbsolutePath();
        FileReader f = new FileReader(filePath+"/test/hidato/RecordsData.txt");
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine()) != null){
            String[] aux = cadena.split(" ");
            Time t = new Time(Long.parseLong(aux[0]));
            Record r = new Record(t,aux[1]);
            leaderboard.addRecord(r);
        }
        b.close();
    }
}