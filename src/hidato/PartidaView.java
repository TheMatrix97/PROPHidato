package hidato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.SwingConstants.CENTER;

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
    private boolean b = true;
    private Thread timerinoCapuccino;
    private boolean resolt = false;


    public PartidaView(JFrame frame) {
        this.framePartida = frame;
        setValues();
        createQGrid(); //Grid!!!!!
        if(b){
            actTimer();
            b = false;
        }
        for(int i = 0; i < fieldG.length; ++i){
            for(int j = 0; j < fieldG[0].length; ++j){
                fieldG[i][j].addActionListener(new MyListener(i, j));
            }
        }
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().guardarPartida();
                JOptionPane.showMessageDialog(new JFrame(),
                        "Partida guardada!");
            }
        });
        HELPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CtrlPresentacio.getSingletonInstance().demanarHelp();
                    recalcular_Matrix();
                } catch (Utils.ExceptionTaulerResolt exceptionTaulerResolt) {
                    recalcular_Matrix();
                    resolt = true;
                    timerinoCapuccino.interrupt();
                    timerinoCapuccino = null;
                    //System.out.println("Estic al thread del timer" + timerinoCapuccino.getName() + " id: " + timerinoCapuccino.getId());
                    JOptionPane.showMessageDialog(new JFrame(),
                            "GOOD GAME!\n Guardant record...");
                    end_game();
                }
            }
        });
        framePartida.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                CtrlPresentacio.getSingletonInstance().iniMenu();
                super.windowClosed(e);
            }
        });
        MENUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().iniMenu();
                framePartida.setVisible(false);
            }
        });
    }
    private void recalcular_Matrix(){
        for(int i = 0; i < fieldG.length; ++i){
            for(int j = 0; j < fieldG[0].length; ++j){
                if(!c[i][j].isVacia() && !c[i][j].isPrefijada() && c[i][j].isValida()) {
                    fieldG[i][j].setText(String.valueOf(c[i][j].getValor()));
                }
            }
        }
    }

    private void createQGrid(){
        int i = CtrlPresentacio.getSingletonInstance().sacaN();
        int j = CtrlPresentacio.getSingletonInstance().sacaK();
        char tcela = CtrlPresentacio.getSingletonInstance().getTcela();
        System.out.println("Tcela: " + tcela);
        switch (tcela){
            case 'H':
                fieldG = new HexButton[i][j];
                gamePanel.setLayout(null);
                break;
            default:
                fieldG = new JButton[i][j];
                gamePanel.setLayout(new GridLayout(i, j));
                break;
        }
        gamePanel.setBackground(Color.white);
        c = CtrlPresentacio.getSingletonInstance().getTaulerdeCelles();
        int offsetX = -10, offsetY = 0;
        for(int x = 0; x < i; ++x){
                for(int y = 0; y < j; ++y) {
                    switch (tcela){
                        case 'H':
                            fieldG[x][y] = new HexButton();
                            fieldG[x][y].setBounds(offsetY, offsetX, 50, 50);
                            break;
                        default:
                            fieldG[x][y] = new JButton();
                    }
                    fieldG[x][y].setHorizontalAlignment(CENTER);
                    if(!c[x][y].isValida()){
                        if(!c[x][y].isFrontera()) {
                            fieldG[x][y].setBackground(Color.ORANGE); //ES UN '*'
                            fieldG[x][y].setEnabled(false); //NO EL PODEM SOBRESCRIURE
                        }
                        else fieldG[x][y].setVisible(false); //Es un "#", no el volem mostrar!
                    }
                    else if(c[x][y].isPrefijada()){
                        //obtenim el valor per mostrar-lo
                        fieldG[x][y].setText(String.valueOf(c[x][y].getValor()));
                        fieldG[x][y].setBackground(Color.CYAN);
                        fieldG[x][y].setEnabled(false); //SON PREFIXADES; NO LES PODEM MODIFICAR!
                    }else if(!c[x][y].isVacia()){ //si es una celda de una partida cargada puede tener un numero dentro
                        fieldG[x][y].setText(String.valueOf(c[x][y].getValor()));
                    }
                    gamePanel.add(fieldG[x][y]);
                    offsetX += 42;
                }
            if(x%2 == 0) offsetX = -33;
            else offsetX = -11;
            offsetY += 38;
        }
    }

    private void setValues(){
        Partida aux = CtrlPresentacio.getSingletonInstance().getPartida();
        //TODO lo deberia hacer Ctrlpresentacio?
        difLabel.setText(aux.getConf().getDificultat());
        adjLabel.setText(aux.getConf().getAdjacencia());
        //timeLabel.setText(aux.getTiempo().get_time());
        nomJLabel.setText(aux.getJugador().getNom());
    }

    public JPanel getPanel(){
        return bigPanel;
    }

    private class MyListener implements ActionListener {
        private int i, j;
        private MyListener(int i, int j){
            this.i = i;
            this.j = j;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            boolean del = false;
            if(valorJugada.getText().equals("")) del = true;
            try {
                System.out.println("del: " + del);
                if(!del){
                    int valorIns = Integer.parseInt(valorJugada.getText());
                    CtrlPresentacio.getSingletonInstance().ferJugadaIns(this.i, this.j, valorIns);
                    ++valorIns;
                }
                else CtrlPresentacio.getSingletonInstance().ferJugadaDel(this.i, this.j);
                fieldG[this.i][this.j].setText(valorJugada.getText());
                if(!del) valorJugada.setText(nextValue());
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
        }
    }

    private String nextValue(){
        boolean[] al = Gestor.getSingletonInstance().getPartida().getTauler().getUsats();
        for(int i = 1; i < al.length; ++i){
            if(!al[i]) return String.valueOf(i);
        }
        return null;
    }
    private void end_game(){

    }

    public void actTimer() {
        timerinoCapuccino = new Thread(new Runnable() {
            @Override
            public void run() {
               //System.out.println("Estic al thread del timer" + timerinoCapuccino.getName() + " id: " + timerinoCapuccino.getId());
               Timer timer = new Timer(1000, new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       //System.out.println("Actualitzant timer...");
                       if(!resolt) timeLabel.setText(CtrlPresentacio.getSingletonInstance().getTimerinoPartida());
                   }
               });
               timer.start();
            }
        });
        timerinoCapuccino.start();
    }
}
