package timer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.TreeSet;
import java.util.NoSuchElementException;
import java.util.ArrayList;

class DateTimerTest {

    @Test
    void testDateIntervals() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(0);
        dates.add(3);
        dates.add(6);

        DateTimer timer = new DateTimer(dates);

        assertEquals(0, timer.next(), "Le premier intervalle doit être de 0");
        assertEquals(3, timer.next(), "Le deuxième intervalle doit être de 3");
        assertEquals(3, timer.next(), "Le troisième intervalle doit être de 3");
    }

    @Test
    void testNextNegation() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(10);
        dates.add(15);
        DateTimer timer = new DateTimer(dates);

        timer.next(); // Première invocation
        timer.next(); // Deuxième invocation

        assertThrows(NoSuchElementException.class, timer::next, "Une NoSuchElementException devrait être levée lorsque plus aucun intervalle n'est disponible");
    }

    @Test
    void testEmptyDates() {
        TreeSet<Integer> dates = new TreeSet<>();
        DateTimer timer = new DateTimer(dates);

        assertFalse(timer.hasNext(), "Le timer ne doit pas avoir de prochain élément");
        assertThrows(NoSuchElementException.class, timer::next, "Une NoSuchElementException devrait être levée pour une liste vide");
    }

    @Test
    void testNonUniformIntervals() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(1);
        dates.add(4);
        dates.add(10);
        dates.add(15);

        DateTimer timer = new DateTimer(dates);

        assertEquals(1, timer.next(), "Le premier intervalle doit être de 1");
        assertEquals(3, timer.next(), "Le deuxième intervalle doit être de 3");
        assertEquals(6, timer.next(), "Le troisième intervalle doit être de 6");
        assertEquals(5, timer.next(), "Le quatrième intervalle doit être de 5");
    }

    @Test
    void testConstructorWithArrayList() {
        ArrayList<Integer> lapsTimes = new ArrayList<>();
        lapsTimes.add(2);
        lapsTimes.add(3);
        lapsTimes.add(5);

        DateTimer timer = new DateTimer(lapsTimes);

        assertEquals(2, timer.next(), "Le premier intervalle doit être de 2");
        assertEquals(3, timer.next(), "Le deuxième intervalle doit être de 3");
        assertEquals(5, timer.next(), "Le troisième intervalle doit être de 5");
    }

    @Test
    void testSingleElement() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(5);

        DateTimer timer = new DateTimer(dates);

        assertEquals(5, timer.next(), "L'intervalle unique doit être de 5");
        assertFalse(timer.hasNext(), "Le timer ne doit plus avoir de prochain élément");
        assertThrows(NoSuchElementException.class, timer::next, "Une NoSuchElementException devrait être levée après l'unique élément");
    }

    @Test
    void testMultipleElements() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(2);
        dates.add(4);
        dates.add(7);
        dates.add(11);

        DateTimer timer = new DateTimer(dates);

        assertEquals(2, timer.next(), "Le premier intervalle doit être de 2");
        assertEquals(2, timer.next(), "Le deuxième intervalle doit être de 2");
        assertEquals(3, timer.next(), "Le troisième intervalle doit être de 3");
        assertEquals(4, timer.next(), "Le quatrième intervalle doit être de 4");
        assertFalse(timer.hasNext(), "Le timer ne doit plus avoir de prochain élément");
    }
}
