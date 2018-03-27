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

import java.util.ArrayList;

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
        Celda instance = new Celda(true, 5, 'Q', "CA");
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
        Celda instance = new Celda(true, 5, 'Q', "CA");
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
        Celda instance = new Celda(false, 10, 'T', "C");
        int expResult = 10;
        int result = instance.getValor();
        assertEquals(expResult, result);

    }

    /**
     * Test of isVacia method, of class Celda.
     */
    @Test
    public void testIsVacia() {
        System.out.println("isVacia");
        Celda instance = new Celda(true, 'H',"C");
        boolean expResult = true;
        boolean result = instance.isVacia();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getVecinos method, of class Celda.
     */
    @Test
    public void testGetVecinos() {
        System.out.println("getVecinos");
        Celda instance = new Celda(true, 'H',"C");
        Celda vecino = new Celda(false, 'H',"C"); //valor por defecto 0
        instance.addVecino(vecino);
        instance.addVecino(vecino);
        ArrayList<Celda> result = instance.getVecinos();
        assertEquals(2, result.size());
    }

    /**
     * Test of setVecinos method, of class Celda.
     */
    @Test
    public void testAddVecino() {
        System.out.println("AddVecino");
        Celda vecino = new Celda(true, 'H',"C");
        Celda instance = new Celda(false, 10, 'T', "C");
        instance.addVecino(vecino);
        ArrayList<Celda> result = instance.getVecinos();
        assert(result.contains(vecino));
    }

    @Test
    public void test_invalida(){
       Celda t = new Celda('T', "C",false);
       assert(!t.isValida() && !t.isFrontera());
    }

    @Test
    public void test_frontera(){
        Celda t = new Celda('T', "C",true);
        assert(!t.isValida() && t.isFrontera());
    }
}
