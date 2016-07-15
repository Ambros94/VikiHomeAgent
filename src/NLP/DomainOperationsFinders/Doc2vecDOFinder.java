package NLP.DomainOperationsFinders;

import Brain.DomainOperationPair;
import LearningAlgorithm.Doc2Vec.LabelSeeker;
import LearningAlgorithm.Doc2Vec.MeansBuilder;
import LearningAlgorithm.Doc2Vec.UniverseDoc2VecBuilder;
import LearningAlgorithm.Doc2Vec.UniverseModelWriter;
import Things.Domain;
import org.canova.api.util.ClassPathResource;
import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * DomainFinder that uses doc2vec to determine similarities between the sentence and Domain,Operation
 * Every operation has at the startReceiver at least one text invocation associated, during the system usage the sentence
 * number will increase. Those sentences are used to train a CNN, with supervised learning.
 * After a few sentence associated with a specific DOPair the model should be able to determine which DOPair a
 * is the most probable associated with the sentence.
 */
public class Doc2vecDOFinder implements DomainOperationFinder {

    private Set<Domain> domains;
    private ParagraphVectors paragraphVectors;
    private FileLabelAwareIterator iterator;

    private Doc2vecDOFinder(Set<Domain> domains, ParagraphVectors paragraphVectors, FileLabelAwareIterator iterator) {
        this.iterator = iterator;
        this.paragraphVectors = paragraphVectors;
        this.domains = domains;
    }

    /**
     * @param universe Universe containing sentences that are added to the model before training
     * @return DomainOperationFinder trained using filed in resources/doc2vecModel
     * @throws FileNotFoundException thrown when cannot write or read model from file
     */
    public static DomainOperationFinder build(Set<Domain> universe) throws FileNotFoundException {
        /**
         * Write universe sentences on file
         * That will constitute the model
         */
        UniverseModelWriter modelWriter = new UniverseModelWriter();
        modelWriter.write(universe);
        /**
         * Build the models based on what is on file (just written+history)
         */
        ClassPathResource resource = new ClassPathResource("doc2vecModel");
        FileLabelAwareIterator iterator = new FileLabelAwareIterator.Builder()
                .addSourceFolder(resource.getFile())
                .build();
        ParagraphVectors paragraphVectors = new UniverseDoc2VecBuilder().build(iterator);
        return new Doc2vecDOFinder(universe, paragraphVectors, iterator);
    }

    @Override
    public List<DomainOperationPair> find(String text) {

        TokenizerFactory tokenizerFactory;
        tokenizerFactory = new DefaultTokenizerFactory();
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());
         /*
          Now we'll iterate over unlabeled data, and check which label it could be assigned to
          Please note: for many domains it's normal to have 1 document fall into few labels at once,
          with different "weight" for each.
         */
        MeansBuilder meansBuilder = new MeansBuilder(
                (InMemoryLookupTable<VocabWord>) paragraphVectors.getLookupTable(),
                tokenizerFactory);
        LabelSeeker seeker = new LabelSeeker(iterator.getLabelsSource().getLabels(),
                (InMemoryLookupTable<VocabWord>) paragraphVectors.getLookupTable());
        /**
         * Build a fake LabelledDocument to compare with Documents in the model
         */
        LabelledDocument document = new LabelledDocument();
        document.setContent(text);
        document.setLabel("Unlabeled");

        INDArray documentAsCentroid = meansBuilder.documentAsVector(document);
        List<Pair<String, Double>> scores = seeker.getScores(documentAsCentroid);
         /*
          please note, document.getLabel() is used just to show which document we're looking at now,
          as a substitute for printing out the whole document name.
          So, labels on these two documents are used like titles,
          just to visualize our classification done properly
         */
        List<DomainOperationPair> domainOperationPairList = new ArrayList<>();
        for (Pair<String, Double> pair : scores) {
            String[] pieces = pair.getFirst().split(Pattern.quote("."));
            String domainId = pieces[0];
            String operationId = pieces[1];
            domains.stream()
                    .filter(d -> d.getId().equals(domainId))
                    .forEach(d -> domainOperationPairList.addAll(d.getOperations()
                            .stream().filter(operation -> operation.getId().equals(operationId))
                            .map(operation -> new DomainOperationPair(d, operation, pair.getSecond()))
                            .collect(Collectors.toList())));
        }
        return domainOperationPairList;


    }
}
