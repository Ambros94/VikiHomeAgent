package Things;

import NLP.Synonyms;

import java.util.HashSet;
import java.util.Set;

/**
 * An operation that can be performed in a determined Domain, that has optional and mandatory parameters.
 */
public class Operation extends Synonyms {

    Set<Parameter> optionalParameters;
    Set<Parameter> mandatoryParameters;

    public Operation(String id, Set<String> words) {
        super(id, words);
        mandatoryParameters = new HashSet<>();
        optionalParameters = new HashSet<>();
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

}
