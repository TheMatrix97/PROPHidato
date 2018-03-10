/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidato;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marc.catrisse
 */
public class CeldaTest {
    
    public CeldaTest() {
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

    /**
     * Test of isValida method, of class Celda.
     */
    @Test
    public void testIsValida() {
        System.out.println("isValida");
        Celda instance = new Celda(false,5); //
        boolean expResult = true;
        boolean result = instance.isValida();
        assertEquals(expResult, result);

    }

    /**
     * Test of isPrefijada method, of class Celda.
     */
    @Test
    public void testIsPrefijada() {
        System.out.println("isPrefijada");
        Celda instance = new Celda(true,6);
        boolean expResult = true;
        boolean result = instance.isPrefijada();
        assertEquals(expResult, result);

    }

    /**
     * Test of getValor method, of class Celda.
     */
    @Test
    public void testGetValor() {
        System.out.println("getValor");
        Celda instance = new Celda(false, 90);
        int expResult = 90;
        int result = instance.getValor();
        assertEquals(expResult, result);

    }

    /**
     * Test of isVacia method, of class Celda.
     */
    @Test
    public void testIsVacia() {
        System.out.println("isVacia");
        Celda instance = new Celda(false,80);
        boolean expResult = false;
        boolean result = instance.isVacia();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getVecinos method, of class Celda.
     */
    @Test
    public void testGetVecinos() {
        System.out.println("getVecinos");
        Celda instance = new Celda();
        Celda[] expResult = null;
        Celda[] result = instance.getVecinos();
        assertArrayEquals(expResult, result);

    }

    /**
     * Test of setVecinos method, of class Celda.
     */
    @Test
    public void testSetVecinos() {
        System.out.println("setVecinos");
        Celda[] vecinos = null;
        Celda instance = new Celda();
        instance.setVecinos(vecinos);
        Celda[] result = instance.getVecinos();
        assertArrayEquals(result,vecinos);
    }
    
}
