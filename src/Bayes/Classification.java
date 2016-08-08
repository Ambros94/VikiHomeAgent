package Bayes;

import java.util.Collection;

/**
 * A basic wrapper reflecting a classification.  It will store both featureSet
 * and resulting classification.
 *
 * @author Philipp Nolte
 *
 * @param <T> The feature class.
 * @param <K> The category class.
 */
public class Classification<T, K> {

    /**
     * The classified featureSet.
     */
    private Collection<T> featureSet;

    /**
     * The category as which the featureSet was classified.
     */
    private K category;

    /**
     * The probability that the featureSet belongs to the given category.
     */
    private float probability;

    /**
     * Constructs a new Classification with the parameters given and a default
     * probability of 1.
     *
     * @param featureSet The featureSet.
     * @param category The category.
     */
    public Classification(Collection<T> featureSet, K category) {
        this(featureSet, category, 1.0f);
    }

    /**
     * Constructs a new Classification with the parameters given.
     *
     * @param featureSet The featureSet.
     * @param category The category.
     * @param probability The probability.
     */
    public Classification(Collection<T> featureSet, K category,
                          float probability) {
        this.featureSet = featureSet;
        this.category = category;
        this.probability = probability;
    }

    /**
     * Retrieves the featureSet classified.
     *
     * @return The featureSet.
     */
    Collection<T> getFeatureSet() {
        return featureSet;
    }

    /**
     * Retrieves the classification's probability.
     */
    public float getProbability() {
        return this.probability;
    }

    /**
     * Retrieves the category the featureSet was classified as.
     *
     * @return The category.
     */
    public K getCategory() {
        return category;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Classification [category=" + this.category
                + ", probability=" + this.probability
                + ", featureSet=" + this.featureSet
                + "]";
    }

}
