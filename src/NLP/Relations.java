package NLP;

import java.util.LinkedList;
import java.util.List;

/**
 * Utility class that stores type of relation between two words
 * e.g. relation between verb and his preposition
 */
public class Relations {
    private static List<String> verb_preposition = new LinkedList<>();

    static {
        /**
         * Relations between Verb and Prepositions
         */
        verb_preposition.add("nmod");
        verb_preposition.add("advmod");
        verb_preposition.add("compound:prt");

    }

    public static boolean isVerbPrepositionRelation(String relation) {
        for (String r : verb_preposition) {
            if (r.equalsIgnoreCase(relation))
                return true;
        }
        return false;
    }
}
