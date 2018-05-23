package hidato;

import javax.swing.*;


public abstract class GridGame {
    private JPanel gamePanel;
    private Celda[][] mapaCeldas;
    private int i, j;

    public GridGame(JPanel gamePanel, Celda[][] mapaCeldas) {
        this.gamePanel = gamePanel;
        this.mapaCeldas = mapaCeldas;
        this.i = mapaCeldas.length;
        this.j = mapaCeldas[0].length; //no hauria de petar, ja que no admetem taulers buids

    }
    abstract public JButton[][] pintaGrid();

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public Celda[][] getMapaCeldas() {
        return mapaCeldas;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean getOrientacioTri(int x, int y, char t) {
        return CtrlPresentacio.getSingletonInstance().getOrientacioTriangle(x,y,t);
    }

}
