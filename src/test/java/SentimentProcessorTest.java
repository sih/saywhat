import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author sih
 */
public class SentimentProcessorTest {

    private  SentimentProcessor processor;
    Path story1Path;


    @Before
    public void setUp() throws Exception {
        processor = new SentimentProcessor();
        story1Path = Paths.get("./src/main/resources/story-1.txt");
    }

    @Test
    public void process() throws Exception {

        Map<String,List<String>> results = processor.process(story1Path);
        assertNotNull(results);
        assertFalse(results.get(SentimentProcessor.GOOD).isEmpty());
        assertFalse(results.get(SentimentProcessor.BAD).isEmpty());
        assertFalse(results.get(SentimentProcessor.INDIFFERENT).isEmpty());

    }
}