package timer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TimerTest implements Timer {
    private Iterator<Integer> iterator;

    public TimerTest(Iterable<Integer> values) {
        this.iterator = values.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return iterator.next();
    }
}
