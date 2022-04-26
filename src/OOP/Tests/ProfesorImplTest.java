package OOP.Tests;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.ProfesorImpl;
import OOP.Solution.CasaDeBurritoImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ProfesorImplTest {
    private static Profesor Profesor17, Profesor23, Profesor42, Profesor69, ProfesorMinusOne, ProfesorCopy, ProfesorClone;
    private static CasaDeBurrito CasaDeBurrito3, CasaDeBurrito7, CasaDeBurritoAngels, CasaDeBurrito23, CasaDeBurritoMinus3, CasaDeBurritoZoo, CasaDeBurritoDamned;
    private static Set< String > menu3, menu7, menuAngels, menu23;

    @BeforeClass
    public static void init() {
        Profesor17 = new ProfesorImpl(17, "Seventeen");
        Profesor23 = new ProfesorImpl(23, "Twenty three");
        Profesor42 = new ProfesorImpl(42, "Forty two");
        Profesor69 = new ProfesorImpl(69, "Sixty Nine");
        ProfesorMinusOne = new ProfesorImpl(2, "Minus One");
        ProfesorCopy = new ProfesorImpl(0, "Minus One");
        ProfesorClone = new ProfesorImpl(2, "42");

        menu3 = new HashSet<String>() {{
            add("Humus");
            add("Tchina");
            add("Tubi");
        }};

        menu7 = new HashSet<String>() {{
            add("Borsch");
            add("Varenyky");
            add("Salo");
        }};

        menuAngels = new HashSet<String>() {{
            add("Currywurst");
            add("Chips");
            add("Fish");
            add("Beer");
        }};

        menu23 = new HashSet<String>() {{
            add("Vodka");
            add("Samogon");
            add("Shchi");
        }};

        CasaDeBurrito3 = new CasaDeBurritoImpl(3, "Three", 321, menu3);
        CasaDeBurritoMinus3 = new CasaDeBurritoImpl(-3, "Three", 321, menu3);
        CasaDeBurrito7 = new CasaDeBurritoImpl(7, "We are the seven judgment of heaven", 23, menu7);
        CasaDeBurritoAngels = new CasaDeBurritoImpl(13, "Why don't we know - We are the angels", 17, menuAngels);
        CasaDeBurrito23 = new CasaDeBurritoImpl(23, "Drink and Drive", 42, menu23);
        CasaDeBurritoZoo = new CasaDeBurritoImpl(666, "Zoo", 666, menu23);
        CasaDeBurritoDamned = new CasaDeBurritoImpl(-666, "Damned", 16, menu23);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(Profesor17.getId(), 17);
        assertEquals(Profesor23.getId(), 23);
        assertEquals(Profesor42.getId(), 42);
        assertEquals(Profesor69.getId(), 69);
        assertEquals(ProfesorMinusOne.getId(), 2);
    }

    @Test
    public void favorite() throws Exception {
        try {
            ProfesorMinusOne.favorite(CasaDeBurrito3);
            fail();
        }
        catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {
            assertEquals(ProfesorMinusOne.favorites().size(), 0);
        }

        CasaDeBurrito3.rate(ProfesorMinusOne, 3);

        try {
            ProfesorMinusOne.favorite(CasaDeBurritoMinus3);
            fail();
        }
        catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {
            assertEquals(ProfesorMinusOne.favorites().size(), 0);
        }

        try {
            ProfesorMinusOne.favorite(CasaDeBurrito3);
        }
        catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {
            fail();
        }

        assertEquals(ProfesorMinusOne.favorites().size(), 1);
        assertTrue(ProfesorMinusOne.favorites().contains(CasaDeBurrito3));
        assertTrue(!ProfesorMinusOne.favorites().contains(CasaDeBurritoMinus3));

        ProfesorMinusOne.favorite(CasaDeBurrito3);

        assertEquals(ProfesorMinusOne.favorites().size(), 1);
        assertTrue(ProfesorMinusOne.favorites().contains(CasaDeBurrito3));
        assertTrue(!ProfesorMinusOne.favorites().contains(CasaDeBurritoMinus3));

        CasaDeBurritoMinus3.rate(ProfesorMinusOne, 4);

        assertEquals(ProfesorMinusOne.favorites().size(), 1);
        assertTrue(ProfesorMinusOne.favorites().contains(CasaDeBurrito3));
        assertFalse(ProfesorMinusOne.favorites().contains(CasaDeBurritoMinus3));

        ProfesorMinusOne.favorite(CasaDeBurritoMinus3);

        assertEquals(ProfesorMinusOne.favoritesByDist(321), Arrays.asList(CasaDeBurritoMinus3, CasaDeBurrito3));
        assertTrue(ProfesorMinusOne.favoritesByDist(320).isEmpty());
        assertTrue(ProfesorMinusOne.favoritesByRating(5).isEmpty());
        assertEquals(ProfesorMinusOne.favoritesByRating(4), Arrays.asList(CasaDeBurritoMinus3));
        assertEquals(ProfesorMinusOne.favoritesByRating(3), Arrays.asList(CasaDeBurritoMinus3, CasaDeBurrito3));
        assertEquals(ProfesorMinusOne.favoritesByRating(2), Arrays.asList(CasaDeBurritoMinus3, CasaDeBurrito3));

        CasaDeBurritoMinus3.rate(Profesor17, 5);

        Profesor17.favorite(CasaDeBurritoMinus3);

        CasaDeBurrito3.rate(Profesor17, 4);

        assertTrue(Profesor17.favoritesByRating(5).isEmpty());
        assertEquals(Profesor17.favoritesByRating(4), Arrays.asList(CasaDeBurritoMinus3));
        assertEquals(Profesor17.favoritesByDist(321), Arrays.asList(CasaDeBurritoMinus3));

        Profesor17.favorite(CasaDeBurrito3);

        assertEquals(Profesor17.favoritesByRating(4), Arrays.asList(CasaDeBurritoMinus3));
        assertEquals(Profesor17.favoritesByRating(3), Arrays.asList(CasaDeBurritoMinus3, CasaDeBurrito3));
        assertEquals(Profesor17.favoritesByDist(321), Arrays.asList(CasaDeBurritoMinus3, CasaDeBurrito3));

        CasaDeBurritoMinus3.rate(Profesor17, 3);

        assertTrue(Profesor17.favoritesByRating(4).isEmpty());
        assertEquals(Profesor17.favoritesByRating(3), Arrays.asList(CasaDeBurritoMinus3, CasaDeBurrito3));
        assertEquals(Profesor17.favoritesByDist(321), Arrays.asList(CasaDeBurritoMinus3, CasaDeBurrito3));


        CasaDeBurrito3.rate(ProfesorMinusOne, 5);
        CasaDeBurrito3.rate(Profesor17, 4);

        assertTrue(Profesor17.favoritesByRating(5).isEmpty());
        assertEquals(Profesor17.favoritesByRating(4), Arrays.asList(CasaDeBurrito3));
        assertEquals(Profesor17.favoritesByRating(3), Arrays.asList(CasaDeBurrito3, CasaDeBurritoMinus3));
        assertEquals(Profesor17.favoritesByDist(321), Arrays.asList(CasaDeBurrito3, CasaDeBurritoMinus3));

        CasaDeBurrito7.rate(Profesor17, 0);
        CasaDeBurrito23.rate(Profesor17, 0);

        Profesor17.favorite(CasaDeBurrito7).favorite(CasaDeBurrito23);

        Profesor17.favorites().remove(CasaDeBurrito23);
        Profesor17.favorites().remove(CasaDeBurrito7);

        assertEquals(Profesor17.favorites().size(), 4);
        assertTrue(Profesor17.favorites().contains(CasaDeBurrito23));
        assertTrue(Profesor17.favorites().contains(CasaDeBurrito7));
    }

    @Test
    public void addFriend() throws Exception {
        assertTrue(Profesor23.getFriends().isEmpty());

        try {
            Profesor23.addFriend(Profesor23);
            fail();
        }
        catch (Profesor.SameProfesorException e) {
            assertTrue(true);
        }

        Profesor23.addFriend(Profesor42);

        assertEquals(Profesor23.getFriends().size(), 1);
        assertTrue(Profesor42.getFriends().isEmpty());

        try {
            Profesor23.addFriend(Profesor42);
            fail();
        }
        catch (Profesor.ConnectionAlreadyExistsException e) {
            assertTrue(true);
        }

        Profesor42.addFriend(Profesor23).addFriend(Profesor69);
        Profesor42.getFriends().add(Profesor17);
        Profesor42.getFriends().remove(Profesor69);

        assertTrue(Profesor42.getFriends().contains(Profesor23));
        assertTrue(Profesor42.getFriends().contains(Profesor69));
        assertEquals(Profesor42.getFriends().size() , 2);
        assertFalse(Profesor42.getFriends().contains(Profesor17));
    }

    @Test
    public void equals() throws Exception {
        assertEquals(ProfesorMinusOne, ProfesorMinusOne);
        assertEquals(ProfesorMinusOne, ProfesorClone);
        assertNotEquals(ProfesorMinusOne, ProfesorCopy);
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(Profesor69.toString(), "Profesor: Sixty Nine.\nId: 69.\nFavorites: ."); //TODO: check it
        CasaDeBurrito7.rate(Profesor69, 4);
        Profesor69.favorite(CasaDeBurrito7);
        assertEquals(Profesor69.toString(), "Profesor: Sixty Nine.\nId: 69.\nFavorites: We are the seven judgment of heaven."); //TODO: check it
        CasaDeBurritoAngels.rate(Profesor69, 4);
        Profesor69.favorite(CasaDeBurritoAngels);
        assertEquals(Profesor69.toString(), "Profesor: Sixty Nine.\nId: 69.\nFavorites: We are the seven judgment of heaven, Why don't we know - We are the angels."); //TODO: check it
        CasaDeBurritoZoo.rate(Profesor69, 5);
        Profesor69.favorite(CasaDeBurritoZoo);
        assertEquals(Profesor69.toString(), "Profesor: Sixty Nine.\nId: 69.\nFavorites: We are the seven judgment of heaven, Why don't we know - We are the angels, Zoo."); //TODO: check it
    }

    @Test
    public void compareTo() throws Exception {
        assertTrue(Profesor17.compareTo(Profesor69) < 0);
        assertTrue(Profesor69.compareTo(Profesor17) > 0);
        assertEquals(ProfesorMinusOne.compareTo(ProfesorClone), 0);
    }

}