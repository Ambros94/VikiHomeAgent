package Brain.Confidence;

public class ConfidenceCalculatorBuilder {
    ConfidenceCalculator getStatic() {
        return new StaticConfidenceCalculator();
    }

    ConfidenceCalculator getBayesian(){
        return new BayesianConfidenceCalculator();
    }
}
