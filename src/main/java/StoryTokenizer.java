import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Take an input file and use the Stanford tokenizer to turn it in to sentences.
 * @author sih
 */
class StoryTokenizer {

    /**
     * Read an input file containing a story and turn in to a Stream of Annotations for further processing
     * @param path
     * @return
     */
    Stream<Annotation> tokenize(final Path path) throws IOException {

        Properties tokenizerProps = createTokenizerProperties();
        StanfordCoreNLP tokenizer = new StanfordCoreNLP(tokenizerProps);

        String fileText = new String(Files.readAllBytes(path));
        Annotation annotation = new Annotation(fileText);

        tokenizer.annotate(annotation);

        List<Annotation> annotations = new ArrayList<>();
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Annotation nextAnnotation = new Annotation(sentence.get(CoreAnnotations.TextAnnotation.class));
            nextAnnotation.set(CoreAnnotations.SentencesAnnotation.class, Collections.singletonList(sentence));
            annotations.add(nextAnnotation);
        }

        return annotations.stream();
    }

    private Properties createTokenizerProperties() {
        Properties tokenizerProps = new Properties();
        tokenizerProps.setProperty("annotators", "tokenize, ssplit");
        tokenizerProps.setProperty(StanfordCoreNLP.NEWLINE_SPLITTER_PROPERTY, "true");
        return tokenizerProps;
    }

}
