import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

public class StanfordCoreNlpDemo {

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, depparse, parse, natlog, openie");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = new Annotation("Viki turn on the lamp when the sun rises");


        pipeline.annotate(annotation);
        //pipeline.prettyPrint(annotation, out);

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            /**
             * Sentence level
             */
            SemanticGraph s = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            System.out.println(s);
            /**
             * Every word
             */
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                System.out.println("Word:" + word);
                System.out.println("Pos:" + pos);
                System.out.println("Ne:" + ne);
                System.out.println("*********");
            }


            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);

            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
        }

    }

}
