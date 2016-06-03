package NLP;

import Things.Domain;
import Things.Operation;
import Things.Parameter;
import edu.mit.jwi.item.POS;
import edu.stanford.nlp.international.Language;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.EnglishGrammaticalRelations;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.util.CoreMap;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Use StanfordNLP information to detect Domains, Operations and Parameters
 * TODO I think that can have serious performance problem, should be profiled
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
    public int containsDomain(Domain domain) {
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
                    if (domain.equalsSynonyms(lemma, POS.NOUN))
                        return word.index();
                }
            }
        }
        return -1;
    }

    @Override
    public int containsOperation(Operation operation, Domain domain, int domainIndex) {
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap coreMap : sentences) {
            SemanticGraph semanticGraph = coreMap.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            /**
             * Get the world associated with the domain
             */
            IndexedWord domainWord = semanticGraph.getNodeByIndex(domainIndex);
            /**
             * Look in every verb+preposition linked if there is something equals to the operation
             */
            Set<StringIndexPair> possibleOperations = getLinkedVerbs(semanticGraph, domainWord);
            for (StringIndexPair possibleOperation : possibleOperations) {
                if (operation.equalsSynonyms(possibleOperation.getVerbPreposition(), POS.VERB))
                    return possibleOperation.getIndex();
            }
        }
        return -1;
    }

    /**
     * @param semanticGraph Graph used to look for verbs, linked to the domainWorld
     * @param domainWord    Indexed world that has been mapped to a domain
     * @return Collection of Verb+Preposition associated with verb index in the sentence represented by the given graph
     */
    private Set<StringIndexPair> getLinkedVerbs(SemanticGraph semanticGraph, IndexedWord domainWord) {
        Set<StringIndexPair> linkedVerbs = new HashSet<>();
        List<SemanticGraphEdge> domainIncomingEdges = semanticGraph.getIncomingEdgesSorted(domainWord);

        for (SemanticGraphEdge edge : domainIncomingEdges) {
            String verb = edge.getSource().lemma();
            // edge.getSource() -> Verbo
            /**
             * Look for prepositions
             */

            List<SemanticGraphEdge> outEdgesSorted = semanticGraph.getOutEdgesSorted(edge.getSource());
            System.out.println("outEdges from" + edge.getSource());
            for (SemanticGraphEdge outedge : outEdgesSorted) {
                System.out.println(outedge);
                if (outedge.getRelation().getShortName().equals("nmod")) {//TODO Faulty method, and maybe this is not the only relation possibile
                    System.out.println("Preposition found");
                    verb += " " + outedge.getTarget().lemma();
                }
            }
            linkedVerbs.add(new StringIndexPair(verb, edge.getSource().index()));
        }
        return linkedVerbs;
    }

    @Override
    public Object containsParameter(Parameter p, int operationIndex, int domainIndex) {
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

    private class StringIndexPair {

        private final String verbPreposition;
        private final int index;

        private StringIndexPair(String string, int index) {
            this.verbPreposition = string;
            this.index = index;
        }

        public String getVerbPreposition() {
            return verbPreposition;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public String toString() {
            return "StringIndexPair{" +
                    "verbPreposition='" + verbPreposition + '\'' +
                    ", index=" + index +
                    '}';
        }
    }
}
