import java.util.HashMap;

public class SentimentAnalysis {

    static HashMap<String,Float> polarityMap;
    static HashMap<String,String> modMap;

    /* public SentimentAnalysis() {
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
    } */

    public SentimentAnalysis() {
        String txt = ReadFiler.filepathToString("library/lexicon_scores.txt");
        String[] partial = txt.split("\n");
        polarityMap = new HashMap<>();
        float polarity = 0;
        for (String values : partial) {
            polarity = SentimentAnalysis.safeParseFloat(values.split("\t")[1]);
            polarityMap.put(values.split("\t")[0], polarity);
        }

        txt = ReadFiler.filepathToString("library/modifiers.csv");
        partial = txt.split("\n");
        modMap = new HashMap<>();
        for (String values : partial) {
            modMap.put(values.split(",")[0], values.split(",")[1]);
        }
    }

    public static float safeParseFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static float getArticlePolarity(Article article) {
        float polarity = 0;
        for (String word : article.wordList) {
            if (polarityMap.containsKey(word)) {
//                System.out.println(word + "\t" + polarityMap.get(word));
                polarity += polarityMap.get(word);
            }
        }
        return polarity;
    }

//    public static float dumbPolarity(Article article) {
//        float positive = 0;
//        float negative = 0;
//        float polarity;
//        float mod;
//        for (int i = 1; i < article.wordList.length; i++) {
//            if (polarityMap.containsKey(article.wordList[i])) {
//                polarity = polarityMap.get(article.wordList[i]);
//                if (modMap.containsKey(article.wordList[i - 1])) mod = modMap.get(article.wordList[i - 1]);
//                else mod = 1;
//                if (mod * polarity > 0) positive += mod * polarity;
//                else if (mod * polarity < 0) negative += mod * polarity;
//            }
//        }
//        return positive + negative;
//    }

    public static double tunedPolarity(Article article) {
        String[] sentences = article.parsedContent.split("\\.");
        boolean bad; double total = 0;
        for (String sentence : sentences) {
            bad = false;
            String[] words = sentence.split(" ");
            for (String word : words) {
                if (modMap.containsKey(word) &&
                        (modMap.get(word).equals("badfor") ||
                            modMap.get(word).equals("reverse"))) bad = true;
                if (polarityMap.containsKey(word)) total += bad ? -polarityMap.get(word) : polarityMap.get(word);
            }
        }
        return total / Math.log(article.wordList.length);
    }
}
