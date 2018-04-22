package hidato;

import org.junit.*;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TaulerTest {

    public TaulerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestTaulerAdjQCA() throws IOException{ //el cuadrado CA chusca!
        Tauler t = new Tauler("QCAresolt");
        Celda[][] aux = t.getTauler();
        String out = calculADJ(aux);
        String expected = expected_out("QCAresolt");
        assertEquals(expected,out);
    }
    @Test
    public void TestTaulerAdjQC() throws IOException{ //el cuadrado CA chusca!
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
    @Test
    public void TestTaulerAdjHC2() throws IOException{
        Tauler t = new Tauler("HCresolt2");
        Celda[][] aux = t.getTauler();
        String out = calculADJ(aux);
        String expected = expected_out("HCresolt2");
        assertEquals(expected,out);
    }
    @Test(timeout=20000)
    public void test_generador(){
        generar_tauler("Facil","C",'T');
        generar_tauler("Normal","C",'H');
        generar_tauler("Dificil", "C", 'Q');
    }

    private void generar_tauler(String dif, String adj, char type){
        Configuracio c = new Configuracio(dif,adj,type);
        Tauler t = new Tauler(c);
        Celda[][] aux = t.getTauler();
        Maquina m = new Maquina();
        System.out.print(MaquinaTest.recorreCeldas(aux));
        assert(m.resolHidato(t));
        System.out.println();
    }

    private String expected_out(String name) throws IOException {
        String filePath = new File("").getAbsolutePath();
        FileReader f = new FileReader(filePath+"/BaseDadesHidatos/"+name+"Out.txt");
        BufferedReader b = new BufferedReader(f);
        String auxs;
        String expected = "";
        while((auxs = b.readLine()) != null) expected += auxs + " \n";
        return expected;
    }
    //TODO test QuadradoCA trianguloCA

    private String calculADJ(Celda[][] aux){
        String out = "";
        for(Celda[] a : aux) {
            for(Celda b: a){
                if(b.isValida()) {
                    ArrayList<Celda> veins = b.getVecinos();
                    System.out.print("Celda " + b.getValor() + ": ");
                    out += "Celda " + b.getValor() + ": ";
                    for (Celda k : veins) {
                        if (k.isValida()){
                            System.out.print(k.getValor() + " ");
                            out += k.getValor() + " ";
                        }
                    }
                    out += '\n';
                    System.out.print('\n');
                }
            }
        }
        return out;
    }

}