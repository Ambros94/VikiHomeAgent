package Brain;


import Things.Domain;
import Things.Operation;
import Things.Parameter;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A command is a action, relative to a Domain, an Operation, and values for his parameters
 * It contains also the sentence that has been said. One of the sentence mapped with that command in the sentence
 * (different parameters value can occur)
 * Finally there is an confidence value between 0-1, higher is better
 */
public class Command implements JSONParsable {

    private final Operation operation;
    private final Domain domain;
    private final Set<ParamValue> pairs;
    private final String saidSentence;
    private final double confidence;
    private CommandStatus status;


    public Command(Domain domain, Operation operation, String saidSentence, double confidence) {
        this.domain = domain;
        this.operation = operation;
        this.saidSentence = saidSentence;
        this.confidence = confidence;
        this.pairs = new LinkedHashSet<>();
        this.status = CommandStatus.UNKNOWN;
    }

    public Operation getOperation() {
        return operation;
    }

    public Domain getDomain() {
        return domain;
    }

    public Set<ParamValue> getParamValue() {
        return pairs;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    /**
     * Add a parameter to the collection, if there is yet a value for this parameter it throws a runTimeException
     *
     * @param paramValue that you want to add
     */
    void addParamValue(ParamValue paramValue) {
        if (!(operation.getOptionalParameters().contains(paramValue.getParameter()) || operation.getMandatoryParameters().contains(paramValue.getParameter()))) {
            throw new RuntimeException("Parameter" + paramValue.getParameter() + "now valid for this operation" + operation);
        }
        if (!pairs.add(paramValue)) {
            throw new RuntimeException("Parameter" + paramValue.getParameter() + "is yet present, with value" + paramValue.getValue());
        }
    }

    /**
     * Adds every element in the collection to the paramValuePair list
     *
     * @param paramValues Collection of elements you wanna add
     */
    public void addParamValue(Collection<ParamValue> paramValues) {
        paramValues.stream().filter(pair -> pair != null).forEach(this::addParamValue);
    }


    @Override
    public String toString() {
        return "Command{" +
                "operation=" + operation +
                ", domain=" + domain +
                ", pairs=" + pairs +
                ", confidence=" + confidence +
                ", status=" + status +
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
        json.append("'status':'").append(status).append("'");
        json.append(",");
        json.append("'understood':'").append(operation.getFirstSentence().replace('\'', ' ')).append("'");
        json.append(",");
        json.append("'parameters':[");
        int i = 1;
        for (ParamValue pair : pairs) {
            json.append(pair.toJson());
            if (i != pairs.size())
                json.append(",");
            i++;
        }
        json.append("]}");
        return json.toString().replace("\'", "\"");
    }

    /**
     * Get to know if a command has a value for each mandatory parameter
     *
     * @return true is the command has a ParamValue for each mandatoryParam of his operation.
     * false otherwise
     */
    public boolean isFullFilled() {
        for (Parameter p : operation.getMandatoryParameters()) {
            if (pairs.stream().filter(pair -> pair.getParameter().equals(p)).collect(Collectors.toList()).size() == 0)
                return false;
        }
        return true;
    }
}


