package NLP;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RelationsTest {
    @Test
    public void isVerbPrepositionRelation() throws Exception {
        assertTrue(Relations.isVerbPrepositionRelation("advmod"));
        assertFalse(Relations.isVerbPrepositionRelation("cadcad"));
    }

}