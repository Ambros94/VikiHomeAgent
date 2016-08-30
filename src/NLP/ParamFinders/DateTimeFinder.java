package NLP.ParamFinders;

import NLP.Params.MyDate;
import NLP.Params.Value;
import Things.ParameterType;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.time.TimeExpression;
import edu.stanford.nlp.util.CoreMap;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Class able to find dates. Using stanfordNLP sutime module.
 * Is able to find formatted dates, expression like "today" or "The next tuesday"
 */
class DateTimeFinder implements ITypeFinder {

    private Logger logger = Logger.getLogger(DateTimeFinder.class);
    private static final Properties props = new Properties();
    private static final AnnotationPipeline pipeline = new AnnotationPipeline();

    static {
        pipeline.addAnnotator(new TokenizerAnnotator(false));
        pipeline.addAnnotator(new                WordsToSentencesAnnotator(false));
        pipeline.addAnnotator(new                POSTaggerAnnotator(false));
        pipeline.addAnnotator(new                TimeAnnotator("sutime", props));
    }

    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.DATETIME;
    }

    @Override
    public Value find(ParameterType parameterType, String sentence) {
/*
         * Build the pipeline
         */

        Annotation annotation = new Annotation(sentence);
        /*
         * Set today date
         */
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        annotation.set(CoreAnnotations.DocDateAnnotation.class, dateFormat.format(date));
        pipeline.annotate(annotation);
        /*
         * Get time annotations
         */
        List<CoreMap> timexAnnsAll = annotation.get(TimeAnnotations.TimexAnnotations.class);
        if (timexAnnsAll.size() > 1) {
            logger.info("Found more than one date time, returning the first one");
        }
        if (timexAnnsAll.size() == 0) {
            return null;
        }/*
        for (CoreMap cm : timexAnnsAll) {
            List<CoreLabel> tokens = cm.get(CoreAnnotations.TokensAnnotation.class);
            logger.debug(cm + " [from char offset " +
                    tokens.get(0).get(CoreAnnotations.CharacterOffsetBeginAnnotation.class) +
                    " to " + tokens.get(tokens.size() - 1).get(CoreAnnotations.CharacterOffsetEndAnnotation.class) + ']' +
                    " --> " + cm.get(TimeExpression.Annotation.class).getTemporal());
        }*/
        return new MyDate(timexAnnsAll.get(0).get(TimeExpression.Annotation.class).getTemporal().toISOString());
    }
}
