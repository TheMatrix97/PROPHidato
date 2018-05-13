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
                String value = comboBox1.getSelectedItem().toString();
                vrank.textField1.setText(value);
                String value2 = comboBox2.getSelectedItem().toString();
                vrank.textField2.setText(value2);
                String value3 = comboBox3.getSelectedItem().toString();
                vrank.textField3.setText(value3);
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

