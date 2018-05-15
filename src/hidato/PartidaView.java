package hidato;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;
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


    public void main(String[] args) {
        framePartida = new JFrame("PartidaView");
        framePartida.setContentPane(new PartidaView().bigPanel);
        framePartida.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        framePartida.pack();
        framePartida.setVisible(true);
    }

    public PartidaView() {
        setValues();
        createQGrid(); //Grid pels Quadrats!!!!!
        for(int i = 0; i < fieldG.length; ++i){
            for(int j = 0; j < fieldG[0].length; ++j){
                fieldG[i][j].addActionListener(new MyListener(i, j));
            }
        }
        HELPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Gestor.getSingletonInstance().demanarAjuda();
                    recalcular_Matrix();
                } catch (Utils.ExceptionTaulerResolt exceptionTaulerResolt) {
                    recalcular_Matrix(); //Necessari per l'ultim valor, a lo chapuza
                    exceptionTaulerResolt.printStackTrace();
                }
            }
        });
    }

    public void recalcular_Matrix(){
        for(int i = 0; i < fieldG.length; ++i){
            for(int j = 0; j < fieldG[0].length; ++j){
                if(!c[i][j].isVacia()) {
                    fieldG[i][j].setText(String.valueOf(c[i][j].getValor()));
                }
            }
        }
    }

    public void createQGrid(){
        int i = Gestor.getSingletonInstance().getPartida().getTauler().getN();
        int j = Gestor.getSingletonInstance().getPartida().getTauler().getK();
        gamePanel.setLayout(new GridLayout(i, j));
        gamePanel.setBackground(Color.gray);
        fieldG = new JButton[i][j];
        c = Gestor.getSingletonInstance().getPartida().getTauler().getTauler();
        for(int x = 0; x < i; ++x){
                for(int y = 0; y < j; ++y) {
                    fieldG[x][y] =  new JButton();
                    fieldG[x][y].setHorizontalAlignment(CENTER);
                    fieldG[x][y].setPreferredSize(new Dimension(2,8));
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
                    }
                    gamePanel.add(fieldG[x][y]);
                }
        }
    }

    public void setValues(){
        difLabel.setText(Gestor.getSingletonInstance().getPartida().getConf().getDificultat());
        adjLabel.setText(Gestor.getSingletonInstance().getPartida().getConf().getAdjacencia());
        timeLabel.setText(Gestor.getSingletonInstance().getPartida().getTiempo().get_time());
        nomJLabel.setText(Gestor.getSingletonInstance().getPartida().getJugador().getNom());
    }

    public JPanel getPanel(){
        return bigPanel;
    }

    private class MyListener implements ActionListener {
        private int i, j;
        public MyListener(int i, int j){
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
                    Gestor.getSingletonInstance().getPartida().fesJugadaIns(this.i,this.j, valorIns);
                    ++valorIns;
                }
                else Gestor.getSingletonInstance().getPartida().fesJugadaDel(this.i,this.j);
                fieldG[this.i][this.j].setText(valorJugada.getText());
                if(!del) valorJugada.setText(nextValue());
            } catch (Utils.ExceptionJugadaNoValida exceptionJugadaNoValida) {
                exceptionJugadaNoValida.printStackTrace();
            } catch (Utils.ExceptionTaulerResolt exceptionTaulerResolt) {
                fieldG[this.i][this.j].setText(valorJugada.getText());
                JOptionPane.showMessageDialog(new JFrame(),
                        "GOOD GAME!\n Guardant record...");
                exceptionTaulerResolt.printStackTrace();
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
}
