package NLP.Params;

import Things.ParameterType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LocationTest {
    @Test
    public void getType() throws Exception {
        Location l = new Location("London");
        assertEquals(l.getType(), ParameterType.LOCATION);
    }

    @Test
    public void toJson() throws Exception {
        Location l = new Location("London");
        assertEquals("'London'", l.toJson());

    }

    @Test
    public void equals() throws Exception {
        Location l1 = new Location("London");
        Location l1bis = new Location("London");
        Location l2 = new Location("Madrid");
        assertEquals(l1,l1bis);
        assertNotEquals(l1,l2);
        assertEquals(l1.hashCode(),l1bis.hashCode());
        assertNotEquals(l1.hashCode(),l2.hashCode());
    }

    @Test
    public void hashCodeTest() throws Exception {
        Location l1 = new Location("London");
        Location l1bis = new Location("London");
        Location l2 = new Location("Madrid");
        assertEquals(l1.hashCode(),l1bis.hashCode());
        assertNotEquals(l1.hashCode(),l2.hashCode());
    }

}