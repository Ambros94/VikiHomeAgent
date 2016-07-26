package Brain;

import NLP.Params.FreeText;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Test;

public class ParamValueTest {
    @Test
    public void getValue() throws Exception {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
    }

    @Test
    public void getParameter() throws Exception {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
    }

    @Test
    public void equals() throws Exception {


    }

    @Test
    public void hashTest() {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        ParamValue pair3 = new ParamValue<>(p, new FreeText("thing"));
    }

    @Test
    public void StringerTest() {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
    }
}