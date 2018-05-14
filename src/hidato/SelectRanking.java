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
                vrank.label1.setText(value);
                String value2 = (String)comboBox2.getSelectedItem();
                vrank.label2.setText(value2);
                String value3 = (String)comboBox3.getSelectedItem();
                vrank.label3.setText(value3);
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

