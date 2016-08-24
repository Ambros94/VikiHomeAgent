package Brain.Confidence;


class StaticConfidenceCalculator implements ConfidenceCalculator {

    @Override
    public double computeConfidence(double domainConfidence, double operationConfidence, int rightParameters, int wrongParameters) {
        return 0.5d * domainConfidence + 0.5d * operationConfidence + 0.5 * rightParameters - 0.2 * wrongParameters;
    }
}
