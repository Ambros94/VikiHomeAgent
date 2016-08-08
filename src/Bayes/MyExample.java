package Bayes;


import Bayes.bayes.BayesClassifier;

import java.util.Arrays;

public class MyExample {

    private static final int GOOD = 1;
    private static final int NEUTRAL = 0;
    private static final int WORST = -1;


    public static void main(String[] args) {
        final Classifier<Integer, String> bayes =
                new BayesClassifier<>();

        //Turn on the light
        bayes.learn("positive", Arrays.asList(GOOD, GOOD, NEUTRAL));
        //Make it red
        bayes.learn("positive", Arrays.asList(NEUTRAL, GOOD, GOOD));

        //It's dark
        bayes.learn("negative", Arrays.asList(WORST, WORST, WORST));
        //Something to turn on red
        bayes.learn("negative", Arrays.asList(NEUTRAL, GOOD, WORST));


        System.out.println( // What's the weather like in paris ?
                bayes.classify(Arrays.asList(GOOD, NEUTRAL, GOOD)));
        System.out.println( // Lamp something
                bayes.classify(Arrays.asList(NEUTRAL, WORST, NEUTRAL)));


    }
}
