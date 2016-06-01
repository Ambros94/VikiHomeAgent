package NLP;

import Things.Domain;
import Things.Operation;
import Things.Parameter;
import edu.stanford.nlp.ling.CoreAnnotations;
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
 * Use StanfordNLP information to detect Domains, Operations and Parameters
 */
public class NLPGraph implements Graph {

    private static final StanfordCoreNLP nlp;
    private final Annotation annotation;

    static {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, depparse, parse, natlog, openie");
        nlp = new StanfordCoreNLP(props);
    }

    public NLPGraph(String sentence) {
        /**
         * Annotate the sentence
         */
        annotation = new Annotation(sentence);
        nlp.annotate(annotation);
    }

    @Override
    public boolean contains(Domain t) {
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap coreMap : sentences) {
            /**
             * Sentence level
             */
            SemanticGraph semanticGraph = coreMap.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            IndexedWord root = semanticGraph.getFirstRoot();
            final String posAnnotation = root.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            System.out.println(posAnnotation);
            for (IndexedWord indexedWord :             semanticGraph.getAllNodesByWordPattern("")) {
                System.out.println(indexedWord);
            }
        }
        return true;
    }

    @Override
    public boolean contains(Operation o, Domain t) {
        return false;//TODO
    }

    @Override
    public Object contains(Parameter p, Operation o, Domain t) {
        return null;//TODO
    }
}
