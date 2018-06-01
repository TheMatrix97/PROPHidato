package hidato;

import javax.swing.*;
import java.awt.*;

public class GridTri extends GridGame {
    public GridTri(JPanel gamePanel, Celda[][] mapaCeldas) {
        super(gamePanel, mapaCeldas);
    }

    @Override
    public int offiniY() {
        return 0;
    }

    @Override
    public int incrementoIY(int j) {
        return 50;
    }

    @Override
    public int incrementoJX() {
        return 28;
    }

    @Override
    public int incrementoJY(int y) {
        return 0;
    }

    public JButton[][] obteArrayCelda(){
        return new TriButton[super.getI()][super.getJ()];
    }
    public JButton ObteCelda(boolean act, boolean orientacio, Color c){
        return new TriButton(act,orientacio,c);
    }
}
