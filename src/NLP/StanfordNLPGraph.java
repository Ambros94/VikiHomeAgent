package NLP;

import Things.Domain;
import Things.Operation;
import Things.Parameter;
import edu.mit.jwi.item.POS;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Use StanfordNLP information to detect Domains, Operations and Parameters
 */
public class StanfordNLPGraph implements Graph {

    private static final StanfordCoreNLP nlp;
    private final Annotation annotation;

    static {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, depparse, parse, natlog, openie");
        nlp = new StanfordCoreNLP(props);
    }

    public StanfordNLPGraph(String sentence) {
        /**
         * Annotate the sentence
         */
        annotation = new Annotation(sentence);
        nlp.annotate(annotation);
    }

    @Override
    public int contains(Domain t) {
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap coreMap : sentences) {
            /**
             * Sentence level
             */
            SemanticGraph semanticGraph = coreMap.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            Set<IndexedWord> words = getWords(semanticGraph);
            for (IndexedWord word : words) {
                String POSType = word.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String lemma = word.lemma();
                /*
                If this is a noun, of every kind get if it similar to any given lemma
                NN Noun, singular or mass
                NNS Noun, plural
                NNP Proper noun, singular
                NNPS Proper noun, plural
                 */
                if (POSType.equals("NN") || POSType.equals("NNS") || POSType.equals("NNP") || POSType.equals("NNPS")) {
                    if (t.equalsSynonyms(lemma, POS.NOUN))
                        return word.index();
                }
            }
        }
        return -1;
    }

    @Override
    public int contains(Operation o, int domainIndex) {
        return 1;//TODO
    }

    @Override
    public Object contains(Parameter p, int operationIndex, int domainIndex) {
        return null;//TODO
    }

    private Set<IndexedWord> getWords(SemanticGraph semanticGraph) {
        Set<IndexedWord> words = new HashSet<>();
        for (SemanticGraphEdge graphEdge : semanticGraph.edgeIterable()) {
            words.add(graphEdge.getSource());
            words.add(graphEdge.getTarget());
        }
        return words;
    }
}
