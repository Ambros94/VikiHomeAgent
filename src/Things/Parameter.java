package Things;

public class Parameter {

    private final String name;
    private final ParameterType type;

    public Parameter(String name, ParameterType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ParameterType getType() {
        return type;
    }


}
