package discrete_behavior_simulator;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Formatter;
import java.util.logging.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.junit.Test;

import discrete_behavior_simulator.LogFormatter;

public class LogFormatterTest {

    @Test
    public void testFormat() {
        // Entrance
        LogRecord record = new LogRecord(Level.INFO, "Test message");
        long millis = System.currentTimeMillis();
        record.setMillis(millis);

        // Creating the LogFormatter object
        LogFormatter formatter = new LogFormatter();

        // Calling the format() method
        String formatted = formatter.format(record);

        // Expected result
        String expected = this.calcDate(millis) + ": INFO\nTest message\n";
        System.out.println(expected);
        System.out.println(formatted);
        
        // Verification
        assertEquals(expected, formatted);
    }

    @Test
    public void testCalcDate() {
        // Entrance
        long millis = System.currentTimeMillis();

        // Calling the calcDate() method
        String formattedDate = this.calcDate(millis);

        // Expected result
        // Date format: "yyyy.MM.dd HH:mm:ss.SS"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
        String expectedDate = dateFormat.format(new Date(millis));

        // Verification
        assertEquals(expectedDate, formattedDate);
    }

    @Test
    public void testCalcDateWithNegativeMillis() {
        // Create an instance of LogFormatter
        LogFormatter formatter = new LogFormatter();
        
        // Define a negative milliseconds value for testing
        long negativeMillis = -1L;
    
        // Verify that calcDate throws an IllegalArgumentException when passed negative milliseconds
        assertThrows(IllegalArgumentException.class, () -> {
            formatter.calcDate(negativeMillis);
        });
    }

    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }
}
