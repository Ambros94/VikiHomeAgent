package Things;

import InstanceCreator.ParameterInstanceCreator;
import NLP.Synonyms;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * An operation that can be performed in a determined Domain, that has optional and mandatory parameters.
 */
public class Operation extends Synonyms {
    /**
     * Set of parameter that can be added at the Operation
     */
    private Set<Parameter> optionalParameters;
    /**
     * Set of parameter mandatory. Without those the command cannot be completed
     */
    private Set<Parameter> mandatoryParameters;
    /**
     * List of sentences that can be used to invoke this operation
     */
    private Set<String> textInvocation = new HashSet<>();


    public Operation(String id, Set<String> words) {
        super(id, words);
        mandatoryParameters = new HashSet<>();
        optionalParameters = new HashSet<>();
    }

    public Operation(String id, Collection<String> words, Collection<String> textInvocation, Collection<Parameter> optionalParameters, Collection<Parameter> mandatoryParameters) {
        super(id, words);
        this.textInvocation = new HashSet<>(textInvocation);
        this.optionalParameters = new HashSet<>(optionalParameters);
        this.mandatoryParameters = new HashSet<>(mandatoryParameters);
    }

    /**
     * Build a Operation from a JSON, if the JSON is not properly formatted the given object will have
     * id=NoOperations? and words={NoWords?}
     *
     * @param json String JSON formatted representing the Parameter
     * @return Instance of Operation built with data from the JSON
     */
    public static Operation fromJson(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Parameter.class, new ParameterInstanceCreator());
        Gson gson = gsonBuilder.create();
        Operation op = gson.fromJson(json, Operation.class);
        op.updateSynonyms();
        return op;
    }

    /**
     * Get a sentence that can invoke this operation
     *
     * @return The first sentence contained in textInvocation set. If there are no sentences it will return "No default sentence inserted";
     */
    public String getFirstSentence() {
        if (textInvocation.toArray().length == 0) {
            return "No default sentence inserted";
        }
        return (String) textInvocation.toArray()[0];
    }

    /**
     * Noisy java methods
     */

    public Set<String> getTextInvocation() {
        return textInvocation;
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

    public void setTextInvocation(Set<String> textInvocation) {
        this.textInvocation = textInvocation;
    }

    public void setMandatoryParameters(Set<Parameter> mandatoryParameters) {
        this.mandatoryParameters = mandatoryParameters;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + super.getId() +
                "words" + getWords() +
                ", optionalParameters=" + optionalParameters +
                ", mandatoryParameters=" + mandatoryParameters +
                ", textInvocation=" + textInvocation +
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
