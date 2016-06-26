package Brain;

import Things.Domain;
import Things.Operation;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;


public class DomainOperationPairTest {

    private DomainOperationPair domainOperationPair;
    Domain domain;
    Operation operation;

    @Before
    public void init() {
        domain = new Domain("things", Collections.singleton("thing words"));
        operation = new Operation("op1", Collections.singleton("op1Word"));
        operation.setTextInvocation(Collections.singleton("turn thig thing on"));
        domainOperationPair = new DomainOperationPair(domain, operation, 0.45d);
    }

    @Test
    public void getDomain() {
        assertEquals(domain, domainOperationPair.getDomain());
    }

    @Test
    public void getOperation() {
        assertEquals(operation, domainOperationPair.getOperation());
    }

    @Test
    public void getConfidence() {
        assertEquals(0.45d, domainOperationPair.getConfidence(), 0.0001d);
    }

    @Test
    public void toStringTest() {
        assertEquals("DomainOperationPair{domain=things, operation=op1, confidence=0.45}", domainOperationPair.toString());
    }

    @Test
    public void equals() {
        DomainOperationPair domainOperationPair2 = new DomainOperationPair(domain, operation, 0.45d);
        assertEquals(domainOperationPair2, domainOperationPair);
    }

}