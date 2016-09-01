package Brain.Confidence;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DiscreteTest {
    @Test
    public void discreteDouble() throws Exception {
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

    @Test
    public void discreteInt() throws Exception {
        assertEquals(Discrete.ZERO, Discrete.discrete(0));
        assertEquals(Discrete.ONE, Discrete.discrete(1));
        assertEquals(Discrete.TWO, Discrete.discrete(2));
        assertEquals(Discrete.THREE, Discrete.discrete(3));
        assertEquals(Discrete.FOUR, Discrete.discrete(4));
        assertEquals(Discrete.FIVE, Discrete.discrete(5));
        assertEquals(Discrete.SIX, Discrete.discrete(6));
        assertEquals(Discrete.SEVEN, Discrete.discrete(7));
        assertEquals(Discrete.EIGHT, Discrete.discrete(8));
        assertEquals(Discrete.NINE, Discrete.discrete(9));
        assertEquals(Discrete.TEN, Discrete.discrete(10));
    }

    @Test(expected = RuntimeException.class)
    public void discrete3() throws Exception {
        Discrete.discrete(1.1d);
    }

    @Test(expected = RuntimeException.class)
    public void discrete4() throws Exception {
        Discrete.discrete(-7);
    }

    @Test(expected = RuntimeException.class)
    public void discrete5() throws Exception {
        Discrete.discrete(71);
    }

}