package NLP.Params;

import Things.ParameterType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MyNumberTest {
    @Test
    public void getType() throws Exception {
        MyNumber number = new MyNumber(25);
        assertEquals(ParameterType.NUMBER, number.getType());
    }

    @Test
    public void toJson() throws Exception {
        MyNumber number = new MyNumber(25);
        assertEquals("25", number.toJson());
    }

    @Test
    public void toString2() throws Exception {
        MyNumber number = new MyNumber(25);
        assertEquals("MyNumber{number=25}", number.toString());
    }

    @Test
    public void equals() throws Exception {
        MyNumber number = new MyNumber(25);
        MyNumber number2 = new MyNumber(26);
        MyNumber numberbis = new MyNumber(25);
        assertEquals(number,numberbis);
        assertNotEquals(number,number2);
        assertEquals(number.hashCode(),numberbis.hashCode());
        assertNotEquals(number.hashCode(),number2.hashCode());

    }

}