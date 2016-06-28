package Brain;


import Things.Domain;
import Things.Operation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Command implements JSONParsable {

    private final Operation operation;
    private final Domain domain;
    private final Set<ParamValuePair> parameters;
    private final String saidSentence;
    private final double confidence;


    public Command(Domain domain, Operation operation, String saidSentence, double confidence) {
        this.domain = domain;
        this.operation = operation;
        this.saidSentence = saidSentence;
        this.confidence = confidence;
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

    public double getConfidence() {
        return confidence;
    }

    public void addParamValue(ParamValuePair paramValuePair) {
        if (!(operation.getOptionalParameters().contains(paramValuePair.getParameter()) || operation.getMandatoryParameters().contains(paramValuePair.getParameter()))) {
            throw new RuntimeException("Parameter" + paramValuePair.getParameter() + "now valid for this operation" + operation);
        }
        if (!parameters.add(paramValuePair)) {
            throw new RuntimeException("Parameter" + paramValuePair.getParameter() + "is yet present, with value" + paramValuePair.getValue());
        }
    }

    public void addParamValue(Collection<ParamValuePair> paramValuePairs) {
        paramValuePairs.forEach(this::addParamValue);
    }


    @Override
    public String toString() {
        return "Command{" +
                "operation=" + operation +
                ", domain=" + domain +
                ", parameters=" + parameters +
                ", confidence=" + confidence +
                '}';
    }

    @Override
    public String toJson() {
        StringBuilder json = new StringBuilder("{");
        json.append("'domain':'").append(domain.getId()).append("'");
        json.append(",");
        json.append("'operation':'").append(operation.getId()).append("'");
        json.append(",");
        json.append("'confidence':'").append(confidence).append("'");
        json.append(",");
        json.append("'said':'").append(saidSentence.replace('\'', ' ')).append("'");
        json.append(",");
        json.append("'understood':'").append(operation.getOneSentence().replace('\'', ' ')).append("'");
        json.append(",");
        json.append("'paramValuePairs':[");
        int i = 1;
        for (ParamValuePair pair : parameters) {
            json.append(pair.toJson());
            if (i != parameters.size())
                json.append(",");
        }
        json.append("]}");
        return json.toString().replace("\'", "\"");
    }

}
