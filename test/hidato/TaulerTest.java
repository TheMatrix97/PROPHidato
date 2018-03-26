package hidato;

import org.junit.*;

import java.io.IOException;
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
        calculADJ(aux);

    }
    @Test
    public void TestTaulerAdjTC() throws IOException{
        Tauler t = new Tauler("TCresolt");
        Celda[][] aux = t.getTauler();
        calculADJ(aux);
    }
    @Test
    public void TestTaulerAdjHC() throws IOException{
        Tauler t = new Tauler("HCresolt");
        Celda[][] aux = t.getTauler();
        calculADJ(aux);
    }
    //TODO test QuadradoCA trianguloCA

    private void calculADJ(Celda[][] aux){
        for(Celda[] a : aux) {
            for(Celda b: a){
                if(b.isValida()) {
                    ArrayList<Celda> veins = b.getVecinos();
                    System.out.print("Celda " + b.getValor() + ": ");
                    for (Celda k : veins) {
                        if (k.isValida()) System.out.print(k.getValor() + " ");
                    }
                    System.out.print('\n');
                }
            }
        }
    }

}