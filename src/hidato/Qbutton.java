package hidato;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author lluis.marques
 */
public class Qbutton extends JButton {
    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 48;
    private static final int WIDTH = 48;
    private boolean b;

    public Qbutton(boolean b){
        //b = true -> pintar triangle, rev = true -> triangle invers!
        this.b = b;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public  void paintComponent(Graphics g){
        super.paintComponent(g);
        Polygon quad = new Polygon();
        quad.addPoint(0, 0); //abaix esq
        quad.addPoint(0, HEIGHT); //adalt esq
        quad.addPoint(WIDTH, HEIGHT); //adalt dreta
        quad.addPoint(WIDTH, 0); //abaix dreta
        g.setColor(Color.ORANGE);
        if(this.b) g.fillPolygon(quad);
        g.drawPolygon(quad);
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }
}
