package NLP;

import Things.Domain;
import Things.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;


public class StanfordNLPGraphTest {

    private Domain light = new Domain("light", Collections.singleton("lamp"));
    private Operation turn_on = new Operation("turn_on", Collections.singleton("turn on"));
    private Operation turn_off = new Operation("turn_off", Collections.singleton("turn_off"));
    private Domain heater = new Domain("heater", Collections.singleton("lamp"));
    Operation set_temperature = new Operation("turn_off", Collections.singleton("turn_off"));


    @Test
    public void containsDomain() throws Exception {
        StanfordNLPGraph nlpGraph = new StanfordNLPGraph("Could you please turn on the light?");
        assertTrue(nlpGraph.containsDomain(light) != -1);
        assertTrue(nlpGraph.containsDomain(heater) == -1);
    }

    @Test
    public void containsOperation() throws Exception {
        StanfordNLPGraph nlpGraph = new StanfordNLPGraph("Could you please turn on the light?");
        light.setOperations(new HashSet<>(Arrays.asList(turn_on, turn_off)));
        assertTrue(nlpGraph.containsOperation(turn_on, light, nlpGraph.containsDomain(light)) != -1);
        assertTrue(nlpGraph.containsOperation(turn_off, light, nlpGraph.containsDomain(light)) == -1);
    }

    @Test(expected = RuntimeException.class)
    public void containsOperationException() throws Exception {
        StanfordNLPGraph nlpGraph = new StanfordNLPGraph("Could you please turn on the light?");
        nlpGraph.containsOperation(turn_off, heater, nlpGraph.containsDomain(light));
    }


}