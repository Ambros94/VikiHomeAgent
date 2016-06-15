package Things;

import com.google.gson.Gson;

public class Parameter {

    private final String id;
    private final ParameterType type;

    public Parameter(String id, ParameterType type) {
        this.id = id;
        this.type = type;
    }

    public static Parameter fromJson(String json) {
        return new Gson().fromJson(json, Parameter.class);
    }

    public String getId() {
        return id;
    }

    public ParameterType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "id='" + id + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        return id != null ? id.equals(parameter.id) : parameter.id == null && type == parameter.type;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

}


