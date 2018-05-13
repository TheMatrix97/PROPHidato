package hidato;

import javax.swing.*;

//sigleton com el domini
public class CtrlPresentacio {
    private Gestor g;
    private MenuInici menuInici;
    private JFrame frame;

    private static CtrlPresentacio presentacio;

    //constructora singleton
    private CtrlPresentacio() {
        menuInici = new MenuInici();
        g = Gestor.getSingletonInstance();
        frame = new JFrame("MenuInici");
    }

    public static CtrlPresentacio getSingletonInstance() {
        if (presentacio == null){
            presentacio = new CtrlPresentacio();
        }
        return presentacio;
    }

    public void iniMenu(){
        frame.setContentPane(menuInici.getMenuIni());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setContentFrame(JPanel j){
        frame.setContentPane(j);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
