package capstone.schedulemanager.utilities;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * A functional interface used to convert Timestamp object types to other time object types.
 * The Lambda expressions derived from this interface make the code more readable and concise by reducing the amount
 * of required code necessary to read and write. The implementation for this lambda expression eliminates the
 * repetition of writing the chained methods for each Timestamp object while implementing the use of key words that
 * make it easier to understand the purpose of the code.
 *
 * */
@FunctionalInterface
public interface Conversion {

    /** This method is used to convert Timestamp objects into Local Date Time objects.
     * The stamp is the Timestamp object.
     * @param stamp The Timestamp object that needs to be stored as a Local Date Time object.
     * @return The LocalDateTime conversion*/
    LocalDateTime getConversion(Timestamp stamp);
}
