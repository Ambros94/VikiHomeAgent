package NLP;

import Utility.Config;
import com.google.common.base.CaseFormat;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simpler interface to access WordNet data.
 * Database access from file and not loaded in RAM, because is usually used only at system boot
 */
public class WordNet {
    /**
     * WordNet database loaded from file
     */
    private static IDictionary dict;

    /*
     * Load dictionary from file
     */
    static {
        String path = Config.getConfig().getDictionaryPath();
        URL url;
        try {
            url = new URL("file", null, path);
            dict = new Dictionary(url);
            dict.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param word Represent the word which synonyms you are looking for
     * @param pos  Type of synonyms to search, e.g. Adverb, Nouns, Verbs
     * @return Set of synonyms, empty set in case of none is found
     */
    public static Set<String> getSynonyms(String word, POS pos) {
        if (pos == null)
            throw new NullPointerException("Missing POS");
        /*
         * Eventually convert from lowerCamel to lowerUnderscore
         * Replace every ' '(space) with '_' because this is the notation used in WordNet ( "turn on" -> "turn_on" )
         */
        word = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, word);
        word = word.trim().replace(' ', '_');
        /*
         * Some checks
         */
        IIndexWord idxWord = dict.getIndexWord(word, pos);
        Set<String> synonyms = new HashSet<>();
        if (idxWord == null)
            return synonyms;
        /*
         * For each word linked find every synonym
         */
        for (IWordID wordID : idxWord.getWordIDs()) {
            IWord iword = dict.getWord(wordID);
            if (iword == null)
                continue;
            ISynset synset = iword.getSynset();
            /*
             * Replace every '_' with ' '(space), back from WorldNet to real world
             */
            synonyms.addAll(synset.getWords().stream().map(IWord::getLemma).map(lemma -> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, lemma)).collect(Collectors.toList()));
        }
        return synonyms;
    }

}
