package hidato;

import org.junit.Test;


import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GestorSavesTest {
    //Test que guarda i carrega un hidato de la BD i compara els taulers per comprovar que s'ha carregat la mateixa partida
    @Test
    public void guardar_i_cargar_partida() throws Exception {
        Partida p = new Partida("TCEnunciat","test");
        dothings(p);
    }
    @Test
    public void guardar_i_cargar_partida2() throws Exception {
        Partida p = new Partida("QCAEnunciat","test");
        dothings(p);
    }
    private void dothings(Partida p) throws Exception {
        Gestor g = Gestor.getSingletonInstance();
        GestorSaves gs = new GestorSaves();
        g.setPartida(p);
        gs.guardar_partida(g.getPartida());
        g.setPartida(null); //simulamos un reinicio
        g.setPartida(gs.cargar_partida());
        Tauler expected = g.getPartida().getTauler();
        Celda[][] c = expected.getTauler();
        StringBuilder er = MaquinaTest.recorreCeldas(c);
        Celda[][] c2 = p.getTauler().getTauler();
        StringBuilder or = MaquinaTest.recorreCeldas(c2);
        assertEquals(er.toString(),or.toString());
    }


}