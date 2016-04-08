import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Arrays;

/**
 * Created by Vitaliy Vashchenko on 1. 4. 2016.
 */
public class SentimentAnalyser {
    private static final int TRENDS_COUNT = 10;
    private static final int TWEETS_COUNT = 100;
    private static final int TREND_PLACE_WOEID =  2459115; //New York

    public static void main(String[] args) {
        try {
            execute();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private static void execute() throws TwitterException {
        NLPAnalyser nlpAnalyser = new NLPAnalyser();
        nlpAnalyser.init();

        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setDebugEnabled(true)
//                .setOAuthConsumerKey("ytW8IsUb7A3Rb7ogoRyaGFN0W")
//                .setOAuthConsumerSecret("nHBMdmrwYzY96QOIj3xq0Jutueg4oeqGF834zyuMIKmEfYkCXP")
//                .setOAuthAccessToken("43859767-I6AwfHHYdnFoOvlAKW4TeXvsqMCU6DzLTRALw8mP9")
//                .setOAuthAccessTokenSecret("iuP49ltnqOc1KG316XYt93tTIjI0edloSgeZGXlpQsuzS")
//                .setJSONStoreEnabled(true);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Trends trends = twitter.getPlaceTrends(TREND_PLACE_WOEID);
        for (int j = 0; j < TRENDS_COUNT; j++) {
            int vNegative = 0;
            int negative = 0;
            int neutral = 0;
            int positive = 0;
            int vPositive = 0;
            Query query = new Query(trends.getTrends()[j].getName());

            query.count(TWEETS_COUNT);
            QueryResult result = twitter.search(query);
            int[] resultsArray = new int[TWEETS_COUNT];
            int i = 0;
            for (Status status : result.getTweets()) {
                int score = nlpAnalyser.getSentiment(clearMessage(status.getText()));
                resultsArray[i] = score;
                switch (score) {
                    case 0:
                        vNegative++;
                        break;
                    case 1:
                        negative++;
                        break;
                    case 2:
                        neutral++;
                        break;
                    case 3:
                        positive++;
                        break;
                    case 4:
                        vPositive++;
                        break;
                }
                i++;
            }

            Arrays.sort(resultsArray);
            double med = ((double) resultsArray[TWEETS_COUNT / 2] + (double) resultsArray[TWEETS_COUNT/2 - 1]) / 2;
            TrendScore temp = new TrendScore(trends.getTrends()[j].getName(), vNegative, negative, neutral, positive, vPositive, med);
            System.out.println(temp.toString());
        }
    }


    /**
     * Clear string from @ # links and unsupported characters for better analyse.
     *
     * @param rawStatus raw message
     * @return clean message
     */
    public static String clearMessage(String rawStatus) {
        String clear;
        clear = rawStatus.replaceAll("\\s*RT\\s*@\\w+:", "");
        clear = clear.replaceAll("https?:[^\\s]*", "");
        clear = clear.replaceAll("#[^\\s]*", "");
        clear = clear.replaceAll("[^A-Za-z0-9 !,.:'?-]", "");
        return clear;
    }
}
