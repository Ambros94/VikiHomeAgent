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
     * Adjective synonyms calculated using WordNet, based on id and words
     */
    private final Set<String> adjectiveSynonyms;
    /**
     * Adjective synonyms calculated using WordNet, based on id and words
     */
    private final Set<String> adverbSynonyms;
    /**
     * Adjective synonyms calculated using WordNet, based on id and words
     */
    private final Set<String> nounSynonyms;
    /**
     * Adjective synonyms calculated using WordNet, based on id and words
     */
    private final Set<String> verbSynonyms;


    public Synonyms(String id, Set<String> words) {
        this.words = words;
        this.id = id;
        adjectiveSynonyms = new HashSet<>();
        adverbSynonyms = new HashSet<>();
        nounSynonyms = new HashSet<>();
        verbSynonyms = new HashSet<>();
        /**
         * Compute every kind of synonyms
         */
        computeSynonyms(adjectiveSynonyms, POS.ADJECTIVE);
        computeSynonyms(adverbSynonyms, POS.ADVERB);
        computeSynonyms(nounSynonyms, POS.NOUN);
        computeSynonyms(verbSynonyms, POS.VERB);
    }

    private void computeSynonyms(Set<String> synonyms, POS pos) {
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

    public Set<String> getSynonyms(POS pos) {
        switch (pos) {
            case ADJECTIVE:
                return adjectiveSynonyms;
            case ADVERB:
                return adverbSynonyms;
            case VERB:
                return verbSynonyms;
            case NOUN:
                return nounSynonyms;
            default:
                throw new RuntimeException("Invalid POS type" + pos);
        }
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

    @Override
    public String toString() {
        return "Synonyms{" +
                "id='" + id + '\'' +
                ", words=" + words +
                '}';
    }
}
