package NLP.DomainOperationsFinders;

import Brain.Universe;
import Comunication.UniverseLoader;
import org.junit.Test;

public class Word2vecDOFinderTest {

    DomainOperationFinder finder;

    @Test
    public void build() throws Exception {
        String json = new UniverseLoader().loadFromFile();

        Universe universe = Universe.fromJson(json);
        universe.setDomainOperationFinder(Word2vecDOFinder.build(universe.getDomains()));
        finder = Word2vecDOFinder.build(universe.getDomains());
    }

    @Test
    public void find() throws Exception {
        finder.find("turn on the light");
    }

}