package Brain.Confidence;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DiscreteTest {
    @Test
    public void discrete() throws Exception {
        assertEquals(Discrete.ZERO, Discrete.discrete(0.02));
        assertEquals(Discrete.ONE, Discrete.discrete(0.10));
        assertEquals(Discrete.TWO, Discrete.discrete(0.23));
        assertEquals(Discrete.THREE, Discrete.discrete(0.390));
        assertEquals(Discrete.FOUR, Discrete.discrete(0.40));
        assertEquals(Discrete.FIVE, Discrete.discrete(0.50));
        assertEquals(Discrete.SIX, Discrete.discrete(0.60));
        assertEquals(Discrete.SEVEN, Discrete.discrete(0.70));
        assertEquals(Discrete.EIGHT, Discrete.discrete(0.88));
        assertEquals(Discrete.NINE, Discrete.discrete(0.90));
        assertEquals(Discrete.TEN, Discrete.discrete(1.090));
    }

    @Test(expected = RuntimeException.class)
    public void discrete2() throws Exception {
        Discrete.discrete(-0.1d);
    }

    @Test(expected = RuntimeException.class)
    public void discrete3() throws Exception {
        Discrete.discrete(1.1d);
    }

}