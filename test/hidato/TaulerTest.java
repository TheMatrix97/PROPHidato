package hidato;

import org.junit.*;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TaulerTest {
    //Tests per verificar l'adjecencia de cada casella
    @Test
    public void TestTaulerAdjQCA() throws IOException{
        Tauler t = new Tauler("QCAresolt");
        Celda[][] aux = t.getTauler();
        String out = calculADJ(aux);
        String expected = expected_out("QCAresolt");
        assertEquals(expected,out);
    }
    @Test
    public void TestTaulerAdjQC() throws IOException{
        Tauler t = new Tauler("QCresolt");
        Celda[][] aux = t.getTauler();
        String out = calculADJ(aux);
        String expected = expected_out("QCresolt");
        assertEquals(expected,out);
    }

    @Test
    public void TestTaulerAdjTC() throws IOException{
        Tauler t = new Tauler("TCresolt");
        Celda[][] aux = t.getTauler();
        String out = calculADJ(aux);
        String expected = expected_out("TCresolt");
        assertEquals(expected,out);
    }
    @Test
    public void TestTaulerAdjTCA() throws IOException{
        Tauler t = new Tauler("TCAresolt");
        Celda[][] aux = t.getTauler();
        String out = calculADJ(aux);
        String expected = expected_out("TCAresolt");
        assertEquals(expected,out);
    }
    @Test
    public void TestTaulerAdjHC() throws IOException{
        Tauler t = new Tauler("HCresolt");
        Celda[][] aux = t.getTauler();
        String out = calculADJ(aux);
        String expected = expected_out("HCresolt");
        assertEquals(expected,out);
    }

    //Test que verifica l'algorisme que verifica si un hidato està solucionat
    @Test
    public void Test_validador_tauler() throws IOException {
        Tauler t = new Tauler("QCAEnunciatOut"); //aquest hidato de la BD està solucionat
        assert (t.validador_tauler()); // el validador ha de tornar true
    }
    //Aquest hidato no esta solucionat, el validor ha de retornar fals
    @Test
    public void Test_validador_tauler_contrari() throws IOException {
        Tauler t = new Tauler("QCAEnunciat");
        assert (!t.validador_tauler());
    }
    //Test que verifica el generador
    @Test
    public void Test_generador(){
        //generar_tauler("Facil","C",'T');
        //generar_tauler("Dificil","CA",'T');
        generar_tauler("Dificil", "CA", 'T');
    }

    private void generar_tauler(String dif, String adj, char type){
        Configuracio c = new Configuracio(dif,adj,type);
        Tauler t = new Tauler(c);
        Celda[][] aux = t.getTauler();
        System.out.println(t.getTauler()[0][0].getForma() + "," +t.getTauler()[0][0].getAdj()+ "," + t.getN() + "," + t.getK());
        System.out.print(MaquinaTest.recorreCeldas(aux));
        boolean b = Maquina.resolHidato(t);
        System.out.print("\n");
        System.out.print(MaquinaTest.recorreCeldas(aux));
        System.out.print("\n");
        assert(b);
    }

    private String expected_out(String name) throws IOException {
        String filePath = new File("").getAbsolutePath();
        FileReader f = new FileReader(filePath+"/BaseDadesHidatos/"+name+"Out.txt");
        BufferedReader b = new BufferedReader(f);
        String auxs;
        StringBuilder expected = new StringBuilder();
        while((auxs = b.readLine()) != null) expected.append(auxs).append(" \n");
        return expected.toString();
    }
    //TODO test QuadradoCA trianguloCA

    private String calculADJ(Celda[][] aux){
        StringBuilder out = new StringBuilder();
        for(Celda[] a : aux) {
            for(Celda b: a){
                if(b.isValida()) {
                    ArrayList<Celda> veins = b.getVecinos();
                    System.out.print("Celda " + b.getValor() + ": ");
                    out.append("Celda ").append(b.getValor()).append(": ");
                    for (Celda k : veins) {
                        if (k.isValida()){
                            System.out.print(k.getValor() + " ");
                            out.append(k.getValor()).append(" ");
                        }
                    }
                    out.append('\n');
                    System.out.print('\n');
                }
            }
        }
        return out.toString();
    }

}