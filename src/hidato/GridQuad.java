package hidato;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;

public class GridQuad extends GridGame{
    public GridQuad(JPanel gamePanel, Celda[][] mapaCeldas) {
        super(gamePanel, mapaCeldas);
    }
    public JButton[][] pintaGrid() {
        int i = super.getI();
        int j = super.getJ();
        JButton[][] fieldG = new Qbutton[i][j];
        Celda[][] mapacelda = super.getMapaCeldas();
        super.getGamePanel().setBackground(Color.white);
        int offsetX = 0, offsetY = 0;
        for(int x = 0; x < i; ++x) {
            for (int y = 0; y < j; ++y) {
                if (!mapacelda[x][y].isValida()) {
                    fieldG[x][y] = new Qbutton(true);
                    if (mapacelda[x][y].isFrontera()) fieldG[x][y].setVisible(false); //Es un "#", no el volem mostrar!
                    fieldG[x][y].setEnabled(false); //NO EL PODEM SOBRESCRIURE
                } else {
                    fieldG[x][y] = new Qbutton(false);
                    if (mapacelda[x][y].isPrefijada()) {
                        fieldG[x][y].setText(String.valueOf(mapacelda[x][y].getValor()));
                        fieldG[x][y].setEnabled(false); //SON PREFIXADES; NO LES PODEM MODIFICAR!
                    } else if (!mapacelda[x][y].isVacia()) { //si es una celda de una partida cargada puede tener un numero dentro
                        fieldG[x][y].setText(String.valueOf(mapacelda[x][y].getValor()));
                    }
                }
                fieldG[x][y].setBounds(offsetX, offsetY, 50, 50);
                fieldG[x][y].setHorizontalAlignment(CENTER);
                super.getGamePanel().add(fieldG[x][y]);
                offsetX += 50;
            }
            offsetY += 50;
            offsetX = 0;
        }
        return fieldG;
    }

}
