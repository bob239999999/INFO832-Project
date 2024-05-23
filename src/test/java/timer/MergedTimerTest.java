package timer;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MergedTimerTest {

    @Test
    public void testNext_BothTimersHaveElements() {
        Timer timer1 = new TimerTest(Arrays.asList(1, 2, 3));
        Timer timer2 = new TimerTest(Arrays.asList(4, 5, 6));
        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);

        assertEquals(5, mergedTimer.next()); // 1 + 4
        assertEquals(7, mergedTimer.next()); // 2 + 5
        assertEquals(9, mergedTimer.next()); // 3 + 6
        assertNull(mergedTimer.next());      // Both timers are now empty
    }

    @Test
    public void testNext_FirstTimerEmpty() {
        Timer timer1 = new TimerTest(Arrays.asList());
        Timer timer2 = new TimerTest(Arrays.asList(4, 5, 6));
        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);

        assertNull(mergedTimer.next()); // timer1 is empty, so mergedTimer cannot proceed
    }

    @Test
    public void testNext_SecondTimerEmpty() {
        Timer timer1 = new TimerTest(Arrays.asList(1, 2, 3));
        Timer timer2 = new TimerTest(Arrays.asList());
        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);

        assertNull(mergedTimer.next()); // timer2 is empty, so mergedTimer cannot proceed
    }

    @Test
    public void testNext_BothTimersEmpty() {
        Timer timer1 = new TimerTest(Arrays.asList());
        Timer timer2 = new TimerTest(Arrays.asList());
        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);

        assertNull(mergedTimer.next()); // Both timers are empty
    }

    @Test
    public void testHasNext_BothTimersHaveElements() {
        Timer timer1 = new TimerTest(Arrays.asList(1, 2, 3));
        Timer timer2 = new TimerTest(Arrays.asList(4, 5, 6));
        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);

        assertTrue(mergedTimer.hasNext());  // Both timers have elements
        mergedTimer.next();
        assertTrue(mergedTimer.hasNext());  // Both timers still have elements
        mergedTimer.next();
        assertTrue(mergedTimer.hasNext());  // Both timers still have elements
        mergedTimer.next();
        assertFalse(mergedTimer.hasNext()); // Both timers are now empty
    }

    @Test
    public void testHasNext_FirstTimerEmpty() {
        Timer timer1 = new TimerTest(Arrays.asList());
        Timer timer2 = new TimerTest(Arrays.asList(4, 5, 6));
        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);

        assertFalse(mergedTimer.hasNext()); // timer1 is empty, so mergedTimer cannot proceed
    }

    @Test
    public void testHasNext_SecondTimerEmpty() {
        Timer timer1 = new TimerTest(Arrays.asList(1, 2, 3));
        Timer timer2 = new TimerTest(Arrays.asList());
        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);

        assertFalse(mergedTimer.hasNext()); // timer2 is empty, so mergedTimer cannot proceed
    }

    @Test
    public void testHasNext_BothTimersEmpty() {
        Timer timer1 = new TimerTest(Arrays.asList());
        Timer timer2 = new TimerTest(Arrays.asList());
        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);

        assertFalse(mergedTimer.hasNext()); // Both timers are empty
    }
}
