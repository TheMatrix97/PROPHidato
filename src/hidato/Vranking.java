package hidato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Vranking {
    public JPanel panel1;
    private JPanel southPanel;
    private JButton tornarButton;
    public JLabel label1;
    public JLabel label2;
    public JLabel label3;
    private JTable table1;
    private JLabel labelr;
    public Configuracio conf;



    public Vranking() {



        tornarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().setContentFrame(new SelectRanking().getPanel());
            }

        });
    }
  /*      switch (s) {
            case "Quadrat":
                forma = 'Q';
                break;
            case "Triangle":
                forma = 'T';
                break;
            case  "Hexagon":
                forma = 'H';
        }
        String s2 = label2.getText();
        String adj ="";
        switch (s2) {
            case "Costats":
                adj = "C";
                break;
            case "Costats+angles":
                adj = "CA";
        }
        String dificult = label3.getText();
        Configuracio c = new Configuracio(dificult,adj,forma); */
   //     String [] filaux = new String[2];
     //   filaux[0] = "NomJugador";
      //  filaux[1] = "Temps";

        public void FerTaula(){
        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
        modelo.addColumn("NomJugador");
        modelo.addColumn("Temps");
        modelo.addRow ( new String[] {"NomJugador", "Temps"} );
        table1.setEnabled(false);
        try {
             GestorSaves gs = new GestorSaves();
            ArrayList<Ranking> ar = gs.cargar_ranking();
            boolean b = false;
            String auxiliar = "C";
           for(Ranking a : ar){
             //
                a.getRanking();
                Configuracio confi = a.getConf();
                if (confi.equals(conf)){
                   b = true;
                    ArrayList<Record> rl = a.getRecords();
                    for (Record r : rl){
                        String a1 = r.getnomJugador();
                        String a2 = r.getTime().get_time();
                        modelo.addRow ( new String[] {a1,a2} ); // Añade una fila al final
                        }
                }
                if (b) break;
            }
        } catch (Exception e) {

        }
    }




    public JPanel getPanel() {
        return panel1;
    }
}



