/**
 * Created by Vitaliy Vashchenko on 1. 4. 2016.
 */
public class TrendScore {
    private String hashtag;
    private int vNegative;
    private int negative;
    private int neutral;
    private int positive;
    private int vPositive;
    private double median;

    public TrendScore(String hashtag, int vNegative, int negative, int neutral, int positive, int vPositive, double median) {
        this.hashtag = hashtag;
        this.vNegative = vNegative;
        this.negative = negative;
        this.neutral = neutral;
        this.positive = positive;
        this.vPositive = vPositive;
        this.median = median;
    }


    public void setMedian(double median) {
        this.median = median;
    }

    public TrendScore(String hashtag) {
        this.hashtag = hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public void setvNegative(int vNegative) {
        this.vNegative = vNegative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public void setvPositive(int vPositive) {
        this.vPositive = vPositive;
    }

    private String getMedianMood() {
        switch ((int)this.median) {
            case 0:
                return "Very negative";
            case 1:
                return "Negative";
            case 2:
                return "Neutral";
            case 3:
                return "POsitive";
            case 4:
                return "Very Positive";
            default:
                return "Unknown";
        }
    }

    @Override
    public String toString() {
        return "Hashtag: " + this.hashtag +
                "\n Very negative: " + this.vNegative +
                "\n Negative: " + this.negative +
                "\n Neutral: " + this.neutral +
                "\n Positive: " + this.positive +
                "\n Very positive: " + this.vPositive +
                "\n\n Median: " + this.median +
                "\n Trend Mood: " + getMedianMood() + "\n" ;
    }
}
