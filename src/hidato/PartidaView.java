package hidato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.SwingConstants.CENTER;

/**
 *
 * @author marc.catrisse & lluis.marques
 */
public class PartidaView {
    private JLabel nomJugador;
    private JLabel time;
    private JButton SAVEButton;
    private JButton HELPButton;
    private JButton MENUButton;
    private JPanel bigPanel;
    private JPanel southPanel;
    private JPanel northPanel;
    private JLabel difLabel;
    private JLabel adjLabel;
    private JLabel timeLabel;
    private JLabel nomJLabel;
    private JPanel gamePanel;
    private JTextField valorJugada;
    private JFrame framePartida;
    private JButton[][] fieldG;
    private Celda[][] c;
    private Thread timerinoCapuccino;
    private boolean resolt = false;


    public PartidaView(JFrame frame) {
        this.framePartida = frame;
        c = CtrlPresentacio.getSingletonInstance().getTaulerdeCelles();
        setValues();
        valorJugada.setText(nextValue());
        char tcela = CtrlPresentacio.getSingletonInstance().getTipusCela();
        setTaulerLayout(tcela); //configurem el layout per H / T / Q
        createGrid();
        actTimer();
        addListeners();
        //carreguem el frame i el mostem
        generateJScroll();
        framePartida.setExtendedState(framePartida.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        framePartida.setContentPane(bigPanel);
        framePartida.pack();
        framePartida.setVisible(true);

        SAVEButton.addActionListener(e -> {
            CtrlPresentacio.getSingletonInstance().guardarPartida();
            JOptionPane.showMessageDialog(new JFrame(),
                    "Partida guardada!");
        });
        HELPButton.addActionListener(e -> {
            try {
                CtrlPresentacio.getSingletonInstance().demanarHelp();
                valorJugada.setText(nextValue());
                recalcular_Matrix();
            } catch (Utils.ExceptionTaulerResolt exceptionTaulerResolt) {
                recalcular_Matrix();
                resolt = true;
                stopTimerino();
                //System.out.println("Estic al thread del timer" + timerinoCapuccino.getName() + " id: " + timerinoCapuccino.getId());
                JOptionPane.showMessageDialog(new JFrame(),
                        "GOOD GAME!\n Guardant record...");
                end_game();
            }
        });
        framePartida.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                //CtrlPresentacio.getSingletonInstance().iniMenu();
                framePartida.dispose();
            }
        });
        MENUButton.addActionListener(e -> {
            CtrlPresentacio.getSingletonInstance().iniMenu();
            framePartida.dispose();
        });
    }

    private void addListeners() {
        for(int i = 0; i < fieldG.length; ++i){
            for(int j = 0; j < fieldG[i].length; ++j){
                fieldG[i][j].addMouseListener(new MyListener(i, j));
            }
        }
    }

    public void stopTimerino(){
        timerinoCapuccino.interrupt();
        timerinoCapuccino = null;
    }
    public void recalcular_Matrix(){
        for(int i = 0; i < fieldG.length; ++i){
            for(int j = 0; j < fieldG[0].length; ++j){
                if(!c[i][j].isVacia() && !c[i][j].isPrefijada() && c[i][j].isValida()) {
                    fieldG[i][j].setText(String.valueOf(c[i][j].getValor()));
                }
            }
        }
    }

    private void generateJScroll(){
        JScrollPane j = new JScrollPane(gamePanel);
        j.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        j.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bigPanel.add(j);
    }

    private void createGrid() {
        char tcela = CtrlPresentacio.getSingletonInstance().getTipusCela();
        Celda[][] mapacelda = CtrlPresentacio.getSingletonInstance().getTaulerdeCelles();
        GridGame grid;
        switch (tcela) {
            case 'T':
                grid = new GridTri(gamePanel,mapacelda);
                break;
            case 'H':
                grid = new GridHex(gamePanel,mapacelda);
                break;
            default:
                grid = new GridQuad(gamePanel,mapacelda);
                break;
        }
        this.fieldG = grid.pintaGrid();
    }

    private void setValues(){
        CtrlPresentacio c = CtrlPresentacio.getSingletonInstance();
        //TODO lo deberia hacer Ctrlpresentacio?
        difLabel.setText(c.getDificultat());
        adjLabel.setText(c.getAdj());
        nomJLabel.setText(c.getNomJugador());
    }

    private class MyListener implements MouseListener {
        private int i, j;
        private MyListener(int i, int j){
            this.i = i;
            this.j = j;

        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)) {
                try {
                    int valorIns = Integer.parseInt(valorJugada.getText());
                    CtrlPresentacio.getSingletonInstance().ferJugadaIns(this.i, this.j, valorIns);
                    fieldG[this.i][this.j].setText(valorJugada.getText());
                    valorJugada.setText(nextValue());
                } catch (Utils.ExceptionJugadaNoValida exceptionJugadaNoValida) {
                    exceptionJugadaNoValida.printStackTrace();
                } catch (Utils.ExceptionTaulerResolt exceptionTaulerResolt) {
                    fieldG[this.i][this.j].setText(valorJugada.getText());
                    resolt = true;
                    timerinoCapuccino.interrupt();
                    timerinoCapuccino = null;
                    //System.out.println("Estic al thread del timer" + timerinoCapuccino.getName() + " id: " + timerinoCapuccino.getId());
                    JOptionPane.showMessageDialog(new JFrame(),
                            "GOOD GAME!\n Guardant record...");
                    recalcular_Matrix(); //Necessari per l'ultim valor, a lo chapuza
                    end_game();
                }
            }else if(SwingUtilities.isRightMouseButton(e)) {
                try {
                    CtrlPresentacio.getSingletonInstance().ferJugadaDel(this.i, this.j);
                    fieldG[this.i][this.j].setText("");
                    valorJugada.setText(nextValue());
                } catch (Utils.ExceptionJugadaNoValida exceptionJugadaNoValida) {
                    exceptionJugadaNoValida.printStackTrace();
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private String nextValue(){
        boolean[] al = CtrlPresentacio.getSingletonInstance().getUsats();
        for(int i = 0; i < al.length; ++i){
            if(!al[i]) return String.valueOf(i);
        }
        return null;
    }
    private void end_game(){
        CtrlPresentacio.getSingletonInstance().stopTimer();
        System.out.println("Fin!, voy a guardar");
        CtrlPresentacio.getSingletonInstance().salvar_rankings();
        framePartida.dispose();
    }

    private void actTimer() {
        timerinoCapuccino = new Thread(() -> {
           //System.out.println("Estic al thread del timer" + timerinoCapuccino.getName() + " id: " + timerinoCapuccino.getId());
           Timer timer = new Timer(1000, e -> {
               //System.out.println("Actualitzant timer...");
               if (!resolt) timeLabel.setText(CtrlPresentacio.getSingletonInstance().getTimerinoPartida());
           });
           timer.start();
        });
        timerinoCapuccino.start();
    }

    private void setTaulerLayout(char tcela){ //funció que aplica el layout segons el tcela(H i T) i fixa el min i max size
        int i = CtrlPresentacio.getSingletonInstance().getN();
        int j = CtrlPresentacio.getSingletonInstance().getK();
        int height; //per defecte quadrat
        int width;
        switch(tcela){
            case 'T':
                height = TriButton.getHEIGHT();
                width = TriButton.getWIDTH();
                break;
            case 'H':
                height = HexButton.getLENGTH();
                width = HexButton.getWIDTH();
                break;
            default:
                height = Qbutton.getHEIGHT();
                width  = Qbutton.getWIDTH();
                break;
        }

        int margen = 50;
        height = height * (i+1) + margen;
        width = width * (j+1) + margen;
        System.out.println("H: " + height + " w:" + width);
        int finalHeight = height;
        int finalWidth = width;
        //Dimension screen = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        gamePanel.setLayout(new LayoutManager() {
            @Override
            public void addLayoutComponent(String name, Component comp) {

            }

            @Override
            public void removeLayoutComponent(Component comp) {

            }

            @Override
            public Dimension preferredLayoutSize(Container parent) {
                //if(finalHeight > screen.getHeight()) return new Dimension(screen.width, screen.height);
                //if(finalWidth > screen.getWidth()) return new Dimension(screen.width, screen.height);
                return new Dimension(finalWidth, finalHeight);
            }

            @Override
            public Dimension minimumLayoutSize(Container parent) {
                return new Dimension(finalWidth, finalHeight);
            }

            @Override
            public void layoutContainer(Container parent) {

            }
        });
    }
}
