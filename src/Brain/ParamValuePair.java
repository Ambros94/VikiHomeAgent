package Brain;


import Things.Parameter;

public class ParamValuePair implements JSONParsable {


    private final Object value;
    private final Parameter parameter;

    public ParamValuePair(Parameter parameter, Object value) {
        this.parameter = parameter;
        this.value = value;
    }


    @Override
    public String toJson() {
        String json = "{" +
                "'id':'" + getParameter().getId() + "'" + "," +
                "'type':'" + getParameter().getType() + "'" + "," +
                "'value':'" + getValue() + "'" +
                "}";
        return json.replace("\'", "\"");
    }

    public Object getValue() {
        return value;
    }

    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParamValuePair pair = (ParamValuePair) o;

        if (value != null ? !value.equals(pair.value) : pair.value != null) return false;
        return parameter != null ? parameter.equals(pair.parameter) : pair.parameter == null;

    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (parameter != null ? parameter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParamValuePair{" +
                "value=" + value +
                ", parameter=" + parameter +
                '}';
    }
}
