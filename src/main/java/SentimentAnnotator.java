import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

/**
 * @author sih
 */
public class SentimentAnnotator {


    static final String DEFAULT_SENTIMENT_MODEL = "edu/stanford/nlp/models/sentiment/sentiment.ser.gz";
    static final String DEFAULT_PARSER_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";

    private String sentimentModel;
    private String parserModel;

    private StanfordCoreNLP pipeline;

    /**
     * Default the sentiment and parser models to those included
     */
    SentimentAnnotator() {
        this(DEFAULT_SENTIMENT_MODEL,DEFAULT_PARSER_MODEL);
    }

    /**
     * Allow callers to specify their own sentiment and parser models but use defaults if not specified
     * @param sentimentModel
     * @param parserModel
     */
    SentimentAnnotator(final String sentimentModel, final String parserModel) {
        this.sentimentModel = (null != sentimentModel) ? sentimentModel : DEFAULT_SENTIMENT_MODEL;
        this.parserModel = (null != sentimentModel) ? parserModel : DEFAULT_PARSER_MODEL;

        Properties pipelineProps = createPipelineProperties(sentimentModel, parserModel);
        pipeline = new StanfordCoreNLP(pipelineProps);
    }



    /**
     * @link https://github.com/stanfordnlp/CoreNLP/blob/master/src/edu/stanford/nlp/sentiment/SentimentPipeline.java#L246
     * @param annotation
     */
    Annotation annotate(Annotation annotation) {
        pipeline.annotate(annotation);
        return annotation;
    }



    private Properties createPipelineProperties(String sModel, String pModel) {
        Properties pipelineProps = new Properties();
        pipelineProps.setProperty("sentiment.model", sModel);
        pipelineProps.setProperty("parse.model", pModel);
        pipelineProps.setProperty("annotators", "parse, sentiment");
        pipelineProps.setProperty("enforceRequirements", "false");
        return pipelineProps;
    }



}
