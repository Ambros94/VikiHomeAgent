package DebugMain;


import NLP.Synonyms;

import java.io.IOException;
import java.util.Collections;

public class WorldNetDemo {
    public static void main(String[] args) throws IOException {
        Synonyms s = new Synonyms("light1", Collections.singleton("light"));
        System.out.println(s);
    }
}
