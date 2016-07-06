package InstanceCreator;

import Things.Domain;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;
import java.util.Collections;

public class DomainInstanceCreator implements InstanceCreator<Domain> {
    @Override
    public Domain createInstance(Type type) {
        Domain t = new Domain("NoDomain?", Collections.singleton("NoWords?"));
        return t;
    }
}
