package timer;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MergedTimerTest {

    @Test
    public void testNext() {
        Timer timer1 = new TimerTest(Arrays.asList(1, 2, 3));
        Timer timer2 = new TimerTest(Arrays.asList(4, 5, 6));
        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);

        assertEquals(5, mergedTimer.next());
        assertEquals(7, mergedTimer.next());
        assertEquals(9, mergedTimer.next());
        assertNull(mergedTimer.next());
    }
}
