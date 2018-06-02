package hidato;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author lluis.marques
 */
public class TriButton extends JButton{
    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 96;
    private static final int WIDTH = 96;
    private boolean b;
    private boolean rev;
    private Polygon tri;
    private Color color;
    private Shape shape;


    public TriButton(boolean b, boolean rev, Color color){
        //b = true -> pintar triangle, rev = true -> triangle invers!
        this.b = b;
        this.rev = rev;
        this.color = color;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public  void paintComponent(Graphics g){
        super.paintComponent(g);
        tri = new Polygon();
        if(!rev) {
            tri.addPoint(0, 0); //cantonada esq
            tri.addPoint(WIDTH, 0); //cantonada dreta
            tri.addPoint(WIDTH/2, HEIGHT); //punta amunt
        }else{
            tri.addPoint(0, HEIGHT); //cantonada esq
            tri.addPoint(WIDTH, HEIGHT); //cantonada dreta
            tri.addPoint(WIDTH/2, 0); //punta avall
        }
        g.setColor(color);
        if(this.b) g.fillPolygon(tri);
        g.drawPolygon(tri);
    }

    @Override
    public boolean contains(int x, int y){
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Rectangle(0, 0, getWIDTH() - 24, getHEIGHT() - 24);
        }
        return shape.contains(x, y);
    }
    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }
}
