import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * Created by Vitaliy Vashchenko on 1. 4. 2016.
 */
public class NLPAnalyser {
    static StanfordCoreNLP stanfordCoreNLP;

    public static void init(){
        stanfordCoreNLP = new StanfordCoreNLP("props.properties");
    }

    public static int getSentiment(String tweet){
        int mainSentiment = 0;

        if(tweet!=null && tweet.length()>0){
            int longest = 0;
            Annotation annotation = stanfordCoreNLP.process(tweet);
            for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)){
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String text = sentence.toString();
                if(text.length()>longest){
                    mainSentiment = sentiment;
                    longest = text.length();
                }
            }
        }
        return  mainSentiment;
    }
}
