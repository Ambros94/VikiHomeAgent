package NLP;

import edu.mit.jwi.item.POS;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class WordNetTest {
    @Test
    public void getSynonyms() throws Exception {
        assertEquals(0, WordNet.getSynonyms("turn_on", POS.NOUN).size());
        assertEquals(0, WordNet.getSynonyms("turn_on", POS.ADJECTIVE).size());
        assertEquals(20, WordNet.getSynonyms("turn_on", POS.VERB).size());
    }

    @Test
    public void getSynonyms2() throws Exception {
        assertEquals(21, WordNet.getSynonyms("light", POS.NOUN).size());
        assertEquals(26, WordNet.getSynonyms("light", POS.ADJECTIVE).size());
        assertEquals(14, WordNet.getSynonyms("light", POS.VERB).size());
    }

    @Test
    public void getSynonyms3() throws Exception {
        assertEquals(9, WordNet.getSynonyms("red", POS.NOUN).size());
        assertEquals(16, WordNet.getSynonyms("red", POS.ADJECTIVE).size());
        assertEquals(0, WordNet.getSynonyms("red", POS.VERB).size());
    }

    @Test
    public void regExpCheck() throws Exception {
        Set<String> underscore = WordNet.getSynonyms("turn_on", POS.NOUN);
        Set<String> space = WordNet.getSynonyms("turn on", POS.NOUN);
        assertEquals(underscore.size(), space.size());
        for (String s : underscore) {
            assertTrue(space.contains(s));
            assertFalse(s.contains("_"));
        }
    }

    @Test
    public void whiteSpacesCheck() throws Exception {
        Set<String> underscore = WordNet.getSynonyms(" turn_on", POS.NOUN);
        Set<String> space = WordNet.getSynonyms("  turn on   ", POS.NOUN);
        assertEquals(underscore.size(), space.size());
        for (String s : underscore) {
            assertTrue(space.contains(s));
            assertFalse(s.contains("_"));
        }
    }


}