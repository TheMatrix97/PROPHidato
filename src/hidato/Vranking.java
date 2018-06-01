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
    private JPanel panel1;
    private JPanel southPanel;
    private JButton tornarButton;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTable table1;
    private JLabel labelr;
    private Configuracio conf;



    public Vranking() {



        tornarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().setContentFrame(new SelectRanking().getPanel());
            }

        });
    }
    public void FerTaula(){
        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
        modelo.addColumn("NomJugador");
        modelo.addColumn("Temps");
        table1.setEnabled(false);
        try {
            //GestorSaves gs = new GestorSaves();
            //ArrayList<Ranking> ar = gs.cargar_ranking();
            ArrayList<Ranking> ar = CtrlPresentacio.getSingletonInstance().cargar_ranking();
            boolean b = false;
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

    public void SetJPanel1(String value){
        label1.setText(value);
    }

    public void SetJPanel2(String value){
        label2.setText(value);
    }
    public void SetJPanel3(String value){
        label3.setText(value);
    }

    public void SetConfiguracio(String dif, String adj, char tcelda){
            conf = new Configuracio(dif,adj,tcelda);
    }

    public JPanel getPanel() {
        return panel1;
    }
}



