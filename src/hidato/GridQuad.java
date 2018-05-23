package hidato;

import javax.swing.*;


public class GridQuad extends GridGame{
    public GridQuad(JPanel gamePanel, Celda[][] mapaCeldas) {
        super(gamePanel, mapaCeldas);
    }

    @Override
    public int incrementoIY() {
        return 50;
    }

    @Override
    public int incrementoJY(int y) {
        return 0;
    }

    @Override
    public int incrementoJX() {
        return 50;
    }

    @Override
    public JButton[][] obteArrayCelda() {
        return new Qbutton[super.getI()][super.getJ()];
    }

    @Override
    public JButton ObteCelda(boolean act, boolean orientacio) {
        return new Qbutton(act);
    }
}
