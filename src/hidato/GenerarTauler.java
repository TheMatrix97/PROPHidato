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

    public GenerarTauler() {
        tornarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().setContentFrame(new PartidaNova().getPanel());
            }
        });
    }

    public JPanel getPanel(){
        return generarT;
    }
}
