package OOP.Tests;

import OOP.Provided.Profesor;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.CartelDeNachos;
import OOP.Solution.ProfesorImpl;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.CartelDeNachosImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CartelDeNachosImplTest {
    @Before
    public void setUp() throws Exception {
        CartelDeNachos0 = new CartelDeNachosImpl();
        CartelDeNachos1 = new CartelDeNachosImpl();

        Profesor1 = new ProfesorImpl(0, "Zero");
        CartelDeNachos1.joinCartel(0, "Zero");
        CartelDeNachos1.joinCartel(2, "Two");

        menu1 = new HashSet < String > () {{add("Varenyky");}};
        CasaDeBurritoOne = new CasaDeBurritoImpl(0, "Zero", 42, menu1);
        CartelDeNachos1.addCasaDeBurrito(0, "Zero", 42, menu1);

        Set < String > menu = new HashSet<String>() {{add("Borshch");}};
        CartelDeNachos3 = new CartelDeNachosImpl();

        CasaDeBurrito0 = CartelDeNachos3.addCasaDeBurrito(0, "Zero", 23, menu);
        CasaDeBurrito1 = CartelDeNachos3.addCasaDeBurrito(1, "One", 23, menu);
        CasaDeBurrito2 = CartelDeNachos3.addCasaDeBurrito(2, "Two", 17, menu);
        CasaDeBurrito3 = CartelDeNachos3.addCasaDeBurrito(3, "Tree", 42, menu);
        CasaDeBurrito4 = CartelDeNachos3.addCasaDeBurrito(4, "Four", 42, menu);
        CasaDeBurrito5 = CartelDeNachos3.addCasaDeBurrito(5, "Five", 42, menu);
        CasaDeBurrito6 = CartelDeNachos3.addCasaDeBurrito(6, "Six", 0, menu);
        CasaDeBurrito7 = CartelDeNachos3.addCasaDeBurrito(7, "Seven", 23, menu);
        CasaDeBurrito8 = CartelDeNachos3.addCasaDeBurrito(8, "Eight", 666, menu);

        ProfesorA = CartelDeNachos3.joinCartel(0, "A");
        ProfesorB = CartelDeNachos3.joinCartel(1, "B");
        ProfesorC = CartelDeNachos3.joinCartel(2, "C");
        ProfesorD = CartelDeNachos3.joinCartel(3, "D");

        CasaDeBurrito0.rate(ProfesorA, 1).rate(ProfesorB, 1);
        CasaDeBurrito1.rate(ProfesorA, 1).rate(ProfesorB, 1);
        CasaDeBurrito2.rate(ProfesorA, 2).rate(ProfesorB, 2);
        CasaDeBurrito3.rate(ProfesorA, 2).rate(ProfesorB, 2);
        CasaDeBurrito4.rate(ProfesorB, 4);
        CasaDeBurrito5.rate(ProfesorC, 5);
        CasaDeBurrito6.rate(ProfesorD, 5);
        CasaDeBurrito7.rate(ProfesorA, 0).rate(ProfesorB, 0);
        CasaDeBurrito8.rate(ProfesorD, 5);

        ProfesorA.favorite(CasaDeBurrito0).favorite(CasaDeBurrito1).favorite(CasaDeBurrito2).favorite(CasaDeBurrito3).favorite(CasaDeBurrito7);
        ProfesorB.favorite(CasaDeBurrito0).favorite(CasaDeBurrito1).favorite(CasaDeBurrito2).favorite(CasaDeBurrito3).favorite(CasaDeBurrito4);
        ProfesorC.favorite(CasaDeBurrito5);
        ProfesorD.favorite(CasaDeBurrito8);

        CartelDeNachos3
                .addConnection(ProfesorD, ProfesorB)
                .addConnection(ProfesorD, ProfesorA)
                .addConnection(ProfesorA, ProfesorC)
                .addConnection(ProfesorC, ProfesorB)
                .addConnection(ProfesorA, ProfesorB);
    }

    @Test
    public void joinCartel() throws Exception {
        try {
            CartelDeNachos1.joinCartel(0, "FakeZero");
            fail();
        } catch (Profesor.ProfesorAlreadyInSystemException e) {
            assertTrue(true);
        }

    }

    @Test
    public void addCasaDeBurrito() throws Exception {
        try {
            CartelDeNachos1.addCasaDeBurrito(0, "Zero", 42, menu1);
            fail();
        }
        catch (CasaDeBurrito.CasaDeBurritoAlreadyInSystemException e) {
            assertTrue(true);
        }
    }

    @Test
    public void registeredProfesores() throws Exception {
        assertTrue(CartelDeNachos0.registeredProfesores().isEmpty());
        assertEquals(2, CartelDeNachos1.registeredProfesores().size());

        try {
            CartelDeNachos1.registeredProfesores().add(new ProfesorImpl(1, "One"));
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }

        assertEquals(2, CartelDeNachos1.registeredProfesores().size());
        assertTrue(CartelDeNachos1.registeredProfesores().contains(Profesor1));
    }

    @Test
    public void registeredCasasDeBurrito() throws Exception {
        assertTrue(CartelDeNachos0.registeredCasasDeBurrito().isEmpty());
        assertEquals(1, CartelDeNachos1.registeredCasasDeBurrito().size());

        try {
            CartelDeNachos1.registeredCasasDeBurrito().add(new CasaDeBurritoImpl(1, "One", 23, menu1));
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }

        assertTrue(CartelDeNachos1.registeredCasasDeBurrito().contains(CasaDeBurritoOne));
    }

    @Test
    public void getProfesor() throws Exception {
        try {
            CartelDeNachos1.getProfesor(1);
            fail();
        }
        catch (Profesor.ProfesorNotInSystemException e) {
            assertTrue(true);
        }

        assertEquals(CartelDeNachos1.getProfesor(0), Profesor1);
        assertTrue(CartelDeNachos1.getProfesor(0).favorites().isEmpty());
        CasaDeBurritoOne.rate(CartelDeNachos1.getProfesor(0), 0);
        CartelDeNachos1.getProfesor(0).favorite(CasaDeBurritoOne);
        assertEquals(1, CartelDeNachos1.getProfesor(0).favorites().size());
        assertTrue(CartelDeNachos1.getProfesor(0).favorites().contains(CasaDeBurritoOne));
    }

    @Test
    public void getCasaDeBurrito() throws Exception {
        try {
            CartelDeNachos1.getCasaDeBurrito(1);
            fail();
        }
        catch (CasaDeBurrito.CasaDeBurritoNotInSystemException e) {
            assertTrue(true);
        }

        assertEquals(CasaDeBurritoOne, CartelDeNachos1.getCasaDeBurrito(0));
        assertEquals(0, CartelDeNachos1.getCasaDeBurrito(0).averageRating(), DELTA);
        CartelDeNachos1.getCasaDeBurrito(0).rate(CartelDeNachos1.getProfesor(0), 5);
        assertEquals(1, CartelDeNachos1.getCasaDeBurrito(0).numberOfRates());
        assertEquals(5, CartelDeNachos1.getCasaDeBurrito(0).averageRating(), DELTA);
    }

    @Test
    public void addConnection() throws Exception {
        Profesor Profesor0 = CartelDeNachos1.getProfesor(0);
        Profesor Profesor1 = new ProfesorImpl(1, "One");
        Profesor Profesor2 = CartelDeNachos1.getProfesor(2);

        try {
            CartelDeNachos1.addConnection(Profesor0, Profesor1);
            fail();
        }
        catch (Profesor.ProfesorNotInSystemException e) {
            assertTrue(true);
        }
        try {
            CartelDeNachos1.addConnection(Profesor0, Profesor0);
            fail();
        }
        catch (Profesor.SameProfesorException e) {
            assertTrue(true);
        }

        try {
            CartelDeNachos1.addConnection(Profesor0, Profesor0);
            fail();
        }
        catch (Profesor.SameProfesorException e) {
            assertTrue(true);
        }

        try {
            CartelDeNachos1.addConnection(Profesor0, Profesor0);
            fail();
        }
        catch (Profesor.SameProfesorException e) {
            assertTrue(true);
        }

        assertTrue(Profesor0.getFriends().isEmpty());
        assertTrue(Profesor2.getFriends().isEmpty());
        CartelDeNachos1.addConnection(Profesor0, Profesor2);
        assertEquals(1, Profesor0.getFriends().size());
        assertEquals(1, Profesor2.getFriends().size());
        assertTrue(Profesor0.getFriends().contains(Profesor2));
        assertTrue(Profesor2.getFriends().contains(Profesor0));

        try {
            CartelDeNachos1.addConnection(Profesor0, Profesor2);
            fail();
        }
        catch (Profesor.ConnectionAlreadyExistsException e) {
            assertTrue(true);
        }

        assertEquals(1, Profesor0.getFriends().size());
        assertEquals(1, Profesor2.getFriends().size());
        assertTrue(Profesor0.getFriends().contains(Profesor2));
        assertTrue(Profesor2.getFriends().contains(Profesor0));
    }

    @Test
    public void favoritesByRating() throws Exception {
        Profesor Profesor1 = new ProfesorImpl(1, "One");

        try {
            CartelDeNachos1.favoritesByRating(Profesor1);
            fail();
        }
        catch (Profesor.ProfesorNotInSystemException e) {
            assertTrue(true);
        }

        assertEquals(Arrays.asList(CasaDeBurrito2, CasaDeBurrito3, CasaDeBurrito0, CasaDeBurrito1, CasaDeBurrito7, CasaDeBurrito4), CartelDeNachos3.favoritesByRating(ProfesorD));
    }

    @Test
    public void favoritesByDist() throws Exception {
        Profesor Profesor1 = new ProfesorImpl(1, "One");

        try {
            CartelDeNachos1.favoritesByDist(Profesor1);
            fail();
        }
        catch (Profesor.ProfesorNotInSystemException e) {
            assertTrue(true);
        }

        assertEquals(Arrays.asList(CasaDeBurrito2, CasaDeBurrito0, CasaDeBurrito1, CasaDeBurrito7, CasaDeBurrito3, CasaDeBurrito4), CartelDeNachos3.favoritesByDist(ProfesorD));
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("Registered profesores: .\n" +
                "Registered casas de burrito: .\n" +
                "Profesores:\n" +
                "End profesores.", CartelDeNachos0.toString());
        assertEquals("Registered profesores: 0, 1, 2, 3.\n" +
                "Registered casas de burrito: 0, 1, 2, 3, 4, 5, 6, 7, 8.\n" +
                "Profesores:\n" +
                "0 -> [1, 2, 3].\n" +
                "1 -> [0, 2, 3].\n" +
                "2 -> [0, 1].\n" +
                "3 -> [0, 1].\n" +
                "End profesores.", CartelDeNachos3.toString());

        CartelDeNachos CartelDeNachos = new CartelDeNachosImpl();
        CartelDeNachos.joinCartel(1, "One");
        assertEquals("Registered profesores: 1.\n" +
                "Registered casas de burrito: .\n" +
                "Profesores:\n" +
                "1 -> [].\n" +
                "End profesores.", CartelDeNachos.toString());

        CartelDeNachos = new CartelDeNachosImpl();
        CartelDeNachos.addCasaDeBurrito(1, "One", 0, new HashSet<String>(){{ add("Varenyky"); }});
        assertEquals("Registered profesores: .\n" +
                "Registered casas de burrito: 1.\n" +
                "Profesores:\n" +
                "End profesores.", CartelDeNachos.toString());
    }

    @Test
    public void getRecommendation() throws Exception {
        try {
            CartelDeNachos0.getRecommendation(Profesor1, CasaDeBurrito1, -1);
            fail();
        }
        catch (Profesor.ProfesorNotInSystemException e) {
            assertTrue(true);
        }
        try {
            CartelDeNachos1.getRecommendation(Profesor1, CasaDeBurrito6, -1);
            fail();
        }
        catch (CasaDeBurrito.CasaDeBurritoNotInSystemException e) {
            assertTrue(true);
        }
        try {
            CartelDeNachos1.getRecommendation(Profesor1, CasaDeBurritoOne, -1);
            fail();
        }
        catch (CartelDeNachos.ImpossibleConnectionException e) {
            assertTrue(true);
        }

        assertFalse(CartelDeNachos3.getRecommendation(ProfesorD, CasaDeBurrito0, 0));
        assertTrue(CartelDeNachos3.getRecommendation(ProfesorD, CasaDeBurrito8, 0));
        assertFalse(CartelDeNachos3.getRecommendation(ProfesorD, CasaDeBurrito5, 1));
        assertTrue(CartelDeNachos3.getRecommendation(ProfesorD, CasaDeBurrito0, 1));
        assertTrue(CartelDeNachos3.getRecommendation(ProfesorD, CasaDeBurrito8, 1));
        assertTrue(CartelDeNachos3.getRecommendation(ProfesorD, CasaDeBurrito5, 2));
        assertTrue(CartelDeNachos3.getRecommendation(ProfesorD, CasaDeBurrito0, 2));
        assertTrue(CartelDeNachos3.getRecommendation(ProfesorD, CasaDeBurrito8, 2));
    }

    private static CartelDeNachos CartelDeNachos1;
    private static CartelDeNachos CartelDeNachos0;
    private static CartelDeNachos CartelDeNachos3;
    private static Set< String > menu1;
    private static Profesor Profesor1;
    private static CasaDeBurrito CasaDeBurritoOne;

    private static CasaDeBurrito CasaDeBurrito0, CasaDeBurrito1, CasaDeBurrito2, CasaDeBurrito3, CasaDeBurrito4, CasaDeBurrito5, CasaDeBurrito6, CasaDeBurrito7, CasaDeBurrito8;
    private static Profesor ProfesorA, ProfesorB, ProfesorC, ProfesorD;

    private static final double DELTA = 1e-10;
}
//todo
//   _    _          _  ______  _         ______      ________ _____  _ _
//  | |  | |   /\   | |/ / __ \| |       / __ \ \    / /  ____|  __ \| | |
//  | |__| |  /  \  | ' / |  | | |      | |  | \ \  / /| |__  | |  | | | |
//  |  __  | / /\ \ |  <| |  | | |      | |  | |\ \/ / |  __| | |  | | | |
//  | |  | |/ ____ \| . \ |__| | |____  | |__| | \  /  | |____| |__| |_|_|
//  |_|  |_/_/    \_\_|\_\____/|______|  \____/   \/   |______|_____/(_|_)
//
//todo
//   _    _          _  ______  _         ______      ________ _____  _ _
//  | |  | |   /\   | |/ / __ \| |       / __ \ \    / /  ____|  __ \| | |
//  | |__| |  /  \  | ' / |  | | |      | |  | \ \  / /| |__  | |  | | | |
//  |  __  | / /\ \ |  <| |  | | |      | |  | |\ \/ / |  __| | |  | | | |
//  | |  | |/ ____ \| . \ |__| | |____  | |__| | \  /  | |____| |__| |_|_|
//  |_|  |_/_/    \_\_|\_\____/|______|  \____/   \/   |______|_____/(_|_)

