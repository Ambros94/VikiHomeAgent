package Things;

import edu.mit.jwi.item.POS;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static javafx.scene.input.KeyCode.T;
import static org.junit.Assert.*;

public class SynonymsTest {
    @Test
    public void equals() throws Exception {

    }

    @Test
    public void getId() throws Exception {
        Synonyms synonyms = new Synonyms("lamp", POS.NOUN, Collections.singleton("light"));
        assertEquals("lamp", synonyms.getId());
    }

    @Test
    public void getWords() throws Exception {
        Synonyms synonyms = new Synonyms("lamp", POS.NOUN, Collections.singleton("light"));
        assertEquals(1, synonyms.getWords().size());
        Synonyms names = new Synonyms("lamp", POS.NOUN, new HashSet<String>(Arrays.asList("light", "bulb")));
        assertEquals(2, names.getWords().size());
    }

    @Test
    public void getSynonyms() throws Exception {
        Synonyms synonyms = new Synonyms("lamp", POS.NOUN, Collections.singleton("light"));
        assertEquals(23, synonyms.getSynonyms().size());
        Synonyms names = new Synonyms("lamp", POS.NOUN, new HashSet<String>(Arrays.asList("light", "bulb")));
        assertEquals(31, names.getSynonyms().size());
    }

}