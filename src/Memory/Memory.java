package Memory;

import Brain.Command;

import java.util.HashMap;
import java.util.Map;

public class Memory {

    private Map<String, Command> memory;


    public Memory() {
        this.memory = new HashMap<>();
    }

    public boolean remind(String sentence, Command rightThing) {
        return memory.put(sentence, rightThing) == null;
    }

    public Command isInMemory(String sentence) {
        System.err.println(memory);
        return memory.get(sentence);
    }
}
