package Brain;


import Things.Parameter;

public class ParamValuePair {


    private final Object value;
    private final Parameter p;

    public ParamValuePair(Parameter p, Object value) {
        this.p = p;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public Parameter getP() {
        return p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParamValuePair that = (ParamValuePair) o;

        return p.equals(that.p);

    }

    @Override
    public int hashCode() {
        return p.hashCode();
    }
}
