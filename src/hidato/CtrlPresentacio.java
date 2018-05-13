package hidato;

import javax.swing.*;

public class CtrlPresentacio {
    private Gestor g;
    private MenuInici menuInici;

    public CtrlPresentacio(){
        g = Gestor.getSingletonInstance();
    }

    public void iniMenu(){
        JFrame frame = new JFrame("MenuInici");
        frame.setContentPane(new MenuInici().getMenuIni());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
