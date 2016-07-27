package Brain;


import Things.Parameter;

public class ParamValue<T extends JSONParsable> implements JSONParsable {


    private final T value;
    private final Parameter parameter;

    public ParamValue(Parameter parameter, T value) {
        this.parameter = parameter;
        this.value = value;
    }


    @Override
    public String toJson() {
        String json = "{" +
                "'id':'" + getParameter().getId() + "'" + "," +
                "'type':'" + getParameter().getType() + "'" + "," +
                "'value':" + getValue().toJson()  +
                "}";
        return json.replace("\'", "\"");
    }

    public T getValue() {
        return value;
    }

    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParamValue pair = (ParamValue) o;

        return value != null ? value.equals(pair.value) : pair.value == null && (parameter != null ? parameter.equals(pair.parameter) : pair.parameter == null);

    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (parameter != null ? parameter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParamValue{" +
                "value=" + value +
                ", parameter=" + parameter +
                '}';
    }
}
