package Brain.Confidence;


class BayesianConfidenceCalculator implements ConfidenceCalculator {

    @Override
    public double computeConfidence(double domainConfidence, double operationConfidence, int rightParameters, int wrongParameters) {
        Discrete domainDiscrete=Discrete.discrete(domainConfidence);
        Discrete operationDiscrete=Discrete.discrete(operationConfidence);
        return 0.0;
    }
}
