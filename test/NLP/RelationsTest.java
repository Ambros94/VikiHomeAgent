package NLP;

import org.junit.Test;

import static org.junit.Assert.*;

public class RelationsTest {
    @Test
    public void isVerbPrepositionRelation() throws Exception {
        assertTrue(Relations.isVerbPrepositionRelation("advmod"));
        assertFalse(Relations.isVerbPrepositionRelation("cadcad"));
    }

}