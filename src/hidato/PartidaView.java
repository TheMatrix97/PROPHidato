package hidato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.SwingConstants.CENTER;

/**
 *
 * @author marc.catrisse & lluis.marques
 */
public class PartidaView {
    private JLabel nomJugador;
    private JLabel time;
    private JButton SAVEButton;
    private JButton HELPButton;
    private JButton MENUButton;
    private JPanel bigPanel;
    private JPanel southPanel;
    private JPanel northPanel;
    private JLabel difLabel;
    private JLabel adjLabel;
    private JLabel timeLabel;
    private JLabel nomJLabel;
    private JPanel gamePanel;
    private JTextField valorJugada;
    private JFrame framePartida;
    private JButton[][] fieldG;
    private Celda[][] c;
    private boolean b = true;
    private Thread timerinoCapuccino;
    private boolean resolt = false;


    public PartidaView(JFrame frame) {
        this.framePartida = frame;
        setValues();
        valorJugada.setText(nextValue());
        char tcela = CtrlPresentacio.getSingletonInstance().getTcela();
        setTaulerLayout(tcela); //configurem el layout per H / T / Q
        switch(tcela){
            case 'H':
                createHGrid();
                break;
            case 'T':
                createTGrid();
                break;
            default:
                createQGrid();
                break;
        }
        if(b){
            actTimer();
            b = false;
        }
        for(int i = 0; i < fieldG.length; ++i){
            for(int j = 0; j < fieldG[i].length; ++j){
                fieldG[i][j].addActionListener(new MyListener(i, j));
            }
        }
        //carreguem el frame i el mostem
        generateJScroll();
        framePartida.setContentPane(bigPanel);
        framePartida.pack();
        framePartida.setVisible(true);

        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().guardarPartida();
                JOptionPane.showMessageDialog(new JFrame(),
                        "Partida guardada!");
            }
        });
        HELPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CtrlPresentacio.getSingletonInstance().demanarHelp();
                    recalcular_Matrix();
                } catch (Utils.ExceptionTaulerResolt exceptionTaulerResolt) {
                    recalcular_Matrix();
                    resolt = true;
                    timerinoCapuccino.interrupt();
                    timerinoCapuccino = null;
                    //System.out.println("Estic al thread del timer" + timerinoCapuccino.getName() + " id: " + timerinoCapuccino.getId());
                    JOptionPane.showMessageDialog(new JFrame(),
                            "GOOD GAME!\n Guardant record...");
                    end_game();
                }
            }
        });
        framePartida.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                CtrlPresentacio.getSingletonInstance().iniMenu();
                framePartida.dispose();
            }
        });
        MENUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().iniMenu();
                framePartida.dispose();
            }
        });
    }
    private void recalcular_Matrix(){
        for(int i = 0; i < fieldG.length; ++i){
            for(int j = 0; j < fieldG[0].length; ++j){
                if(!c[i][j].isVacia() && !c[i][j].isPrefijada() && c[i][j].isValida()) {
                    fieldG[i][j].setText(String.valueOf(c[i][j].getValor()));
                }
            }
        }
    }

    private void generateJScroll(){
        JScrollPane j = new JScrollPane(gamePanel);
        j.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        j.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bigPanel.add(j);
    }

    private void createTGrid(){
        int i = CtrlPresentacio.getSingletonInstance().sacaN();
        int j = CtrlPresentacio.getSingletonInstance().sacaK();
        fieldG = new TriButton[i][j];
        gamePanel.setBackground(Color.white);
        c = CtrlPresentacio.getSingletonInstance().getTaulerdeCelles();
        Tauler t = CtrlPresentacio.getSingletonInstance().getPartida().getTauler();
        int offsetX = 0, offsetY = 0;
        for(int x = 0; x < i; ++x){
            for(int y = 0; y < j; ++y) {
                boolean orientacio = t.orientacio(x,y,'T');
                if(!c[x][y].isValida()){
                    fieldG[x][y] = new TriButton(true, orientacio);
                    if(!c[x][y].isFrontera()) {
                        //ES UN '*'
                        fieldG[x][y].setEnabled(false); //NO EL PODEM SOBRESCRIURE
                    }
                    else{
                        fieldG[x][y].setVisible(false); //Es un "#", no el volem mostrar!
                    }
                }
                else if(c[x][y].isPrefijada()){ //Casella buida/prefixada
                    //obtenim el valor per mostrar-lo
                    fieldG[x][y] = new TriButton(false, orientacio);
                    fieldG[x][y].setText(String.valueOf(c[x][y].getValor()));
                    fieldG[x][y].setEnabled(false); //SON PREFIXADES; NO LES PODEM MODIFICAR!
                }else if(!c[x][y].isVacia()){ //si es una celda de una partida cargada puede tener un numero dentro
                    fieldG[x][y] = new TriButton(false, orientacio); //Casella buida/prefixada
                    fieldG[x][y].setText(String.valueOf(c[x][y].getValor()));
                }
                else{ //Casella buida!
                    fieldG[x][y] = new TriButton(false, orientacio);
                }
                fieldG[x][y].setBounds(offsetX, offsetY, 50, 50);
                fieldG[x][y].setHorizontalAlignment(CENTER);

                gamePanel.add(fieldG[x][y]);
                offsetX += 28;
            }
            offsetY += 50;
            offsetX = 0;
        }
    }

    private void createHGrid(){
        int i = CtrlPresentacio.getSingletonInstance().sacaN();
        int j = CtrlPresentacio.getSingletonInstance().sacaK();
        fieldG = new HexButton[i][j];
        gamePanel.setBackground(Color.white);
        c = CtrlPresentacio.getSingletonInstance().getTaulerdeCelles();
        int offsetX = 0, offsetY = +25;
        for(int x = 0; x < i; ++x){
            for(int y = 0; y < j; ++y) {
                if(!c[x][y].isValida()){
                    fieldG[x][y] = new HexButton(true);
                    if(!c[x][y].isFrontera()) {
                         //ES UN '*'
                        fieldG[x][y].setEnabled(false); //NO EL PODEM SOBRESCRIURE
                    }
                    else{
                        fieldG[x][y].setVisible(false); //Es un "#", no el volem mostrar!
                    }
                }
                else if(c[x][y].isPrefijada()){ //Casella buida/prefixada
                    //obtenim el valor per mostrar-lo
                    fieldG[x][y] = new HexButton(false);
                    fieldG[x][y].setText(String.valueOf(c[x][y].getValor()));
                    fieldG[x][y].setEnabled(false); //SON PREFIXADES; NO LES PODEM MODIFICAR!
                }else if(!c[x][y].isVacia()){ //si es una celda de una partida cargada puede tener un numero dentro
                    fieldG[x][y] = new HexButton(false); //Casella buida/prefixada
                    fieldG[x][y].setText(String.valueOf(c[x][y].getValor()));
                }
                else{ //Casella buida!
                    fieldG[x][y] = new HexButton(false);
                }
                fieldG[x][y].setBounds(offsetX, offsetY, 50, 50);
                fieldG[x][y].setHorizontalAlignment(CENTER);

                gamePanel.add(fieldG[x][y]);
                if (y % 2 == 0) offsetY -= 25;
                else offsetY += 25;
                offsetX += 43;
            }
            offsetY += 50;
            offsetX = 0;
        }
    }

    private void createQGrid(){
        int i = CtrlPresentacio.getSingletonInstance().sacaN();
        int j = CtrlPresentacio.getSingletonInstance().sacaK();
        fieldG = new Qbutton[i][j];
        gamePanel.setBackground(Color.white);
        c = CtrlPresentacio.getSingletonInstance().getTaulerdeCelles();
        int offsetX = 0; int offsetY = 0;
        for(int x = 0; x < i; ++x){
            for(int y = 0; y < j; ++y) {
                if(!c[x][y].isValida()){
                    fieldG[x][y] = new Qbutton(true);
                    if(!c[x][y].isFrontera()) {
                        //ES UN '*'
                        fieldG[x][y].setEnabled(false); //NO EL PODEM SOBRESCRIURE
                    }
                    else{
                        fieldG[x][y].setVisible(false); //Es un "#", no el volem mostrar!
                    }
                }
                else if(c[x][y].isPrefijada()){ //Casella buida/prefixada
                    //obtenim el valor per mostrar-lo
                    fieldG[x][y] = new Qbutton(false);
                    fieldG[x][y].setText(String.valueOf(c[x][y].getValor()));
                    fieldG[x][y].setEnabled(false); //SON PREFIXADES; NO LES PODEM MODIFICAR!
                }else if(!c[x][y].isVacia()){ //si es una celda de una partida cargada puede tener un numero dentro
                    fieldG[x][y] = new Qbutton(false); //Casella buida/prefixada
                    fieldG[x][y].setText(String.valueOf(c[x][y].getValor()));
                }
                else{ //Casella buida!
                    fieldG[x][y] = new Qbutton(false);
                }
                fieldG[x][y].setBounds(offsetX, offsetY, 50, 50);
                fieldG[x][y].setHorizontalAlignment(CENTER);

                gamePanel.add(fieldG[x][y]);
                offsetX += 50;
            }
            offsetY += 50;
            offsetX = 0;
        }
    }

    private void setValues(){
        Partida aux = CtrlPresentacio.getSingletonInstance().getPartida();
        //TODO lo deberia hacer Ctrlpresentacio?
        difLabel.setText(aux.getConf().getDificultat());
        adjLabel.setText(aux.getConf().getAdjacencia());
        nomJLabel.setText(aux.getJugador().getNom());
    }

    public JPanel getPanel(){
        return bigPanel;
    }

    private class MyListener implements ActionListener {
        private int i, j;
        private MyListener(int i, int j){
            this.i = i;
            this.j = j;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            boolean del = false;
            if(valorJugada.getText().equals("")) del = true;
            try {
                System.out.println("del: " + del);
                if(!del){
                    int valorIns = Integer.parseInt(valorJugada.getText());
                    CtrlPresentacio.getSingletonInstance().ferJugadaIns(this.i, this.j, valorIns);
                    ++valorIns;
                }
                else CtrlPresentacio.getSingletonInstance().ferJugadaDel(this.i, this.j);
                fieldG[this.i][this.j].setText(valorJugada.getText());
                if(!del) valorJugada.setText(nextValue());
            } catch (Utils.ExceptionJugadaNoValida exceptionJugadaNoValida) {
                exceptionJugadaNoValida.printStackTrace();
            } catch (Utils.ExceptionTaulerResolt exceptionTaulerResolt) {
                fieldG[this.i][this.j].setText(valorJugada.getText());
                resolt = true;
                timerinoCapuccino.interrupt();
                timerinoCapuccino = null;
                //System.out.println("Estic al thread del timer" + timerinoCapuccino.getName() + " id: " + timerinoCapuccino.getId());
                JOptionPane.showMessageDialog(new JFrame(),
                        "GOOD GAME!\n Guardant record...");
                recalcular_Matrix(); //Necessari per l'ultim valor, a lo chapuza
                end_game();
            }
        }
    }

    private String nextValue(){
        boolean[] al = Gestor.getSingletonInstance().getPartida().getTauler().getUsats();
        for(int i = 1; i < al.length; ++i){
            if(!al[i]) return String.valueOf(i);
        }
        return null;
    }
    private void end_game(){
        framePartida.dispose();
    }

    private void actTimer() {
        timerinoCapuccino = new Thread(new Runnable() {
            @Override
            public void run() {
               //System.out.println("Estic al thread del timer" + timerinoCapuccino.getName() + " id: " + timerinoCapuccino.getId());
               Timer timer = new Timer(1000, new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       //System.out.println("Actualitzant timer...");
                       if(!resolt) timeLabel.setText(CtrlPresentacio.getSingletonInstance().getTimerinoPartida());
                   }
               });
               timer.start();
            }
        });
        timerinoCapuccino.start();
    }
    private void setTaulerLayout(char tcela){ //funció que aplica el layout segons el tcela(H i T) i fixa el min i max size
        int i = CtrlPresentacio.getSingletonInstance().getPartida().getTauler().getN();
        int j = CtrlPresentacio.getSingletonInstance().getPartida().getTauler().getK();
        int height; //per defecte quadrat
        int width;
        switch(tcela){
            case 'T':
                height = TriButton.getHEIGHT();
                width = TriButton.getWIDTH();
                break;
            case 'H':
                height = HexButton.getLENGTH();
                width = HexButton.getWIDTH();
                break;
            default:
                height = Qbutton.getHEIGHT();
                width  = Qbutton.getWIDTH();
                break;
        }
        int margen = 10;
        height = height * (i+1) + margen;
        width = width * (j+1) + margen;
        System.out.println("H: " + height + " w:" + width);
        int finalHeight = height;
        int finalWidth = width;
        Dimension screen = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        gamePanel.setLayout(new LayoutManager() {
            @Override
            public void addLayoutComponent(String name, Component comp) {

            }

            @Override
            public void removeLayoutComponent(Component comp) {

            }

            @Override
            public Dimension preferredLayoutSize(Container parent) {
                if((finalHeight + northPanel.getHeight() + southPanel.getHeight()) > screen.getHeight()) return new Dimension(screen.width, screen.height - 50);
                if(finalWidth > screen.getWidth()) return new Dimension(screen.width, screen.height - 50);
                return new Dimension(finalWidth, finalHeight + northPanel.getHeight() + southPanel.getHeight());
            }

            @Override
            public Dimension minimumLayoutSize(Container parent) {
                Dimension d = new Dimension(finalWidth, finalHeight + northPanel.getHeight() + southPanel.getHeight());
                if(screen.getHeight() - 50 > d.getHeight() && screen.getWidth() > d.getWidth()) return d;
                return new Dimension(screen.width, screen.height - 50);
            }

            @Override
            public void layoutContainer(Container parent) {

            }
        });
    }
}
