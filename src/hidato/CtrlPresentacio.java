package hidato;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//sigleton com el domini
public class CtrlPresentacio {
    private Gestor g;
    private MenuInici menuInici;
    private JFrame frame;

    private static CtrlPresentacio presentacio;

    //constructora singleton
    private CtrlPresentacio() {
        menuInici = new MenuInici();
        g = Gestor.getSingletonInstance();
        frame = new JFrame("MenuInici");
    }

    public static CtrlPresentacio getSingletonInstance() {
        if (presentacio == null){
            presentacio = new CtrlPresentacio();
        }
        return presentacio;
    }
    public void iniMenu(){
        menuInici.setCarregarPartidaButton();
        frame.setContentPane(menuInici.getMenuIni());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setContentFrame(JPanel j){
        frame.setContentPane(j);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void crearPartida(String cell, String adj, String dif, String name){
        g.crearPartidaConf(cell.charAt(0), adj,dif, name);
    }

    public int getN(){
        return g.getN();
    }
    public int getK(){
        return g.getK();
    }
    public String getDificultat(){
        return g.getDificultat();
    }
    public String getAdj() {
        return g.getAdj();
    }
    public char getTipusCela(){
        return g.getTipusCela();
    }

    public void guardarPartida(){
        g.guardarPartida();
    }

    public void cargarPartida() throws Exception {
        g.cargarPartida();
    }

    public boolean existePartidaGuardada(){
        return g.partidaGuardada();
    }

    public void demanarHelp() throws Utils.ExceptionTaulerResolt {
        g.demanarAjuda();
    }

    public Celda[][] getTaulerdeCelles(){
        return g.getTaulerCeles();
    }

    public void ferJugadaIns(int i, int j, int num) throws Utils.ExceptionJugadaNoValida, Utils.ExceptionTaulerResolt {
        System.out.println("Jugada!");
        g.ferJugada(i,j,num);
    }

    public void ferJugadaDel(int i, int j) throws Utils.ExceptionJugadaNoValida {
        g.ferJugadaDel(i,j);
    }

    public void crearPartidaBD(String nom, String naux) throws Exception {
        g.crearPartidaBD(nom, naux);
    }

    public String getTimerinoPartida(){
        return g.getTempsPartida();
    }


    public void start_partida(){
        JFrame framePartida = new JFrame("PartidaView");
        new PartidaView(framePartida);
        framePartida.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public boolean getOrientacioTriangle(int x, int y, char t) {
        return g.getOrientacioTriangle(x,y,t);
    }
    public void salvar_rankings(){
        g.guardar_rankings();
    }
    public String getNomJugador(){
        return g.getNomJugador();
    }

    public ArrayList<Ranking> cargar_ranking(){
        return g.getRankings();
    }

    public int getLast() {
        return g.getLast();
    }

    public boolean[] getUsats() {
        return g.getUsats();
    }

    public void carregarTXTaBD(File f) {
        try {
            g.carregarTXTBD(f);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Error al llegir el fitxer",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void stopTimer() {
        g.stopTimer();
    }
}
