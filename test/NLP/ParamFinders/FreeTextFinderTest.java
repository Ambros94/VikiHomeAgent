package NLP.ParamFinders;

import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class FreeTextFinderTest {

    private FreeTextFinder finder;

    @Before
    public void init() {
        finder = new FreeTextFinder();
    }

    @Test
    public void getAssociatedType() throws Exception {
        assertEquals(ParameterType.FREE_TEXT, finder.getAssociatedType());
    }
}