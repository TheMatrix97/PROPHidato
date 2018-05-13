package hidato;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PartidaNova {
    private JButton carregarDeLaBaseButton;
    private JButton generarTaulerAleatoriButton;
    private JButton tornarButton;
    private JPanel partidaNova;
    private JPanel northPanel;
    private JPanel southPanel;

    public PartidaNova() {
        tornarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().iniMenu();
            }
        });
    }

    public JPanel getPanel(){
        return partidaNova;
    }
}
