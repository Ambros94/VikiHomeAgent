package Brain;


import Things.Operation;
import Things.Parameter;
import Things.Domain;

import java.util.HashSet;
import java.util.Set;

public class Command {

    private final Operation o;
    private final Domain t;
    private final Set<ParamValuePair> optionalParameters;
    private final Set<ParamValuePair> mandatoryParameters;

    public Command(Domain t, Operation o) {
        this.t = t;
        this.o = o;
        mandatoryParameters = new HashSet<>();
        optionalParameters = new HashSet<>();
    }

    public void addMandatoryParameter(Parameter p, Object value) {
        if (!mandatoryParameters.add(new ParamValuePair(p, value))) {
            throw new RuntimeException("Parameter" + p + "is yet present, with value" + value);
        }
    }

    public void addOptionalParameters(Parameter p, Object value) {
        if (!optionalParameters.add(new ParamValuePair(p, value))) {
            throw new RuntimeException("Parameter" + p + "is yet present, with value" + value);
        }
    }

    @Override
    public String toString() {
        return "Command{" +
                "o=" + o +
                ", t=" + t +
                ", optionalParameters=" + optionalParameters +
                ", mandatoryParameters=" + mandatoryParameters +
                '}';
    }
}
