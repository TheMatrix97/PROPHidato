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



    public Vranking(){
        String s = label1.getText();
        DefaultTableModel modelo = (DefaultTableModel)table1.getModel();
        char forma =' ';
        switch (s) {
            case "Quadrat":
                forma = 'Q';
            case "Triangle":
                forma = 'T';
            case  "Hexagon":
                forma = 'H';
        }
        String s2 = label2.getText();
        String adj ="";
        switch (s2) {
            case "Costats":
                adj = "C";
            case "Costats+angles":
                adj = "CA";
        }
        String dificult = label3.getText();
        Configuracio c = new Configuracio(dificult,adj,forma);
        Object [] filaux = new Object[2];
        filaux[0] = "NomJugador";
        filaux[1] = "Temps";
        modelo.addRow ( filaux );

        try {
            StringBuilder sb = new StringBuilder();
            GestorSaves gs = new GestorSaves();
            ArrayList<Ranking> ar = gs.cargar_ranking();
            boolean b = false;
            for(Ranking a : ar){
                Configuracio confi = a.getConf();
                if (confi.equals(c)){
                    b = true;
                    ArrayList<Record> rl = a.getRecords();
                    for (Record r : rl){
                         Object [] fila = new Object[2];
                        fila[0] = r.getnomJugador();
                        fila[1] = r.getTime();
                        modelo.addRow ( fila ); // Añade una fila al final
                        }
                }
                if (b) break;
            }
        } catch (Exception e) {

        }


        tornarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().setContentFrame(new SelectRanking().getPanel());
            }

        });


    }

    public JPanel getPanel() {
        return panel1;
    }
}



