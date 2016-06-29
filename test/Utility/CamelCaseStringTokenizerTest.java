package Utility;

import org.junit.Test;

import static org.junit.Assert.*;


public class CamelCaseStringTokenizerTest {
    @Test
    public void tokenize() throws Exception {
        String first = "ThisMethodISCamelCase";
        String[] expectedFirst = {"This", "Method", "IS", "Camel", "Case"};
        String[] computedFirst = new CamelCaseStringTokenizer().tokenize(first);
        assertEquals(expectedFirst.length, computedFirst.length);
        for (int i = 0; i < expectedFirst.length; i++) {
            assertEquals(expectedFirst[i], computedFirst[i]);
        }
    }

}