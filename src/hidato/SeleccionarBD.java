package hidato;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 *
 * @author marc.catrisse & lluis.marques
 */
public class SeleccionarBD {
    private JButton tornarButton;
    private JButton OKButton;
    private JPanel seleccionarBD;
    private JPanel southPanel;
    private JComboBox comboBox1;
    private JPanel northPanel;
    private JProgressBar progressBar1;
    private JTextField textField1;
    private Thread threadsolver;

    public SeleccionarBD() {
        progressBar1.setVisible(false);
        threadsolver = null;
        ArrayList<String> llista = GestorBD.llista_hidatos_disponibles();
        for(String aux : llista){
            comboBox1.addItem(aux);
        }
        tornarButton.addActionListener(e -> {
            if(threadsolver != null && !threadsolver.isInterrupted()){
                threadsolver.interrupt();
                threadsolver = null;
            }
            CtrlPresentacio.getSingletonInstance().setContentFrame(new PartidaNova().getPanel());
        });
        OKButton.addActionListener(e -> {
            String nom = (String) comboBox1.getSelectedItem();
            String naux;
            final String[] nomJ = new String[1];
            final String[] adj = new String[1];
            final String[] dif = new String[1];
            try{
                naux = getName();
            }catch(Utils.ExceptionNomNoValid ex){
                return;
            }
            //TODO encpasular Thread en una clase nova per evitar repetir codi aqui i a generarTauler
            threadsolver = new Thread(() -> {
                progressBar1.setVisible(true);
                OKButton.setEnabled(false);
                comboBox1.setEnabled(false);
                try {
                    CtrlPresentacio.getSingletonInstance().crearPartidaBD(nom, naux);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    if (!Thread.currentThread().isInterrupted()) {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Aquest hidato no te solució!, selecciona un altre",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    progressBar1.setVisible(false);
                    OKButton.setEnabled(true);
                    comboBox1.setEnabled(true);
                    return;
                }
                progressBar1.setVisible(false);
                OKButton.setEnabled(true);
                comboBox1.setEnabled(true);
                CtrlPresentacio.getSingletonInstance().start_partida();
            });
            threadsolver.start();
        });
    }

    private String getName() throws Utils.ExceptionNomNoValid {
        String aux = textField1.getText();
        aux = aux.replace(" ", "");
        if(!aux.isEmpty()) return aux;
        JOptionPane.showMessageDialog(new JFrame(),"Inserta un nom de jugador!");
        throw new Utils.ExceptionNomNoValid();
    }

    public JPanel getPanel(){
        return seleccionarBD;
    }

}
