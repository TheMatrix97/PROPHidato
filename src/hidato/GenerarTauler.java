package hidato;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public GenerarTauler() {
        tornarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().setContentFrame(new PartidaNova().getPanel());
            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gestor g = Gestor.getSingletonInstance();
                String name;
                try{
                    name = getName();
                }catch(Utils.ExceptionNomNoValid ex){
                    return;
                }
                g.crearPartidaConf(getOpcio("tcela").charAt(0),getOpcio("adj"),getOpcio("dif"),name);
                //TODO System.out 4 debug ELIMINAR!!!
                Partida p = g.getPartida();
                System.out.println("Nom user: " + p.getJugador().getNom());
                System.out.println("Tipus cela: " + p.getConf().getcell());
                System.out.println("Adj: " + p.getConf().getAdjacencia());
                System.out.println("Dificultat: " + p.getConf().getDificultat());
                System.out.println("Tauler: ");
                Utils.printa_tauler(p.getTauler().getTauler());
            }
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
