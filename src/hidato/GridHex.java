package hidato;

import javax.swing.*;


public class GridHex extends GridGame {
    public GridHex(JPanel gamePanel, Celda[][] mapaCeldas) {
        super(gamePanel,mapaCeldas);
    }

    @Override
    public int incrementoIY() {
        return 50;
    }

    @Override
    public int incrementoJY(int y) {
        if(y % 2 == 0) return -25;
        else return 25;
    }

    @Override
    public int incrementoJX() {
        return 43;
    }

    @Override
    public JButton[][] obteArrayCelda() {
        return new HexButton[super.getI()][super.getJ()];
    }

    @Override
    public JButton ObteCelda(boolean act, boolean orientacio) {
        return new HexButton(act);
    }
}
