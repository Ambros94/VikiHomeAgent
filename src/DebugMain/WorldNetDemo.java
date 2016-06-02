package DebugMain;


import NLP.WordNet;
import edu.mit.jwi.item.POS;

import java.io.IOException;

public class WorldNetDemo {
    public static void main(String[] args) throws IOException {
        System.out.println(WordNet.getSynonyms("light", POS.NOUN));
    }
}
