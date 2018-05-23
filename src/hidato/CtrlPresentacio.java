package hidato;

import javax.swing.*;

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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

    public Partida getPartida(){
        return g.getPartida();
    }

    public void guardarPartida(){
        g.guardarPartida();
    }

    public void cargarPartida() throws Exception {
        g.cargarPartida();
    }

    public boolean existePartidaGuardada(){
        return Gestor.getSingletonInstance().partidaGuardada();
    }

    public void demanarHelp() throws Utils.ExceptionTaulerResolt {
        g.demanarAjuda();
    }

    public Celda[][] getTaulerdeCelles(){
        return g.getPartida().getTauler().getTauler();
    }

    public int sacaN(){
        return  g.getPartida().getTauler().getN();
    }

    public int sacaK(){
        return g.getPartida().getTauler().getK();
    }

    public void ferJugadaIns(int i, int j, int num) throws Utils.ExceptionJugadaNoValida, Utils.ExceptionTaulerResolt {
        g.getPartida().fesJugadaIns(i,j,num);
    }

    public void ferJugadaDel(int i, int j) throws Utils.ExceptionJugadaNoValida {
        g.getPartida().fesJugadaDel(i, j);
    }

    public void crearPartidaBD(String nom, String naux) throws Exception {
        g.crearPartidaBD(nom, naux);
    }

    public String getTimerinoPartida(){
        return g.getPartida().getTiempo().get_time();
    }

    public char getTcela(){
        return g.getPartida().getConf().getcell();
    }

    public void start_partida(){
        JFrame framePartida = new JFrame("PartidaView");
        new PartidaView(framePartida);
        framePartida.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void orientacioHexagon(){

    }

    public boolean getOrientacioTriangle(int x, int y, char t) {
        return g.getOrientacioTriangle(x,y,t);
    }
}
