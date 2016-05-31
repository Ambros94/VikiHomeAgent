package Things;


import NLP.WordNet;
import edu.mit.jwi.item.POS;

import java.util.*;

/**
 * Makes id compulsory, let object to have a list of words associated and automatically populates synonyms list on create.
 */
public class Synonyms {
    /**
     * Unique identifier for every object, e.g. lamp or turn_on
     */
    private final String id;
    /**
     * Friendly names or alternative names e.g. Sphere, light
     */
    private final Set<String> words;
    /**
     * Friendly names and id synonyms, calculated using WordNet
     */
    private final Set<String> synonyms;


    public Synonyms( String id, POS pos,Set<String> words) {
        this.words = words;
        this.id = id;
        synonyms = new HashSet<>();
        /**
         * Compute synonyms
         */
        synonyms.addAll(WordNet.getSynonyms(id, pos));
        for (String word : words) {
            synonyms.addAll(WordNet.getSynonyms(word, pos));
        }
    }

    public String getId() {
        return id;
    }

    public Set<String> getWords() {
        return words;
    }

    public Set<String> getSynonyms() {
        return synonyms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Synonyms synonyms = (Synonyms) o;

        return id != null ? id.equals(synonyms.id) : synonyms.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
