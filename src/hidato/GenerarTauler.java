package hidato;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author marc.catrisse & lluis.marques
 */
public class GenerarTauler {
    private JPanel generarT;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JButton tornarButton;
    private JButton OKButton;
    private JPanel insertarConf;
    private JPanel southPanel;
    private JTextField textField1;
    private JProgressBar progressBar1;
    private Thread threadSolver;

    public GenerarTauler() {
        threadSolver = null;
        tornarButton.addActionListener(e -> CtrlPresentacio.getSingletonInstance().setContentFrame(new PartidaNova().getPanel()));
        OKButton.addActionListener(e -> {
            String name;
            try{
                name = getName();
            }catch(Utils.ExceptionNomNoValid ex){
                return;
            }
            OKButton.setEnabled(false);
            threadSolver = new Thread(() -> {
                progressBar1.setVisible(true);
                //Gestor g = CtrlPresentacio.gestorSingleton();
                CtrlPresentacio.getSingletonInstance().crearPartida(getOpcio("tcela"), getOpcio("adj"), getOpcio("dif"), name);
                progressBar1.setVisible(false);
                OKButton.setEnabled(true);
                CtrlPresentacio.getSingletonInstance().start_partida();
            });
            threadSolver.start();
        });
    }
    private String getName() throws Utils.ExceptionNomNoValid {
        String aux = textField1.getText();
        aux = aux.replace(" ","");
        if(!aux.isEmpty()){
            return aux;
        }else{
            JOptionPane.showMessageDialog(new JFrame(),
                    "Inserta un nom de jugador!");
            throw new Utils.ExceptionNomNoValid();
        }
    }
    public JPanel getPanel(){
        return generarT;
    }

    private String getOpcio(String opcio){
        int aux;
        switch(opcio){
            case "tcela":
                aux = comboBox1.getSelectedIndex();
                switch (aux){
                    case 0:
                        return "Q";
                    case 1:
                        return "T";
                    default:
                        return "H";
                }
            case "adj":
                aux = comboBox2.getSelectedIndex();
                switch (aux){
                    case 0:
                        return "C";
                    default:
                        return "CA";
                }
            default:
                aux = comboBox3.getSelectedIndex();
                switch (aux){
                    case 0:
                        return "Facil";
                    case 1:
                        return "Normal";
                    default:
                        return "Dificil";
                }
        }
    }

}
