package NLP.ParamFinders;

import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeExpression;
import edu.stanford.nlp.util.CoreMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class NumberFinder implements ITypeFinder {

    private Logger logger = LoggerFactory.getLogger(NumberFinder.class);

    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.NUMBER;
    }

    @Override
    public ParamValuePair find(Parameter parameter, String sentence) {
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(sentence);
        List<String> numbers = new ArrayList<>();
        while (m.find()) {
            numbers.add(m.group());
        }
        if (numbers.size() > 1) {
            logger.info("Found more than one number, returning the first one");
        }
        if (numbers.size() == 0) {
            return null;
        }
        return new ParamValuePair(parameter, numbers.get(0));
    }
}
