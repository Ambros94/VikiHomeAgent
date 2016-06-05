package NLP;

import NLP.Synonyms;
import edu.mit.jwi.item.POS;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class SynonymsTest {
    @Test
    public void equals() throws Exception {
        Synonyms synonyms = new Synonyms("lamp", Collections.singleton("light"));
        Synonyms synonyms2 = new Synonyms("lamp2", Collections.singleton("light"));
        Synonyms synonyms3 = new Synonyms("lamp", Collections.singleton("nothing"));

        assertEquals(synonyms, synonyms);
        assertEquals(synonyms, synonyms3);
        assertNotEquals(synonyms, synonyms2);

    }

    @Test
    public void getId() throws Exception {
        Synonyms synonyms = new Synonyms("lamp", Collections.singleton("light"));
        assertEquals("lamp", synonyms.getId());
    }

    @Test
    public void getWords() throws Exception {
        Synonyms synonyms = new Synonyms("lamp", Collections.singleton("light"));
        assertEquals(1, synonyms.getWords().size());
        Synonyms names = new Synonyms("lamp", new HashSet<>(Arrays.asList("light", "bulb")));
        assertEquals(2, names.getWords().size());
    }

    @Test
    public void getSynonyms() throws Exception {
        Synonyms synonyms = new Synonyms("lamp", Collections.singleton("light"));
        assertEquals(23, synonyms.getSynonyms(POS.NOUN).size());
        assertEquals(26, synonyms.getSynonyms(POS.ADJECTIVE).size());
        assertEquals(0, synonyms.getSynonyms(POS.ADVERB).size());
        assertEquals(14, synonyms.getSynonyms(POS.VERB).size());

        Synonyms names = new Synonyms("lamp", new HashSet<>(Arrays.asList("light", "bulb")));
        assertEquals(31, names.getSynonyms(POS.NOUN).size());
        assertEquals(26, names.getSynonyms(POS.ADJECTIVE).size());
        assertEquals(0, names.getSynonyms(POS.ADVERB).size());
        assertEquals(14, names.getSynonyms(POS.VERB).size());
    }

    @Test
    public void equalsSynonyms() throws Exception {
        Synonyms s = new Synonyms("light1", Collections.singleton("light"));
        assertTrue(s.equalsSynonyms("luminance"));
        assertFalse(s.equalsSynonyms("thing"));
        assertTrue(s.equalsSynonyms("unaccented", POS.ADJECTIVE));
        assertFalse(s.equalsSynonyms("unaccented", POS.ADVERB));
        assertTrue(s.equalsSynonyms("get off", POS.VERB));
        assertTrue(s.equalsSynonyms("twinkle", POS.NOUN));
    }

}