package hidato;

import javax.swing.*;
import java.awt.*;


public class GridQuad extends GridGame{
    public GridQuad(JPanel gamePanel, Celda[][] mapaCeldas) {
        super(gamePanel, mapaCeldas);
    }

    @Override
    public int incrementoIY(int j) {
        return 100;
    }

    @Override
    public int incrementoJY(int y) {
        return 0;
    }

    @Override
    public int offiniY() {
        return 0;
    }

    @Override
    public int incrementoJX() {
        return 100;
    }

    @Override
    public JButton[][] obteArrayCelda() {
        return new Qbutton[super.getI()][super.getJ()];
    }

    @Override
    public JButton ObteCelda(boolean act, boolean orientacio, Color c) {
        return new Qbutton(act,c);
    }
}
