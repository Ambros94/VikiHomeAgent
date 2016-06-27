package InstanceCreator;

import Things.Domain;
import Things.Operation;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DomainInstanceCreator implements InstanceCreator<Domain> {
    @Override
    public Domain createInstance(Type type) {
        Domain t = new Domain("NoDomain?", Collections.singleton("NoWords?"));
        Operation turnon = new Operation("NoOperations?", Collections.singleton("NoWords?"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnon);
        t.setOperations(operationList);
        return t;
    }
}
