package NLP;

import Things.Operation;
import Things.Parameter;
import Things.Domain;
import edu.mit.jwi.item.POS;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

/**
 * Dumb way to find domains, operations and parameters, using mass string contains
 */
public class StringGraph implements Graph {

    private final String sentence;

    public StringGraph(String sentence) {
        this.sentence = sentence;
    }

    @Override
    public boolean contains(Domain t) {
        for (String s : t.getSynonyms(POS.NOUN))
            if (sentence.contains(s))
                return true;
        for (String s : t.getWords())
            if (sentence.contains(s))
                return true;
        return false;
    }

    @Override
    public boolean contains(Operation o, Domain t) {
        for (String s : o.getWords()) {
            if (sentence.contains(s)) {
                return true;
            } else {
            }
        }
        for (String s : o.getSynonyms(POS.VERB)) {
            if (sentence.contains(s)) {
                return true;
            } else {
            }
        }
        return false;
    }

    @Override
    public Object contains(Parameter p, Operation o, Domain t) {
        return new Object();//TODO
    }
}
