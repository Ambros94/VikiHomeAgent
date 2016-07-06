package Utility;

/**
 * Method object used to transform a String into a String[] using camelcase as separator
 * e.g. turnOn -> [turn,on]
 */
public class CamelCaseStringTokenizer {

    /**
     * @param s Camel case string that has to be split
     * @return String Array representing tokens
     */
    public String[] tokenize(String s) {
        return s.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
    }
}
