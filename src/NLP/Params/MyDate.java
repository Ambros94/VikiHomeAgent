package NLP.Params;

import Brain.JSONParsable;

public class MyDate implements JSONParsable {

    private final String date;

    public MyDate(String date) {
        this.date = date;
    }

    @Override
    public String toJson() {
        return "'" + date + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyDate myDate = (MyDate) o;

        return date != null ? date.equals(myDate.date) : myDate.date == null;

    }

    @Override
    public int hashCode() {
        return date != null ? date.hashCode() : 0;
    }
}