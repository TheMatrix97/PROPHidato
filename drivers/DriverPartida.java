

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
                    c = genera_config(); //todo controla input
                    break;
                case 2:
                    if(c == null){
                        System.out.println("Primer crea una instancia de configuracio!");
                        break;
                    }
                    p = new Partida();
                    p.generar_partida_random(c);
                    System.out.println("Tauler generat: " + p.getTauler().getN() + ", " + p.getTauler().getK());
                    Celda[][] t = p.getTauler().getTauler();
                    printa_tauler(t);
                    break;
                case 3:
                    Celda[][] sol = p.getTaulerSolucio().getTauler();
                    printa_tauler(sol);
                    break;
                case 4:
                    //jugada;
                    Tauler encurs = p.getTauler();
                    int[] coord = demanaJugada(encurs.getN(),encurs.getK());
                    try{
                        p.fesJugadaIns(coord[0],coord[1],coord[2]);
                    }catch (Utils.ExceptionJugadaNoValida e){
                        System.out.println("Jugada no vàlida :(");
                        break;
                    }
                    Celda[][] estructuraEncurs = encurs.getTauler();
                    printa_tauler(estructuraEncurs);
                    break;
                case 5:
                    Tauler en = p.getTauler();
                    int[] coord2 = demanaJugada(en.getN(),en.getK());
                    try{
                        p.fesJugadaDel(coord2[0],coord2[1]);
                    }catch (Utils.ExceptionJugadaNoValida e){
                        System.out.println("Jugada no vàlida :(");
                        break;
                    }
                    printa_tauler(en.getTauler());
                    break;

                default:

            }
        }
    }

    private static void printa_tauler(Celda[][] aux){
        boolean first;
        for(Celda[] c : aux){
            first = true;
            for(Celda celda : c){
                if(!first) System.out.print(",");
                else first = false;
                if(celda.isFrontera())System.out.print("#");
                else if(!celda.isValida()) System.out.print("*");
                else if(celda.isVacia() && celda.isValida()) System.out.print("?");
                else System.out.print(celda.getValor());
            }
            System.out.print("\n");
        }
    }

    private static Configuracio genera_config() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        System.out.print("Adjacencia: ");
        String adj = br.readLine();
        System.out.print("Tipus Cela: ");
        char t = br.readLine().charAt(0);
        System.out.print("Dificultat(Facil,Normal,Dificil): ");
        String dif = br.readLine();
        return new Configuracio(dif,adj,t);
    }
    private static int[] demanaJugada(int imax, int jmax) throws IOException {
        int[] a = new int[3];
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        boolean valido = false;
        while(!valido){
            System.out.print("i: ");
            String i = br.readLine();
            System.out.print("j: ");
            String j = br.readLine();
            int auxi = Integer.parseInt(i);
            int auxj = Integer.parseInt(j);
            if((auxi >= 0 && auxi < imax) && (auxj >= 0 && auxj < jmax)){
                valido = true;
                a[0] = auxi;
                a[1] = auxj;
                System.out.print("Numero: ");
                a[2] = Integer.parseInt(br.readLine());
            }else{
                System.out.println("Vuelve a intentar");
            }
        }
        return a;
    }

    private static void printa_menu(){
        System.out.println("Driver del Gestor de dades partida: ");
        System.out.println("Selecciona l'opcio:");
        System.out.println("1.Crea configuracio");
        System.out.println("2.Crea partida amb tauler generat");
        System.out.println("3.Mostra solucio!");
        System.out.println("4.Fes jugada");

    }

}
