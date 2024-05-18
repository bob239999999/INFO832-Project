package discrete_behavior_simulator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * @author Flavien Vernier
 *
 */
public class LogFormatter  extends Formatter {
	
	/**
     * Formats a log record into a custom string format.
     *
     * @param rec the log record to format
     * @return a formatted string consisting of the date, log level, and log message.
     */

	public String format(LogRecord rec) {
        StringBuilder buf = new StringBuilder();

        buf.append(calcDate(rec.getMillis()));
        buf.append(": ");
        buf.append(rec.getLevel());
        buf.append(System.getProperty("line.separator"));
        buf.append(formatMessage(rec));
        buf.append(System.getProperty("line.separator"));

        return buf.toString();
    }

	/**
	 * Transforms a millisecond time stamp into a human-readable date string using a detailed format.
	 * This method utilizes the standard format that includes the full date and precise time up to milliseconds,
	 * making it suitable for logging and time stamp purposes.
	 *
	 * @param milliseconds the epoch milliseconds to be converted into a formatted date string. 
	 *                  Negative values indicate times before the Unix epoch.
	 * @return the formatted date string in the pattern "yyyy.MM.dd HH:mm:ss.SS".
	 */
	public String calcDate(long millisecs) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
	    Date resultdate = new Date(millisecs);
	    return dateFormat.format(resultdate);
	}


	/**
     * Provides a header string for the log.
     *
     * @param h the handler to which this formatter is assigned
     * @return an empty string as the header.
     */
    @Override
    public String getHead(Handler h) {
        return "";
    }

    /**
     * Provides a footer string for the log.
     *
     * @param h the handler to which this formatter is assigned
     * @return an empty string as the footer.
     */
    @Override
    public String getTail(Handler h) {
        return "";
    }

}
