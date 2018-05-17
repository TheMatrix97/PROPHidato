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
    private static final int SIDES = 6;
    private static final int SIDE_LENGTH = 24;
    private static final int LENGTH = 48;
    private static final int WIDTH = 53;
    private Polygon hex;
    public Color c;
    private boolean b;


    public HexButton(boolean b) {
        super();
        this.b = b;
        setContentAreaFilled(false);
        setFocusPainted(true);
        setBorderPainted(false);
        setPreferredSize(new Dimension(WIDTH, LENGTH));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        hex = new Polygon();
        for (int i = 0; i < SIDES; i++) {
            hex.addPoint((int) (25 + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / SIDES)), //calculation for side
                    (int) (25 + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / SIDES)));   //calculation for side
        }
        g.setColor(Color.ORANGE);
        if(this.b) g.fillPolygon(hex);
        g.drawPolygon(hex);
    }
}