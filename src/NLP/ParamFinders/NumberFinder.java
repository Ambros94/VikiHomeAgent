package NLP.ParamFinders;

import NLP.Params.MyNumber;
import NLP.Params.Value;
import Things.ParameterType;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parameter finder used to retrieve Numbers. It finds number that are yet composed with digits.
 * Can find + and - and number separated by '.'
 * CANNOT find number written in letters
 * e.g. one
 */
class NumberFinder implements ITypeFinder {

    private Logger logger = Logger.getLogger(NumberFinder.class);

    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.NUMBER;
    }

    @Override
    public Value find(ParameterType parameterType, String sentence) {
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
        return new MyNumber(Integer.parseInt(numbers.get(0)));
    }
}
