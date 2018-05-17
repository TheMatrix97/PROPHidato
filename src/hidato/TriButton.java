package hidato;

import javax.swing.*;
import java.awt.*;

public class TriButton extends JButton{
    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 48;
    private static final int WIDTH = 48;
    private Polygon tri;
    public Color c;
    private boolean b;
    private boolean rev;


    public TriButton(boolean b, boolean rev){
        //b = true -> pintar triangle, rev = true -> triangle invers!
        this.b = b;
        this.rev = rev;
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
        g.setColor(Color.ORANGE);
        if(this.b) g.fillPolygon(tri);
        g.drawPolygon(tri);
    }
}