package hidato;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JFrame framePartida;

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
    }

    public void createQGrid(){
        int i = Gestor.getSingletonInstance().getPartida().getTauler().getN();
        int j = Gestor.getSingletonInstance().getPartida().getTauler().getK();
        gamePanel.setLayout(new GridLayout(i, j));
        gamePanel.setBackground(Color.gray);
        JTextField[][] fieldG = new JTextField[i][j];
        Celda[][] c = Gestor.getSingletonInstance().getPartida().getTauler().getTauler();
        for(int x = 0; x < i; ++x){
                for(int y = 0; y < j; ++y) {
                    fieldG[x][y] =  new JTextField();
                    fieldG[x][y].setHorizontalAlignment(CENTER);
                    fieldG[x][y].setPreferredSize(new Dimension(2,8));
                    if(!c[x][y].isValida()){
                        if(!c[x][y].isFrontera()) {
                            fieldG[x][y].setBackground(Color.ORANGE); //ES UN '*'
                            fieldG[x][y].setEditable(false); //NO EL PODEM SOBRESCRIURE
                        }
                        else fieldG[x][y].setVisible(false); //Es un "#", no el volem mostrar!
                    }
                    else if(c[x][y].isPrefijada()){
                        //obtenim el valor per mostrar-lo
                        fieldG[x][y].setText(String.valueOf(c[x][y].getValor()));
                        fieldG[x][y].setBackground(Color.CYAN);
                        fieldG[x][y].setEditable(false); //SON PREFIXADES; NO LES PODEM MODIFICAR!
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
}
