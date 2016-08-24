package Brain.Confidence;

public interface ConfidenceCalculator {
    double computeConfidence(double domainConfidence, double operationConfidence, int rightParameters, int wrongParameters);
}
