package hidato;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeleccionarBD {
    private JButton tornarButton;
    private JButton OKButton;
    private JPanel seleccionarBD;
    private JPanel southPanel;
    private JComboBox comboBox1;

    public SeleccionarBD() {
        tornarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().setContentFrame(new PartidaNova().getPanel());
            }
        });
    }

    public JPanel getPanel(){
        return seleccionarBD;
    }
}
