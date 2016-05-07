import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author sih
 */
class StoryTokenizer {


    /**
     * @param path The location of the file with the story
     * @throws Exception When the file doesn't exist or can't be read
     */
    Stream<String> readStory(final Path path) throws Exception {
        return Files.readAllLines(path).stream();
    }

}
