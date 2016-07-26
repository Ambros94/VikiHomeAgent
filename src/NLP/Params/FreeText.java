package NLP.Params;


import Brain.JSONParsable;

public class FreeText implements JSONParsable {


    private final String text;

    public FreeText(String text) {
        this.text = text;
    }

    @Override
    public String toJson() {
        return "'" + text + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreeText freeText = (FreeText) o;

        return text != null ? text.equals(freeText.text) : freeText.text == null;

    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }
}
