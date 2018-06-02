package hidato;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author lluis.marques
 */
//Following class draws the Buttons
class HexButton extends JButton {
    private static final long serialVersionUID = 1L;
    private static final int SIDE_LENGTH = 48;
    private static final int LENGTH = 96;
    private static final int WIDTH = 106;
    private Color color;
    private boolean b;


    public HexButton(boolean b, Color c) {
        super();
        this.b = b;
        this.color = c;
        setContentAreaFilled(false);
        setFocusPainted(true);
        setBorderPainted(false);
        setPreferredSize(new Dimension(WIDTH, LENGTH));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Polygon hex = new Polygon();
        for(int i = 0; i < 6; ++i) hex.addPoint((int) (50 + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / 6)), (int) (50 + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / 6)));
        g.setColor(color);
        if(this.b) g.fillPolygon(hex);
        g.drawPolygon(hex);
    }

    public static int getLENGTH() {
        return LENGTH;
    }

    public static int getWIDTH() {
        return WIDTH;
    }
}