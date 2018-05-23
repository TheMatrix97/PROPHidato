package hidato;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;

public class GridHex extends GridGame {
    public GridHex(JPanel gamePanel, Celda[][] mapaCeldas) {
        super(gamePanel,mapaCeldas);
    }

    public JButton[][] pintaGrid(){
        int i = super.getI();
        int j = super.getJ();
        JButton[][] fieldG = new HexButton[i][j];
        Celda[][] mapacelda = super.getMapaCeldas();
        super.getGamePanel().setBackground(Color.white);
        int offsetX = 0, offsetY = 25;
        for(int x = 0; x < i; ++x){
            for(int y = 0; y < j; ++y) {
                if(!mapacelda[x][y].isValida()){
                    fieldG[x][y] = new HexButton(true);
                    if(mapacelda[x][y].isFrontera()) fieldG[x][y].setVisible(false); //Es un "#", no el volem mostrar!
                    fieldG[x][y].setEnabled(false); //NO EL PODEM SOBRESCRIURE
                }
                else{
                    fieldG[x][y] = new HexButton(false);
                    if(mapacelda[x][y].isPrefijada()){ //Casella buida/prefixada
                            //obtenim el valor per mostrar-lo
                            fieldG[x][y].setText(String.valueOf(mapacelda[x][y].getValor()));
                            fieldG[x][y].setEnabled(false); //SON PREFIXADES; NO LES PODEM MODIFICAR!
                    }
                    else if(!mapacelda[x][y].isVacia()){ //si es una celda de una partida cargada puede tener un numero dentr
                        fieldG[x][y].setText(String.valueOf(mapacelda[x][y].getValor()));
                    }
                }
                fieldG[x][y].setBounds(offsetX, offsetY, 50, 50);
                fieldG[x][y].setHorizontalAlignment(CENTER);
                super.getGamePanel().add(fieldG[x][y]);
                if (y % 2 == 0) offsetY -= 25;
                else offsetY += 25;
                offsetX += 43;
            }
            offsetY += 50;
            offsetX = 0;
        }
        return fieldG;
    }
}
