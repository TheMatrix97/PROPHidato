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
        framePartida.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePartida.pack();
        framePartida.setVisible(true);
    }

    public PartidaView() {
        setValues();
        //createGrid();
        MENUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //CtrlPresentacio.getSingletonInstance().iniMenu();
                framePartida.setVisible(false);
            }
        });
    }

    public void createGrid(){
        int i = Gestor.getSingletonInstance().getPartida().getTauler().getN();
        int j = Gestor.getSingletonInstance().getPartida().getTauler().getK();
            JLabel[][] grid = new JLabel[i][j];
            for(int x = 0; x < i; ++x){
                for(int y = 0; y < j; ++j) {
                    grid[x][y] = new JLabel(String.valueOf(i), CENTER);
                    grid[x][y].setBorder(new LineBorder(Color.BLACK));
                    gamePanel.add(grid[x][y]);
                }
        }
        grid[0][0].setBackground(Color.white);
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
