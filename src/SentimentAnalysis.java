import java.util.HashMap;

public class SentimentAnalysis {

    static HashMap<String,Integer> polarityMap;

    public SentimentAnalysis() {
        String csv = ReadFiler.filepathToString("library/word-sentiments.csv");
        String[] partial = csv.split("\n");
        polarityMap = new HashMap<>();
        int polarity = 0;
        for (String values : partial) {
            polarity = switch (values.split(",")[1]) {
                case "positive" -> 1;
                case "negative" -> -1;
                default -> polarity;
            };
            polarityMap.put(values.split(",")[0], polarity);
        }
    }

    public static int getArticlePolarity(Article article) {
        int polarity = 0;
        for (String word : article.wordList) {
            if (polarityMap.containsKey(word)) polarity += polarityMap.get(word);
        }
        return polarity;
    }

}
