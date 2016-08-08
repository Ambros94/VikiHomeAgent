package Bayes;

/**
 * Simple interface defining the method to calculate the feature probability.
 *
 * @author Philipp Nolte
 *
 * @param <T> The feature class.
 * @param <K> The category class.
 */
interface IFeatureProbability<T, K> {

    float featureProbability(T feature, K category);

}
