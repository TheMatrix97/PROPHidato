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

    public SeleccionarBD() {
        ArrayList<String> llista = GestorBD.llista_hidatos_disponibles();
        for(String aux : llista){
            comboBox1.addItem(aux);
        }
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
