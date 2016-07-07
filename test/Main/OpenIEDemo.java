package Main;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

import java.util.Collection;
import java.util.Properties;

/**
 * A demo illustrating how to call the OpenIE system programmatically.
 */
public class OpenIEDemo {


    public static void main(String[] args) throws Exception {
        // Create the Stanford CoreNLP pipeline
        Properties props = PropertiesUtils.asProperties(
                "annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie"
                // , "depparse.model", "edu/stanford/nlp/models/parser/nndep/english_SD.gz"
                // "annotators", "tokenize,ssplit,pos,lemma,parse,natlog,openie"
                // , "parse.originalDependencies", "true"
        );
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String text;
        text = "Viki remember me to go to the hairdresser on tuesday";

        Annotation doc = new Annotation(text);
        pipeline.annotate(doc);

        // Loop over sentences in the document
        int sentNo = 0;
        for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
            System.out.println("Sentence #" + ++sentNo + ": ");

            // Get the OpenIE triples for the sentence
            Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);

            // Print the triples
            for (RelationTriple triple : triples) {
                System.out.println(triple.confidence + "\t-" +
                        triple.subjectLemmaGloss() + "\t-" +
                        triple.relationLemmaGloss() + "\t-" +
                        triple.objectLemmaGloss());
            }

            System.out.println();
        }
    }

}