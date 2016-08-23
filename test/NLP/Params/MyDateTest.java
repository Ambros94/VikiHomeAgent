package NLP.Params;

import Things.ParameterType;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created by lambrosini on 23/08/16.
 */
public class MyDateTest {
    @Test
    public void getType() throws Exception {
        MyDate date = new MyDate("25-12-1994");
        assertEquals(ParameterType.DATETIME, date.getType());
    }

    @Test
    public void toJson() throws Exception {
        MyDate date = new MyDate("25-12-1994");
        assertEquals("'25-12-1994'",date.toJson());
    }

    @Test
    public void equals() throws Exception {
        MyDate date = new MyDate("25-12-1994");
        MyDate datebis = new MyDate("25-12-1994");
        MyDate date2 = new MyDate("25-12-1995");
        assertEquals(date,datebis);
        assertNotEquals(date,date2);
        assertEquals(date.hashCode(),datebis.hashCode());
        assertNotEquals(date.hashCode(),date2.hashCode());

    }

    @Test
    public void toString2() throws Exception {
        MyDate date = new MyDate("25-12-1994");
        assertEquals("MyDate{date='25-12-1994'}",date.toString());

    }

}