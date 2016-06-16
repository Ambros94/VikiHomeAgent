package NLP;


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
    private Set<String> adjectiveSynonyms;
    /**
     * Adjective synonyms calculated using WordNet, based on id and words
     */
    private Set<String> adverbSynonyms;
    /**
     * Adjective synonyms calculated using WordNet, based on id and words
     */
    private Set<String> nounSynonyms;
    /**
     * Adjective synonyms calculated using WordNet, based on id and words
     */
    private Set<String> verbSynonyms;

    /**
     * @param id    unique identifier for the given object
     * @param words alternative names for the given object
     */
    public Synonyms(String id, Collection<String> words) {
        this.words = new HashSet<>(words);
        this.id = id;
        updateSynonyms();
    }

    /**
     * Force synonyms update if the object has been constructed with reflection
     */
    public void updateSynonyms() {
        /**
         * Compute every kind of synonyms
         */
        adjectiveSynonyms = new HashSet<>();
        adverbSynonyms = new HashSet<>();
        nounSynonyms = new HashSet<>();
        verbSynonyms = new HashSet<>();
        computeSynonyms(adjectiveSynonyms, POS.ADJECTIVE);
        computeSynonyms(adverbSynonyms, POS.ADVERB);
        computeSynonyms(nounSynonyms, POS.NOUN);
        computeSynonyms(verbSynonyms, POS.VERB);
    }

    /**
     * @param synonyms Set where synonyms are added
     * @param pos      POS type of synonyms to look for
     */
    private void computeSynonyms(Set<String> synonyms, POS pos) {
        synonyms.addAll(WordNet.getSynonyms(id, pos));
        for (String word : words) {
            synonyms.addAll(WordNet.getSynonyms(word, pos));//TODO Use lemmatizer to avoid problems ?
        }
    }

    /**
     * @param pos POS type of synonyms to be returned
     * @return Set of synonyms of the given POS Type (shallow copy)
     */
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

    /**
     * @param word Word that could be a synonym for the object
     * @return true if the world is synonym as the object, with at least one POS Category (ADJECTIVE, ADVERB, VERB, NOUN). Otherwise false
     */
    public boolean equalsSynonyms(String word) {
        //Check every type of synonym
        return equalsSynonyms(word, POS.ADJECTIVE) || equalsSynonyms(word, POS.ADVERB) || equalsSynonyms(word, POS.VERB) || equalsSynonyms(word, POS.NOUN);
    }

    /**
     * @param word Word that could be a synonym for the object
     * @param pos  POS Category where synonyms can be found
     * @return true if the world is synonym as the object, with the given POS Category  Otherwise false
     */
    public boolean equalsSynonyms(String word, POS pos) {
        //Compare with the id
        if (word.equals(id))
            return true;
        //Compare with friendly Names
        for (String s : words) {
            if (s.equals(word))
                return true;
        }
        //Compare with the right type of synonyms
        switch (pos) {
            case ADJECTIVE:
                for (String s : adjectiveSynonyms) {
                    if (s.equals(word))
                        return true;
                }
                return false;
            case ADVERB:
                for (String s : adverbSynonyms) {
                    if (s.equals(word))
                        return true;
                }
                return false;
            case VERB:
                for (String s : verbSynonyms) {
                    if (s.equals(word))
                        return true;
                }
                return false;
            case NOUN:
                for (String s : nounSynonyms) {
                    if (s.equals(word))
                        return true;
                }
                return false;
            default:
                throw new RuntimeException("Invalid POS type" + pos);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
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
                ",\n adjectiveSynonyms=" + adjectiveSynonyms +
                ",\n adverbSynonyms=" + adverbSynonyms +
                ",\n nounSynonyms=" + nounSynonyms +
                ",\n verbSynonyms=" + verbSynonyms +
                '}';
    }

    public String getId() {
        return id;
    }

    public Set<String> getWords() {
        return words;
    }
}
