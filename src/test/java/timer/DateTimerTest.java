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

        assertEquals(0, timer.next(), "First interval must be 0");
        assertEquals(3, timer.next(), "Second interval must be 3");
        assertEquals(3, timer.next(), "Third interval must be 3");
    }

    @Test
    void testNextNegation() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(10);
        dates.add(15);
        DateTimer timer = new DateTimer(dates);

        timer.next(); // First invocation
        timer.next(); // Second invocation

        assertThrows(NoSuchElementException.class, timer::next, "NoSuchElementException should be raised when any interval is no longer available.");
    }

    @Test
    void testEmptyDates() {
        TreeSet<Integer> dates = new TreeSet<>();
        DateTimer timer = new DateTimer(dates);

        assertFalse(timer.hasNext(), "Timer must not have a next element.");
        assertThrows(NoSuchElementException.class, timer::next, "NoSuchElementException should be raised for an empty list.");
    }

    @Test
    void testNonUniformIntervals() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(1);
        dates.add(4);
        dates.add(10);
        dates.add(15);

        DateTimer timer = new DateTimer(dates);

        assertEquals(1, timer.next(), "First interval must be 1");
        assertEquals(3, timer.next(), "Second interval must be 3");
        assertEquals(6, timer.next(), "Third interval must be 6");
        assertEquals(5, timer.next(), "Fourth interval must be 5");
    }

    @Test
    void testConstructorWithArrayList() {
        ArrayList<Integer> lapsTimes = new ArrayList<>();
        lapsTimes.add(2);
        lapsTimes.add(3);
        lapsTimes.add(5);

        DateTimer timer = new DateTimer(lapsTimes);

        assertEquals(2, timer.next(), "First interval must be 2");
        assertEquals(3, timer.next(), "Second interval must be 3");
        assertEquals(5, timer.next(), "Third interval must be 5");
    }

    @Test
    void testSingleElement() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(5);

        DateTimer timer = new DateTimer(dates);

        assertEquals(5, timer.next(), "The unique interval must be 5");
        assertFalse(timer.hasNext(), "Timer should no longer have any element.");
        assertThrows(NoSuchElementException.class, timer::next, "NoSuchElementException should be raised after the unique element.");
    }

    @Test
    void testMultipleElements() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(2);
        dates.add(4);
        dates.add(7);
        dates.add(11);

        DateTimer timer = new DateTimer(dates);

        assertEquals(2, timer.next(), "First interval must be 2");
        assertEquals(2, timer.next(), "Second interval must 2");
        assertEquals(3, timer.next(), "Third interval must be 3");
        assertEquals(4, timer.next(), "Fourth interval must be 4");
        assertFalse(timer.hasNext(), "Timer should no longer have any element.");
    }
}
