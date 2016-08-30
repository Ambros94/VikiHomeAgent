package Brain.Confidence;


import Bayes.BayesClassifier;
import Bayes.Classification;
import Bayes.Classifier;
import Brain.UniverseController;
import org.apache.log4j.Logger;

import java.util.Arrays;

class BayesianConfidenceCalculator implements ConfidenceCalculator {

    private final Classifier<Discrete, Category> bayes;
    private static final ConfidenceCalculator staticCalculator = ConfidenceCalculatorBuilder.getStatic();
    private final Logger logger = Logger.getLogger(UniverseController.class);

    BayesianConfidenceCalculator() {
        bayes = new BayesClassifier<>();
    }

    @Override
    public double computeConfidence(double executionProbability, int rightParameters, int wrongParameters) {
        // Discretize parameters that will be bayesian features
        Discrete probabilityDiscrete = Discrete.discrete(executionProbability);
        Discrete rightDiscrete = Discrete.discrete(rightParameters);
        Discrete wrongDiscrete = Discrete.discrete(wrongParameters);

        // Classify in right or positive
        Classification<Discrete, Category> c = bayes.classify(Arrays.asList(probabilityDiscrete, rightDiscrete, wrongDiscrete));
        logger.info(String.format("%s\t%s\t%s\tCategory:\t%s\tProbability:\t%s", probabilityDiscrete, rightDiscrete, wrongDiscrete,c.getCategory(),c.getProbability()));
        if (c.getCategory().equals(Category.RIGHT)) {
            return staticCalculator.computeConfidence(executionProbability, rightParameters, wrongParameters);

        } else
            return 0.0;
    }

    void train(Category category, double executionProbability, int rightParameters, int wrongParameters) {
        bayes.learn(category, Arrays.asList(Discrete.discrete(executionProbability), Discrete.discrete(rightParameters), Discrete.discrete(wrongParameters)));
    }


}
