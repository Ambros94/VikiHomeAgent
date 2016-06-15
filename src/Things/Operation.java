package Things;

import InstanceCreator.ParameterInstanceCreator;
import NLP.Synonyms;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * An operation that can be performed in a determined Domain, that has optional and mandatory parameters.
 */
public class Operation extends Synonyms {

    private Set<Parameter> optionalParameters;
    private Set<Parameter> mandatoryParameters;
    private String textInvocation;

    public Operation(String id, Set<String> words) {
        super(id, words);
        mandatoryParameters = new HashSet<>();
        optionalParameters = new HashSet<>();
    }

    public Operation(String id, Set<String> words, String textInvocation, Set<Parameter> optionalParameters, Set<Parameter> mandatoryParameters) {
        super(id, words);
        this.textInvocation = textInvocation;
        this.optionalParameters = optionalParameters;
        this.mandatoryParameters = mandatoryParameters;
    }

    public static Operation fromJson(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Parameter.class, new ParameterInstanceCreator());
        Gson gson = gsonBuilder.create();
        Operation op = gson.fromJson(json, Operation.class);
        op.updateSynonyms();
        return op;
    }

    public Set<Parameter> getMandatoryParameters() {
        return mandatoryParameters;
    }

    public Set<Parameter> getOptionalParameters() {
        return optionalParameters;
    }

    public void setOptionalParameters(Set<Parameter> optionalParameters) {
        this.optionalParameters = optionalParameters;
    }

    public void setMandatoryParameters(Set<Parameter> mandatoryParameters) {
        this.mandatoryParameters = mandatoryParameters;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "" + super.toString() +
                "optionalParameters=" + optionalParameters +
                ", mandatoryParameters=" + mandatoryParameters +
                ", textInvocation='" + textInvocation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Operation operation = (Operation) o;

        return optionalParameters != null ? optionalParameters.equals(operation.optionalParameters) : operation.optionalParameters == null && (mandatoryParameters != null ? mandatoryParameters.equals(operation.mandatoryParameters) : operation.mandatoryParameters == null && (textInvocation != null ? textInvocation.equals(operation.textInvocation) : operation.textInvocation == null));

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (optionalParameters != null ? optionalParameters.hashCode() : 0);
        result = 31 * result + (mandatoryParameters != null ? mandatoryParameters.hashCode() : 0);
        result = 31 * result + (textInvocation != null ? textInvocation.hashCode() : 0);
        return result;
    }
}
