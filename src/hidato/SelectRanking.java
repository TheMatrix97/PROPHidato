package hidato;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectRanking {
    private JPanel generarR;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JButton tornarButton;
    private JButton OKButton;
    private JPanel insertarConf;
    private JPanel southPanel;

    public SelectRanking() {
        tornarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().iniMenu();
            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vranking vrank = new Vranking();
                String value = (String)comboBox1.getSelectedItem();
                char forma =' ';
                switch (value) {
                    case "Quadrat":
                        forma = 'Q';
                        break;
                    case "Triangle":
                        forma = 'T';
                        break;
                    case  "Hexagon":
                        forma = 'H';
                }
                vrank.SetJPanel1(value);
        //        vrank.label1.setText(value);
                String value2 = (String)comboBox2.getSelectedItem();
                String adj ="";
                switch (value2) {
                    case "Costats":
                        adj = "C";
                        break;
                    case "Costats+angles":
                        adj = "CA";
                }
           //     vrank.label2.setText(value2);
                vrank.SetJPanel2(value2);
                String value3 = (String)comboBox3.getSelectedItem();
           //     vrank.label3.setText(value3);
                vrank.SetJPanel3(value3);
                vrank.SetConfiguracio(value3,adj,forma);
              //  vrank.conf = new Configuracio(value3,adj,forma);
                vrank.FerTaula();
                CtrlPresentacio.getSingletonInstance().setContentFrame(vrank.getPanel());
            }
        });
  /*          @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
   */ }

    public JPanel getPanel() {
        return generarR;
    }
}

