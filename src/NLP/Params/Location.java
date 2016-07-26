package NLP.Params;

import Brain.JSONParsable;


public class Location implements JSONParsable {

    private final String location;

    public Location(String location) {
        this.location = location;
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
