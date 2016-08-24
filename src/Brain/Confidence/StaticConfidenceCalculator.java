package Brain.Confidence;


class StaticConfidenceCalculator implements ConfidenceCalculator {
    @Override
    public double computeConfidence(double domainConfidence, double operationConfidence, int rightParameters, int wrongParameters) {
        return 0;
    }
}
