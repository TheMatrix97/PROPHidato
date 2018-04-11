package hidato;

import org.junit.Test;


import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GestorTest {
/* todo els tests de GestorSaves
    @Test
    public void guardar_i_cargar_partida() throws Exception {
        Partida p = new Partida("TCEnunciat");
        dothings(p);
    }
    @Test
    public void guardar_i_cargar_partida2() throws Exception {
        Partida p = new Partida("QCAEnunciat");
        dothings(p);
    }

    private void dothings(Partida p) throws Exception {
        Gestor g = Gestor.getSingletonInstance();
        g.setPartida(p);
        g.guardar_partida(); //guardamos la partida
        g.setPartida(null); //simulamos un reinicio
        g.cargar_partida();
        Tauler expected = g.getPartida().getTauler();
        Celda[][] c = expected.getTauler();
        StringBuilder er = MaquinaTest.recorreCeldas(c);
        Celda[][] c2 = p.getTauler().getTauler();
        StringBuilder or = MaquinaTest.recorreCeldas(c2);
        assertEquals(er.toString(),or.toString());
    }

    @Test
    public void test_existe() throws Exception { //no hay nada en el save, devuelve nul
        Gestor g = Gestor.getSingletonInstance();
        Partida p = g.existe_Partida();
        assert (p == null);
    }*/

}