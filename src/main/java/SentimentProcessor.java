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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @author sih
 * TODO https://blog.openshift.com/day-20-stanford-corenlp-performing-sentiment-analysis-of-twitter-using-java/
 */
public class SentimentProcessor {


    static final String GOOD = "Good";
    static final String BAD = "Bad";
    static final String INDIFFERENT = "Indifferent";

    static Logger LOGGER = LoggerFactory.getLogger(SentimentProcessor.class);


    Map<String,List<String>> process(Path path) {

        Map<String,List<String>> results = new HashMap<>();

        List<String> goodList = new ArrayList<>();
        List<String> badist = new ArrayList<>();
        List<String> indifferentList = new ArrayList<>();

        results.put(GOOD,goodList);
        results.put(BAD,badist);
        results.put(INDIFFERENT,indifferentList);

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        List<String> lines = null;

        try {
            lines = Files.readAllLines(path);
        }
        catch (IOException io) {
            LOGGER.error("Bad path "+path.toString());
        }

        for (String line: lines) {
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

                    switch (mainSentiment) {
                        case 1:
                            badist.add(line);
                            break;
                        case 2:
                            badist.add(line);
                            break;
                        case 3:
                            indifferentList.add(line);
                            break;
                        case 4:
                            goodList.add(line);
                            break;
                        case 5:
                            goodList.add(line);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        return results;
    }


}
