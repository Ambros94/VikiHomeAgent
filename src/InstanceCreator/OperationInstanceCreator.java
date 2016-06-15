package InstanceCreator;

import Things.Operation;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;
import java.util.Collections;


public class OperationInstanceCreator implements InstanceCreator<Operation> {

    @Override
    public Operation createInstance(Type type) {
        return new Operation("rotto", Collections.singleton("rotto"));
    }
}
