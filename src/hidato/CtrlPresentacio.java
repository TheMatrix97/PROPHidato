package hidato;

import javax.swing.*;

//sigleton com el domini
public class CtrlPresentacio {
    private Gestor g;
    private MenuInici menuInici;

    private static CtrlPresentacio presentacio;

    //constructora singleton
    private CtrlPresentacio() {
        g = Gestor.getSingletonInstance();
    }

    public static CtrlPresentacio getSingletonInstance() {
        if (presentacio == null){
            presentacio = new CtrlPresentacio();
        }
        return presentacio;
    }

    public void iniMenu(){
        JFrame frame = new JFrame("MenuInici");
        frame.setContentPane(new MenuInici().getMenuIni());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
