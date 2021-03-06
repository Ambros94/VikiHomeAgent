package NLP.Params;

import Things.ParameterType;

public class Color extends Value {

    private final int r;
    private final int g;
    private final int b;

    public Color(String color) {
        r = Integer.parseInt(color.substring(1, 3), 16);
        g = Integer.parseInt(color.substring(3, 5), 16);
        b = Integer.parseInt(color.substring(5, 7), 16);
    }


    @Override
    public String toJson() {
        return String.format("[%d,%d,%d]", r, g, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        return r == color.r && g == color.g && b == color.b;

    }

    @Override
    public int hashCode() {
        int result = r;
        result = 31 * result + g;
        result = 31 * result + b;
        return result;
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }

    @Override
    public ParameterType getType() {
        return ParameterType.COLOR;
    }
}
