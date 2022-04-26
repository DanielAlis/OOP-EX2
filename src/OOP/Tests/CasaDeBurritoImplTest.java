package OOP.Tests;

import OOP.Provided.Profesor;
import OOP.Provided.CasaDeBurrito;
import OOP.Solution.ProfesorImpl;
import OOP.Solution.CasaDeBurritoImpl;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CasaDeBurritoImplTest {
    @Test
    public void getId() throws Exception {
        Set<String> menu = new HashSet<String>() {{
            add("Humus");
            add("Tchina");
            add("Tubi");
        }};

        CasaDeBurrito CasaDeBurrito = new CasaDeBurritoImpl(1, "Pizza Place", 2, menu);

        assertEquals(1, CasaDeBurrito.getId());
        assertEquals(2, CasaDeBurrito.distance());
    }

    @Test
    public void rate() throws Exception {
        Set<String> menu = new HashSet<String>() {{
            add("Humus");
            add("Tchina");
            add("Tubi");
        }};

        CasaDeBurrito CasaDeBurrito = new CasaDeBurritoImpl(1, "Pizza Place", 2, menu);
        Profesor Profesor1 = new ProfesorImpl(3, "Three");
        Profesor Profesor2 = new ProfesorImpl(5, "Five");
        Profesor Profesor3 = new ProfesorImpl(6, "Six");

        try {
            CasaDeBurrito.rate(Profesor1, -1);
            fail();
        }
        catch (CasaDeBurrito.RateRangeException e) {
            assertTrue(true);
        }

        try {
            CasaDeBurrito.rate(Profesor1, 6);
            fail();
        }
        catch (CasaDeBurrito.RateRangeException e) {
            assertTrue(true);
        }

        assertEquals(0, CasaDeBurrito.numberOfRates());
        assertEquals(0, CasaDeBurrito.averageRating(), DELTA);
        CasaDeBurrito.rate(Profesor1, 0);
        assertEquals(1, CasaDeBurrito.numberOfRates());
        assertEquals(0, CasaDeBurrito.averageRating(), DELTA);
        CasaDeBurrito.rate(Profesor2, 1);
        assertEquals(2, CasaDeBurrito.numberOfRates());
        assertEquals(0.5, CasaDeBurrito.averageRating(), DELTA);
        CasaDeBurrito.rate(Profesor3, 2);
        assertEquals(3, CasaDeBurrito.numberOfRates());
        assertEquals(1, CasaDeBurrito.averageRating(), DELTA);
        CasaDeBurrito.rate(Profesor1, 5);
        assertEquals(3, CasaDeBurrito.numberOfRates());
        assertEquals(8.0/3.0, CasaDeBurrito.averageRating(), DELTA);
        CasaDeBurrito.rate(Profesor2, 5).rate(Profesor3, 5);
        assertEquals(3, CasaDeBurrito.numberOfRates());
        assertEquals(5.0, CasaDeBurrito.averageRating(), DELTA);

    }

    @Test
    public void equals() throws Exception {
        Set < String > menu = new HashSet < String >() {{add("Varenyky");}};

        CasaDeBurrito CasaDeBurrito1 = new CasaDeBurritoImpl(0, "Zero", 42, menu);
        CasaDeBurrito CasaDeBurrito2 = new CasaDeBurritoImpl(1, "Zero", 42, menu);
        CasaDeBurrito CasaDeBurrito3 = new CasaDeBurritoImpl(0, "FakeZero", 42, menu);

        assertEquals(CasaDeBurrito1, CasaDeBurrito1);
        assertEquals(CasaDeBurrito1, CasaDeBurrito3);
        assertNotEquals(CasaDeBurrito1, CasaDeBurrito2);
        assertTrue(CasaDeBurrito1.compareTo(CasaDeBurrito2) < 0);
        assertTrue(CasaDeBurrito2.compareTo(CasaDeBurrito1) > 0);
        assertTrue(CasaDeBurrito3.compareTo(CasaDeBurrito1) == 0);
    }

    @Test
    public void toStringTest() throws Exception {
        Set < String > menu = new HashSet<String>() {{ add("varenyky"); }};

        CasaDeBurrito CasaDeBurrito1 = new CasaDeBurritoImpl(0, "Pizza Place", 42, menu);

        assertEquals("CasaDeBurrito: Pizza Place.\nId: 0.\nDistance: 42.\nMenu: varenyky.", CasaDeBurrito1.toString());

        menu.add("Pancake");

        assertEquals("CasaDeBurrito: Pizza Place.\nId: 0.\nDistance: 42.\nMenu: varenyky.", CasaDeBurrito1.toString());

        CasaDeBurrito CasaDeBurrito2 = new CasaDeBurritoImpl(1, "PiZzA PlAcE", 23, menu);

        assertEquals("CasaDeBurrito: PiZzA PlAcE.\nId: 1.\nDistance: 23.\nMenu: Pancake, varenyky.", CasaDeBurrito2.toString());
    }

    private static final double DELTA = 1e-10;
}