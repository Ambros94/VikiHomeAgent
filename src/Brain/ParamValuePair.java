package Brain;


import Things.Parameter;

public class ParamValuePair implements JSONParsable {


    private final Object value;
    private final Parameter parameter;

    public ParamValuePair(Parameter parameter, Object value) {
        this.parameter = parameter;
        this.value = value;
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

        ParamValuePair that = (ParamValuePair) o;

        return parameter.equals(that.parameter);

    }

    @Override
    public int hashCode() {
        return parameter.hashCode();
    }

    @Override
    public String toJson() {
        String json = "{" + "{" +
                "'id':'" + getParameter().getId() + "'" +
                "'type':'" + getParameter().getType() + "'" +
                "'value':'" + getValue() + "'" +
                "}";
        return json.replace("\'", "\"");
    }
}
