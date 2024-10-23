import java.util.HashMap;

public class SentimentAnalysis {

    static HashMap<String,Integer> polarityMap;

    public SentimentAnalysis() {
    }

    public static int getArticlePolarity(Article article) {
        int polarity = 0;
        for (String word : article.wordList) {
            if (polarityMap.containsKey(word)) polarity += polarityMap.get(word);
        }
        return polarity;
    }

}
