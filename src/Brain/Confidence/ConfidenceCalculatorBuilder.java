package Brain.Confidence;

public class ConfidenceCalculatorBuilder {

    public static ConfidenceCalculator getStatic() {
        return new StaticConfidenceCalculator();
    }

    public static ConfidenceCalculator getBayesian() {
        return new BayesianConfidenceCalculator();
    }
}
