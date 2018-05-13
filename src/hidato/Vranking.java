package hidato;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Vranking {
    public JPanel panel1;
    public JTextField textField1;
    public JTextField textField2;
    public JTextField textField3;
    private JTextField formaTextField;
    private JTextField adjacenciaTextField;
    private JTextField dificultatTextField;
    private JPanel southPanel;
    private JButton tornarButton;
    private JButton OKButton;



    public Vranking(){
        tornarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.getSingletonInstance().setContentFrame(new SelectRanking().getPanel());
            }
        });
    //    this.filesPath = new File("").getAbsolutePath() + "/saves/";
        String rank = new String();
        String path = new File("").getAbsolutePath() + "/saves/ranking.hidato";
        File archivo = new File(path);

        FileReader fr = null;
        BufferedReader entrada = null;
        try {
            fr = new FileReader(path);
            entrada = new BufferedReader(fr);

            while(entrada.ready()){
                rank += entrada.readLine();
            }

        }catch(IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if(null != fr){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }

    public JPanel getPanel() {
        return panel1;
    }
}



