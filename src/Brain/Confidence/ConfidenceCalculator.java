package Brain.Confidence;

public interface ConfidenceCalculator {
    double computeConfidence(double executionProbability, int rightParameters, int wrongParameters);
}
