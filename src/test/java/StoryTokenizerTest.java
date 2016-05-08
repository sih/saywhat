import edu.stanford.nlp.pipeline.Annotation;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author sih
 */
public class StoryTokenizerTest {

    Logger logger = LoggerFactory.getLogger(StoryTokenizerTest.class);

    private StoryTokenizer tokenizer;

    Path story1Path;

    @Before
    public void setUp() throws Exception {
        tokenizer = new StoryTokenizer();
        story1Path = Paths.get("./src/main/resources/story-1.txt");
    }

    @Test
    public void tokenize() throws Exception {
        assertNotNull(story1Path);
        Stream<Annotation> annotations = tokenizer.tokenize(story1Path);
        long count = annotations.count();
        logger.info("Count is "+count);
        assertTrue(count > 0);
    }
}