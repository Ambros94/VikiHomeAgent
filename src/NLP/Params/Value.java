package NLP.Params;

import Things.ParameterType;

public abstract class Value {
    /**
     * Get the type of the parameter
     *
     * @return Type of this parameter
     */
    public abstract ParameterType getType();

    /**
     * Convert the value to JSON
     * @return String formatted as JSON value. (eg. adds quotes for string, square brackets for arrays)
     */
    public abstract String toJson();
}
