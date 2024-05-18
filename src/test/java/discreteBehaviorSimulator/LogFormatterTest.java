package discreteBehaviorSimulator;

import static org.junit.Assert.assertEquals;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Formatter;
import java.util.logging.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.junit.Test;

public class LogFormatterTest {

    @Test
    public void testFormat() {
        // Entree
        LogRecord record = new LogRecord(Level.INFO, "Test message");
        long millis = System.currentTimeMillis();
        record.setMillis(millis);

        // Creation de l'objet LogFormatter
        LogFormatter formatter = new LogFormatter();

        // Appel de la méthode format()
        String formatted = formatter.format(record);

        // Résultat attendu
        String expected = this.calcDate(millis) + ": INFO\nTest message\n";
        System.out.println(expected);
        System.out.println(formatted);
        
        // Vérification
        assertEquals(expected, formatted);
    }

    @Test
    public void testCalcDate() {
        // Entrée
        long millis = System.currentTimeMillis();

        // Appel de la méthode calcDate()
        String formattedDate = this.calcDate(millis);

        // Résultat attendu
        // Format de la date : "yyyy.MM.dd HH:mm:ss.SS"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
        String expectedDate = dateFormat.format(new Date(millis));

        // Vérification
        assertEquals(expectedDate, formattedDate);
    }
    
    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }
}
