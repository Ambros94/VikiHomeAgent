package Brain;

import Things.Domain;
import Things.Operation;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;


public class DomainOperationPairTest {

    private DomainOperationPair domainOperationPair;
    private Domain domain;
    private Operation operation;

    @Before
    public void init() {
        domain = new Domain("things", Collections.singleton("thing words"));
        operation = new Operation("op1", Collections.singleton("op1Word"));
        operation.setTextInvocation(Collections.singleton("turn thig thing on"));
        domainOperationPair = new DomainOperationPair(domain, 0.25d, operation, 0.25d);
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
    public void toStringTest() {
        System.out.println(domainOperationPair.toString());
        assertEquals("DOP{domain=things, operation=op1, domainConfidence=0.25, operationConfidence=0.25}", domainOperationPair.toString());
    }

    @Test
    public void equals() {
        DomainOperationPair domainOperationPair2 = new DomainOperationPair(domain, 0.25d, operation, 0.25d);
        assertEquals(domainOperationPair2, domainOperationPair);
    }

    @Test
    public void hashCodeTest() {
        DomainOperationPair domainOperationPair1 = new DomainOperationPair(domain, 0.4d, operation, 0.48d);
        DomainOperationPair domainOperationPair2 = new DomainOperationPair(domain, 0.4d, operation, 0.45d);
        DomainOperationPair domainOperationPair3 = new DomainOperationPair(domain, 0.4d, operation, 0.48d);
        assertEquals(domainOperationPair1.hashCode(), domainOperationPair3.hashCode());
        assertNotEquals(domainOperationPair1.hashCode(), domainOperationPair2.hashCode());

    }

}