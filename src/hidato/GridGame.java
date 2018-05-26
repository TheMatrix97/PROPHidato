package hidato;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;


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
    public JButton[][] pintaGrid(){
        //mapa celda
        JButton[][] fieldG = obteArrayCelda();
        gamePanel.setBackground(Color.white);
        int offsetX = 0, offsetY = offiniY(); //a hexagons es diferent
        for(int x = 0; x < i; ++x) {
            for (int y = 0; y < j; ++y) {
                boolean orientacio = getOrientacioTri(x,y,'T');
                if (!mapaCeldas[x][y].isValida()) {
                    fieldG[x][y] = ObteCelda(true,orientacio,Color.ORANGE);
                    if (mapaCeldas[x][y].isFrontera()) fieldG[x][y].setVisible(false); //Es un "#", no el volem mostrar!
                    fieldG[x][y].setEnabled(false); //NO EL PODEM SOBRESCRIURE
                } else {
                    fieldG[x][y] = ObteCelda(false,orientacio,Color.ORANGE);
                    if (mapaCeldas[x][y].isPrefijada()) {
                        if(mapaCeldas[x][y].getValor() == CtrlPresentacio.getSingletonInstance().getLast() || mapaCeldas[x][y].getValor() == 1){
                            fieldG[x][y] = ObteCelda(false,orientacio,Color.BLUE);
                        }
                        fieldG[x][y].setText(String.valueOf(mapaCeldas[x][y].getValor()));
                        fieldG[x][y].setEnabled(false); //SON PREFIXADES; NO LES PODEM MODIFICAR!
                    } else if (!mapaCeldas[x][y].isVacia()) { //si es una celda de una partida cargada puede tener un numero dentro
                        fieldG[x][y].setText(String.valueOf(mapaCeldas[x][y].getValor()));
                    }
                }
                fieldG[x][y].setBounds(offsetX, offsetY, 50, 50);
                fieldG[x][y].setHorizontalAlignment(CENTER);
                gamePanel.add(fieldG[x][y]);
                System.out.println("seguent: " + offsetX + "," + offsetY);
                offsetX += incrementoJX();
                offsetY += incrementoJY(y);
            }
            offsetY += incrementoIY(j); //HEX usa j para decidir
            offsetX = 0;
        }
        return fieldG;
    }
    public abstract int offiniY();
    public abstract int incrementoIY(int j);
    public abstract int incrementoJY(int y); //se usa y en hex
    public abstract int incrementoJX();
    public abstract JButton[][] obteArrayCelda();
    public abstract JButton ObteCelda(boolean act, boolean orientacio, Color c);

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
