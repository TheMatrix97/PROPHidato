

import hidato.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DriverPartida {
    private static Partida p;
    private static Configuracio c;

    public static void main(String[] args) throws IOException {
        p = null;
        c = null;
        boolean control = true;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        printa_menu();
        int opt = 0;
        while (control) {
            String cadena = br.readLine();
                try {
                    opt = Integer.parseInt(cadena);
                }catch(NumberFormatException e){
                    opt = 5;
                }
            switch (opt){
                case 1:
                    c = genera_config();
                    break;
                case 2:
                    if(c == null){
                        System.out.println("Primer crea una instancia de configuracio!");
                        break;
                    }
                    p = new Partida();
                    p.generar_partida_random(c);
                    System.out.println("Tauler generat: " + p.getTauler().getN() + ", " + p.getTauler().getK());
                    MaquinaTest.recorreCeldas(p.getTauler().getTauler());
                    System.out.println("Solucio: ");
                    MaquinaTest.recorreCeldas(p.getTaulerSolucio().getTauler());
                    break;
                default:

            }
        }
    }

    private static Configuracio genera_config() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        System.out.print("Adjacencia: ");
        String adj = br.readLine();
        System.out.print("Tipus Cela: ");
        char t = br.readLine().charAt(0);
        System.out.print("1Dificultat(Facil,Normal,Dificil): ");
        String dif = br.readLine();
        return new Configuracio(dif,adj,t);
    }

    private static void printa_menu(){
        System.out.println("Driver del Gestor de dades partida: ");
        System.out.println("Selecciona l'opcio:");
        System.out.println("1.Crea configuracio");
        System.out.println("2.Crea partida amb tauler generat");

    }

}
