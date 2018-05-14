package hidato;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

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
        tornarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(threadsolver != null && !threadsolver.isInterrupted()){
                    threadsolver.interrupt();
                    threadsolver = null;
                }
                CtrlPresentacio.getSingletonInstance().setContentFrame(new PartidaNova().getPanel());
            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = (String) comboBox1.getSelectedItem();
                String naux;
                try{
                    naux = getName();
                }catch(Utils.ExceptionNomNoValid ex){
                    return;
                }
                //TODO encpasular Thread en una clase nova per evitar repetir codi aqui i a generarTauler
                threadsolver = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar1.setVisible(true);
                        OKButton.setEnabled(false);
                        comboBox1.setEnabled(false);
                        try {
                            Gestor.getSingletonInstance().crearPartidaBD(nom, naux);
                        }
                        catch (Exception e1) {
                            if(!Thread.currentThread().isInterrupted()) {
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
                        //AQUI ES CRIDA AL FRAME DE PARTIDA
                        Partida p = Gestor.getSingletonInstance().getPartida();
                        System.out.println("Nom user: " + p.getJugador().getNom());
                        System.out.println("Tipus cela: " + p.getConf().getcell());
                        System.out.println("Adj: " + p.getConf().getAdjacencia());
                        System.out.println("Dificultat: " + p.getConf().getDificultat());
                        System.out.println("Tauler: ");
                        Utils.printa_tauler(p.getTauler().getTauler());
                    }
                });
                threadsolver.start();
            }
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
