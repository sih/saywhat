import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

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
        processor.process(story1Path);
    }
}