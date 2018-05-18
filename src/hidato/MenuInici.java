package hidato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 *
 * @author marc.catrisse & lluis.marques
 */

public class MenuInici {
    //MENU INICI
    private JButton novaPartidaButton;
    private JButton carregarPartidaButton;
    private JButton sortirButton;
    private JPanel MenuInici;
    private JLabel imageIni;
    private JButton musicButton;
    private JPanel southpanel;

    //Partida nova
    private PartidaNova partidaNova;

    private boolean music;
    private MusicPlayer player;


    public MenuInici() {
        //setCarregarPartidaButton();
        partidaNova = new PartidaNova();
        music = true;
        player = new MusicPlayer("narutoInicio.wav", true);
        player.start();
        Color background = new Color(255,255,255);
        MenuInici.setBackground(background);
        southpanel.setBackground(background);
        sortirButton.addActionListener(e -> System.exit(0));
        musicButton.addActionListener(e -> {
            if(music){
                music = false;
                player.stopPlayback();
                musicButton.setText("Music ON");
            }else{
                music = true;
                player.startPlayback();
                musicButton.setText("Music OFF");
            }
        });
        novaPartidaButton.addActionListener(e -> CtrlPresentacio.getSingletonInstance().setContentFrame(partidaNova.getPanel()));
        carregarPartidaButton.addActionListener(e -> {
            try {
                CtrlPresentacio.getSingletonInstance().cargarPartida();
                CtrlPresentacio.getSingletonInstance().start_partida();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(new JFrame(),
                        "Error al carregar la partida");
            }

        });
    }


    private void createUIComponents() {
        String path = new File("").getAbsolutePath();
        // TODO: place custom component creation code here
        imageIni = new JLabel(new ImageIcon(path+"/res/hidato1.png"));

    }
    public JPanel getMenuIni(){
        return MenuInici;
    }

    public void setCarregarPartidaButton(){
        CtrlPresentacio  ctrl = CtrlPresentacio.getSingletonInstance();
        carregarPartidaButton.setEnabled(ctrl.existePartidaGuardada());
    }

}
