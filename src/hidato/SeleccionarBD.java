package hidato;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SeleccionarBD {
    private JButton tornarButton;
    private JButton OKButton;
    private JPanel seleccionarBD;
    private JPanel southPanel;
    private JComboBox comboBox1;
    private JPanel northPanel;
    private JProgressBar progressBar1;
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
                    System.out.println("Interrupted!");
                    threadsolver.interrupt();
                    threadsolver = null;
                }
                CtrlPresentacio.getSingletonInstance().setContentFrame(new PartidaNova().getPanel());
            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar1.setVisible(true);
                if(threadsolver != null && !threadsolver.isInterrupted()){
                    threadsolver.interrupt();
                    System.out.println("Interrupted!");
                    threadsolver = null;
                }
                String nom = (String) comboBox1.getSelectedItem();
                String naux = "marc";
                //TODO arreglar threads!!!
                threadsolver = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gestor.getSingletonInstance().crearPartidaBD(nom, naux);
                        }
                        catch (Exception e1) {
                            if(e1 instanceof InterruptedException){
                                System.err.println("Me han matado");
                                Thread.currentThread().interrupt();
                                return;
                            }
                            JOptionPane.showMessageDialog(new JFrame(),
                                    "Aquest hidato no te solució!, selecciona un altre",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            progressBar1.setVisible(false);
                            return;
                        }
                        progressBar1.setVisible(false);
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

    public JPanel getPanel(){
        return seleccionarBD;
    }

}
