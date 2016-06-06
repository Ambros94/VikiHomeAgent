package Brain;


import Things.Operation;
import Things.Parameter;
import Things.Domain;

import java.util.HashSet;
import java.util.Set;

public class Command {

    private final Operation operation;
    private final Domain domain;
    private final Set<ParamValuePair> parameters;


    public Command(Domain domain, Operation operation) {
        this.domain = domain;
        this.operation = operation;
        parameters = new HashSet<>();
    }


    public Operation getOperation() {
        return operation;
    }

    public Domain getDomain() {
        return domain;
    }

    public Set<ParamValuePair> getParamValue() {
        return parameters;
    }

    public void addParamValue(ParamValuePair paramValuePair) {
        if (!(operation.getOptionalParameters().contains(paramValuePair.getParameter()) || operation.getMandatoryParameters().contains(paramValuePair.getParameter()))) {
            throw new RuntimeException("Parameter" + paramValuePair.getParameter() + "now valid for this operation" + operation);
        }
        if (!parameters.add(paramValuePair)) {
            throw new RuntimeException("Parameter" + paramValuePair.getParameter() + "is yet present, with value" + paramValuePair.getValue());
        }
    }

    @Override
    public String toString() {
        return "Command{" +
                "operation=" + operation +
                ", domain=" + domain +
                ", parameters=" + parameters +
                '}';
    }
}
