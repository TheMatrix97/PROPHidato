package hidato;

import javax.swing.*;

public class GridTri extends GridGame {
    public GridTri(JPanel gamePanel, Celda[][] mapaCeldas) {
        super(gamePanel, mapaCeldas);
    }

    @Override
    public int incrementoIY() {
        return 50;
    }

    @Override
    public int incrementoJX() {
        return 28;
    }

    @Override
    public int incrementoJY(int y) {
        return 50;
    }

    public JButton[][] obteArrayCelda(){
        return new TriButton[super.getI()][super.getJ()];
    }
    public JButton ObteCelda(boolean act, boolean orientacio){
        return new TriButton(act,orientacio);
    }
}
