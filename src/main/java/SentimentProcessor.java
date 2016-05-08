import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author sih
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
}
