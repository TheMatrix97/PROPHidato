import hidato.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DriverGestorSaves {
    private static Partida p;
    private static ArrayList<Ranking> r;
    private static GestorSaves g;

    public static void main(String[] args) throws IOException {
        GestorSaves g = new GestorSaves();
        int opt = 0;
        boolean control = true;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        printa_menu();
        while(control) {
            String cadena = br.readLine();
            try {
                opt = Integer.parseInt(cadena);
            }catch(NumberFormatException e){
                opt = 100;
            }
            switch (opt) {
                case 1:
                    if (p == null) {
                        p = new Partida();
                        System.out.println("Partida creada");
                    } else System.out.println("Ja tenim una instacia de partida!");
                    break;
                case 2:
                    if (r == null) {
                        r = new ArrayList<>();
                        Ranking ranq = new Ranking(new Configuracio("Facil", "C", 'Q')); //configuracio de test
                        RankingTest.llegir_rank_fitxer(ranq); //llegim el fitxer RecordsData de bd y carreguem dades
                        r.add(ranq);
                        System.out.println("Ranking creat!");
                    } else System.out.println("Ja tenim un conjunt de rankings");
                    break;
                case 3:
                    if (r != null && !r.isEmpty()) {
                        int contador = 1;
                        for (Ranking raux : r) {
                            System.out.println("Ranking " + contador);
                            for (Record recaux : raux.getRanking()) {
                                System.out.println(recaux.getTime().get_time().toString() + ' ' + recaux.getnomJugador());
                            }
                            System.out.println("------------------");
                            contador++;
                        }
                    } else {
                        System.out.println("No tenim cap ranking");
                    }
                    break;
                case 4:
                    if (r != null && !r.isEmpty()) {
                        g.guardarRankings(r);
                        System.out.println("guardat!");

                    } else {
                        System.out.println("No tenim cap ranking!");
                    }
                    break;
                case 5:
                    ArrayList<Ranking> raux = new ArrayList<>();
                    try {
                        raux = g.cargar_ranking();
                    } catch (Exception e) {
                        if (e instanceof IOException) {
                            System.out.println("Error al llegir el fitxer de save!");
                        } else System.out.println("No tenim cap conjunt guardat!");
                    }
                    System.out.println("S'ha carregat correctament! :)");
                    r = raux;
                    break;
                case 6:
                    r = null;
                    break;
                case 7:
                    if(g.eliminar_data("ranking")){
                        System.out.println("S'ha eliminat correctament!");
                    }else {
                        System.out.println("Error!");
                    }
                    break;
                case 8:
                    if(p != null) {
                        g.guardar_partida(p);
                        System.out.println("Partida guardada");
                    }else System.out.println("error! no s'ha inicialitzat la instancia de Partida");
                    break;

                case 9:
                    try {
                        p = g.cargar_partida();
                    }catch (Exception e){
                        System.out.println("error al carregar la partida!");
                        break;
                    }
                    System.out.println("Partida carregada!");
                    break;

                case 10:
                    control = false;
                    break;
                default:
                    System.out.println("Opcio no valida!");


            }
        }

    }
    private static void printa_menu(){
        System.out.println("Driver del Gestor de dades guardades: ");
        System.out.println("Selecciona l'opcio:");
        System.out.println("1.Crear instancia Partida");
        System.out.println("2.Crear instancia de conjunt de rankings (ArrayList)(amb 1 ranking amb dades de la BD)");
        System.out.println("3.Imprimir ranking de test");
        System.out.println("4.Guarda conjunt de rankings a bd");
        System.out.println("5.Carrega conjunt de rankings de bd");
        System.out.println("6.Neteja instancia de conjunt de rankings");
        System.out.println("7.Neteja save conjunt de rankings");
        System.out.println("8.Guarda partida");
        System.out.println("9.Carrega partida");
        System.out.println("10.Exit");

    }
}
