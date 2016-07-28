package NLP.Params;

import Things.ParameterType;


public class Location extends Value {

    private final String location;

    public Location(String location) {
        this.location = location;
    }

    @Override
    public ParameterType getType() {
        return ParameterType.LOCATION;
    }

    @Override
    public String toJson() {
        return "'" + location + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location1 = (Location) o;

        return location != null ? location.equals(location1.location) : location1.location == null;

    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }
}
