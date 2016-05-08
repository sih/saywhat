import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @author sih
 * TODO https://blog.openshift.com/day-20-stanford-corenlp-performing-sentiment-analysis-of-twitter-using-java/
 */
public class SentimentProcessor {

    private StoryTokenizer tokenizer;
    private SentimentAnnotator annotator;

    static Logger LOGGER = LoggerFactory.getLogger(SentimentProcessor.class);

    /**
     * Creates the tokenizer and annotator
     */
    SentimentProcessor() {
        tokenizer = new StoryTokenizer();
        annotator = new SentimentAnnotator();
    }
    
    void process(final Path storyFile) {
        try {
            tokenizer
                    .tokenize(storyFile)
                    .map(a -> annotator.annotate(a))
                    .forEach(a -> LOGGER.info(a.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void newProcess(String line) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        int mainSentiment = 0;
        if (line != null && line.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(line);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }

            LOGGER.info(mainSentiment+" "+line);
        }


    }


}
