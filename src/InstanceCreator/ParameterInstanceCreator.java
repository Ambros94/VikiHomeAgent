package InstanceCreator;

import Things.Parameter;
import Things.ParameterType;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class ParameterInstanceCreator implements InstanceCreator<Parameter> {

    @Override
    public Parameter createInstance(Type type) {
        return new Parameter("ERRORE", ParameterType.COLOR);
    }
}
