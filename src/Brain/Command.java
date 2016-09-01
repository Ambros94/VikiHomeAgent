package Brain;


import NLP.Params.Value;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import Utility.Config;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A command is a action, relative to a Domain, an Operation, and values for his parameters
 * It contains also the sentence that has been said. One of the sentence mapped with that command in the sentence
 * (different parameters value can occur)
 * Finally there is an finalConfidence value between 0-1, higher is better
 */
public class Command implements JSONParsable, Serializable {

    private final Operation operation;
    private final Domain domain;
    private final Set<ParamValue> pairs;
    private final String saidSentence;
    private double finalConfidence;
    private CommandStatus status;
    private int wrongParameters;
    private int rightParameters;
    private double domainConfidence;
    private double operationConfidence;


    public Command(Domain domain, Operation operation, String saidSentence, double domainConfidence, double operationConfidence) {
        this.domain = domain;
        this.domainConfidence = domainConfidence;
        this.operationConfidence = operationConfidence;
        this.operation = operation;
        this.saidSentence = saidSentence;
        this.pairs = new LinkedHashSet<>();
        this.status = CommandStatus.UNKNOWN;
    }

    void addParamValue(ParameterType type, Value value) {
        if (value == null)
            return;
        if (!value.getType().equals(type))
            throw new RuntimeException("Code should not be here!");
        pairs.addAll(operation.getOptionalParameters().stream().filter(p -> p.getType().equals(type)).map(p -> new ParamValue<>(p, value)).collect(Collectors.toList()));
        pairs.addAll(operation.getMandatoryParameters().stream().filter(p -> p.getType().equals(type)).map(p -> new ParamValue<>(p, value)).collect(Collectors.toList()));
        updateStatus();
    }

    private void updateStatus() {
        ParameterType missingType;
        final double MIN_CONFIDENCE = Config.getConfig().getMinConfidence();
        if (getFinalConfidence() < MIN_CONFIDENCE) {
            setStatus(CommandStatus.LOW_CONFIDENCE);
            return;
        }
        if ((missingType = isFullFilled()) != null) {
            setStatus(CommandStatus.valueOf("MISSING_" + missingType));
            return;
        }
        setStatus(CommandStatus.OK);
    }


    @Override
    public String toString() {
        return "Command{" +
                "operation=" + operation +
                ", domain=" + domain +
                ", pairs=" + pairs +
                ", finalConfidence=" + finalConfidence +
                ", domainConfidence=" + domainConfidence +
                ", operationConfidence=" + operationConfidence +
                ", rightParameters=" + rightParameters +
                ", wrongParameters=" + wrongParameters +
                ", status=" + status +
                '}';
    }

    @Override
    public String toJson() {
        DecimalFormat df = new DecimalFormat("0.00");
        StringBuilder json = new StringBuilder("{");
        json.append("'confidence':'").append(df.format(finalConfidence)).append("'");
        json.append(",");
        json.append("'said':'").append(saidSentence.replace('\'', ' ')).append("'");
        json.append(",");
        json.append("'domain':'").append(domain.getId()).append("'");
        json.append(",");
        json.append("'operation':'").append(operation.getId()).append("'");
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
     * Get to know if a command has a value for each mandatory parameter of his operation.
     *
     * @return true is the command has a ParamValue for each mandatoryParam of his operation.
     * false otherwise
     */
    ParameterType isFullFilled() {
        for (Parameter p : operation.getMandatoryParameters()) {
            if (pairs.stream().filter(pair -> pair.getParameter().equals(p)).collect(Collectors.toList()).size() == 0)
                return p.getType();
        }
        return null;
    }

    /**
     * Allows you to check if the command is
     *
     * @param domainId    Unique identifier of the domain contained in the command
     * @param operationId Unique identifier of the operation contained in the command
     * @return true if both domainId and OperationId are equals with Domain and Operation contained in the command.
     */
    public boolean equalsIds(String domainId, String operationId) {
        return (this.getDomain().getId().equals(domainId) && this.getOperation().getId().equals(operationId));
    }


    String getSaidSentence() {
        return saidSentence;
    }

    public CommandStatus getStatus() {
        return status;
    }

    public Operation getOperation() {
        return operation;
    }

    public Domain getDomain() {
        return domain;
    }

    Set<ParamValue> getParamValue() {
        return pairs;
    }

    public double getFinalConfidence() {
        return finalConfidence;
    }

    void addBonusConfidence() {
        this.rightParameters++;
    }

    void subBonusConfidence() {
        this.wrongParameters++;
    }

    public double getDomainConfidence() {
        return domainConfidence;
    }


    public double getOperationConfidence() {
        return operationConfidence;
    }

    public int getWrongParameters() {
        return wrongParameters;
    }

    public int getRightParameters() {
        return rightParameters;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    public void setFinalConfidence(double finalConfidence) {
        this.finalConfidence = finalConfidence;
        updateStatus();
    }

    public void setProbabilities(double domainProbability, double operationProbability) {
        this.domainConfidence = domainProbability;
        this.operationConfidence = operationProbability;
    }

    public void setOperationConfidence(double operationConfidence) {
        this.operationConfidence = operationConfidence;
    }
}


