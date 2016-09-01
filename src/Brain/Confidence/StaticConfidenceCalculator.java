package Brain.Confidence;


class StaticConfidenceCalculator implements ConfidenceCalculator {

    @Override
    public double computeConfidence(double executionProbability, int rightParameters, int wrongParameters) {
        //return executionProbability + 0.3 * rightParameters - 0.2 * wrongParameters;
        return executionProbability + 0.2 * rightParameters - 0.4 * wrongParameters;
    }


}
