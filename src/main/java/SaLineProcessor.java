import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

/**
 * @author sih
 */
public class SALineProcessor {


    static final String DEFAULT_SENTIMENT_MODEL = "sentiment.ser.gz ";
    static final String DEFAULT_PARSER_MODEL = "englishPCFG.ser.gz ";

    /**
     * @link https://github.com/stanfordnlp/CoreNLP/blob/master/src/edu/stanford/nlp/sentiment/SentimentPipeline.java#L246
     * @param line
     */
    void process(final String line) {
        process(line,null,null);
    }


    /**
     * @link https://github.com/stanfordnlp/CoreNLP/blob/master/src/edu/stanford/nlp/sentiment/SentimentPipeline.java#L246
     * @param line
     */
    void process(final String line, final String sentimentModel, final String parserModel) {

        String sModel = (null != sentimentModel) ? sentimentModel : DEFAULT_SENTIMENT_MODEL;
        String pModel = (null != sentimentModel) ? parserModel : DEFAULT_PARSER_MODEL;

        Properties pipelineProps = createPipelineProperties(sModel, pModel);
        Properties tokenizerProps = createTokenizerProperties();

    }



    private Properties createPipelineProperties(String sModel, String pModel) {
        Properties pipelineProps = new Properties();
        pipelineProps.setProperty("sentiment.model", sModel);
        pipelineProps.setProperty("parse.model", pModel);
        pipelineProps.setProperty("annotators", "parse, sentiment");
        pipelineProps.setProperty("enforceRequirements", "false");
        return pipelineProps;
    }

    private Properties createTokenizerProperties() {
        Properties tokenizerProps = new Properties();
        tokenizerProps.setProperty("annotators", "tokenize, ssplit");
        tokenizerProps.setProperty(StanfordCoreNLP.NEWLINE_SPLITTER_PROPERTY, "true");
        return tokenizerProps;
    }

}
