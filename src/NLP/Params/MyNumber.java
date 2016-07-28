package NLP.Params;

import Things.ParameterType;


public class MyNumber extends Value {

    private final int number;

    public MyNumber(int number) {
        this.number = number;
    }

    @Override
    public ParameterType getType() {
        return ParameterType.NUMBER;
    }

    @Override
    public String toJson() {
        return String.valueOf(number);
    }

    @Override
    public String toString() {
        return "MyNumber{" +
                "number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyNumber myNumber = (MyNumber) o;

        return number == myNumber.number;

    }

    @Override
    public int hashCode() {
        return number;
    }
}
