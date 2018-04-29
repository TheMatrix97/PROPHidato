

import hidato.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DriverGestor {
    private static Gestor g;

    public static void main(String[] args) throws Exception {
        g = Gestor.getSingletonInstance();
        Partida p = null;
        Configuracio c = null;
        boolean control = true;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        System.out.println("Com et dius?: ");
        String nomj = br.readLine();
        Jugador jug = new Jugador(nomj);
        printa_menu();
        int opt;
        while (control) {
            String cadena = br.readLine();
                try {
                    opt = Integer.parseInt(cadena);
                }catch(NumberFormatException e){
                    opt = 200;
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
                    g.crearPartidaBuida(jug.getNom());
                    p = g.getPartida();
                    p.generar_partida_random(c);
                    System.out.println("Tauler generat: " + p.getTauler().getN() + ", " + p.getTauler().getK());
                    Celda[][] t = p.getTauler().getTauler();
                    printa_tauler(t);
                    break;
                case 3:
                    ArrayList<String> noms = GestorBD.llista_hidatos_disponibles();
                    for(String n : noms){
                        System.out.println(noms.indexOf(n)+ " -> " + n);
                    }
                    int num;
                    while(true) {
                        System.out.println("Selecciona un tauler de la BD");
                        String n = br.readLine();
                        try{
                            num = Integer.parseInt(n);
                        }catch(NumberFormatException e){
                            continue;
                        }
                        if(num >= 0 && num < noms.size()) break;
                    }
                    System.out.println("Carregant hidato....");
                    try {
                        g.crearPartidaBD(noms.get(num), jug.getNom());
                    }catch(Exception e){
                        System.out.println("Tauler no valid!");
                        p = null;
                        break;
                    }
                    p = g.getPartida();
                    printa_tauler(p.getTauler().getTauler());


                    break;
                case 4:
                    if(p == null){
                        System.out.println("Primer crea una instancia de partida!");
                        break;
                    }
                    Celda[][] sol = p.getTaulerSolucio().getTauler();
                    printa_tauler(sol);
                    break;
                case 5:
                    //jugada;
                    if(p == null){
                        System.out.println("Primer crea una instancia de partida!");
                        break;
                    }
                    Tauler encurs = p.getTauler();
                    int[] coord = demanaJugada(encurs.getN(),encurs.getK(),true);
                    boolean resolt = false;
                    try{
                        g.ferJugada(coord[0],coord[1],coord[2]);
                    }catch (Utils.ExceptionJugadaNoValida e){
                        System.out.println("Jugada no vàlida :(");
                        break;
                    }catch(Utils.ExceptionTaulerResolt e){
                        resolt = true;
                    }
                    printa_tauler(encurs.getTauler());
                    if(resolt){
                        System.out.println("Tauler Resolt en: " + p.getTiempo().get_time());
                    }
                    break;
                case 6:
                    if(p == null){
                        System.out.println("Primer crea una instancia de partida!");
                        break;
                    }
                    Tauler en = p.getTauler();
                    int[] coord2 = demanaJugada(en.getN(),en.getK(),false);
                    try{
                        g.ferJugadaDel(coord2[0],coord2[1]);
                    }catch (Utils.ExceptionJugadaNoValida e){
                        System.out.println("Jugada no vàlida :(");
                        break;
                    }
                    printa_tauler(en.getTauler());
                    break;
                case 7:
                    if(p == null){
                        System.out.println("Primer crea una instancia de partida!");
                        break;
                    }
                    try{
                        g.demanarAjuda();
                    }catch(Utils.ExceptionTaulerResolt e){
                        System.out.println("Tauler Resolt en: " + p.getTiempo().get_time());
                    }
                    printa_tauler(p.getTauler().getTauler());
                    break;
                case 8:
                    if(p == null){
                        System.out.println("Primer crea una instancia de partida!");
                        break;
                    }
                    Configuracio co = p.getConf();
                    System.out.println("Adj: " + co.getAdjacencia() + " Tipus de cela: " + co.getcell() + " Dificultat: " + co.getDificultat());
                    break;
                case 9:
                    System.out.println("Ranking: ");
                    ArrayList<Ranking> r = g.getRankings();
                    for(Ranking rank : r){
                        ArrayList<Record> records = rank.getRanking();
                        Configuracio conf = rank.getConf();
                        System.out.println("Ranking: " + conf.getcell() + " " + conf.getAdjacencia() + " " + conf.getDificultat());
                        for(Record rec : records){
                            System.out.println(rec.getnomJugador() + " -> " + rec.getTime().get_time());
                        }
                        System.out.println("--------------------");
                    }
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
        boolean valido = false;
        String adj = null;
        String dif = null;
        char t = 'T';
        while(!valido) {
            System.out.print("Adjacencia (C,CA): ");
            adj = br.readLine();
            valido = adj.equals("C") || adj.equals("CA");
        }
        valido = false;
        while(!valido) {
            System.out.print("Tipus Cela (T,Q,H): ");
            t = br.readLine().charAt(0);
            valido = t == 'Q' || t == 'T' || t == 'H';
        }
        valido = false;
        while(!valido){
            System.out.print("Dificultat (Facil,Normal,Dificil): ");
            dif = br.readLine();
            valido = dif.equals("Facil") || dif.equals("Normal") || dif.equals("Dificil");
        }
        System.out.println("Configuracio creada!");
        return new Configuracio(dif,adj,t);
    }
    private static int[] demanaJugada(int imax, int jmax, boolean ins) throws IOException {
        int[] a = new int[3];
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        boolean valido = false;
        while(!valido){
            System.out.print("i: ");
            String i = br.readLine();
            System.out.print("j: ");
            String j = br.readLine();
            int auxi,auxj;
            try {
                auxi = Integer.parseInt(i);
                auxj = Integer.parseInt(j);
            }catch(NumberFormatException e){
                System.out.println("Error de lectura, torna a intentar-ho");
                continue;
            }
            if((auxi >= 0 && auxi < imax) && (auxj >= 0 && auxj < jmax)){
                valido = true;
                a[0] = auxi;
                a[1] = auxj;
                if(ins){
                    System.out.print("Numero: ");
                    try{
                        a[2] = Integer.parseInt(br.readLine());
                    }catch (NumberFormatException e){
                        System.out.println("Error de lectura, torna a intentar-ho");
                        valido = false;
                    }

                }
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
        System.out.println("3.Crea partida amb tauler de la BD");
        System.out.println("4.Mostra solucio!");
        System.out.println("5.Fes jugada per insertar num");
        System.out.println("6.Fes jugada per eliminar num");
        System.out.println("7.Demanar ajuda");
        System.out.println("8.Printa configuració actual");
        System.out.println("9.Printa rankings");

    }

}
