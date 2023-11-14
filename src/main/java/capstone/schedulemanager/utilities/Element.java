package capstone.schedulemanager.utilities;

/**
 * A functional interface used to access an element on a string.
 * The Lambda expressions derived from this interface make the code more readable and concise by reducing the frequency
 * of having to write different split functions to access different parts of a string. The implementation of key words
 * in the expression make it easier to understand what element is being accessed from the String value of an attribute.
 * */
@FunctionalInterface
public interface Element {

    /** This method is used to return elements from a String.
     * The attribute is the String that contains the element to be extracted.
     * @param attribute A String attribute or a String conversion of an attribute of an object.
     * @return An element in a String*/
    String getElement(String attribute);
}
