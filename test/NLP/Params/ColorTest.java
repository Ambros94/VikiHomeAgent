package NLP.Params;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColorTest {

    @Test
    public void toJson() throws Exception {
        Color c = new Color("#FF0001");
        assertEquals("[255,0,1]",c.toJson());
    }

}