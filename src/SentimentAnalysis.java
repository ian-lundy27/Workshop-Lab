import java.util.HashMap;

public class SentimentAnalysis {

    static HashMap<String,Float> polarityMap = instantiatePolarityMap();
    static HashMap<String,String> modMap = instantiateModMap();

    private static HashMap<String,Float> instantiatePolarityMap() {
        String txt = ReadFiler.filepathToString("library/lexicon_scores.txt");
        String[] partial = txt.split("\n");
        HashMap<String,Float> toReturn = new HashMap<>();
        float polarity;
        for (String values : partial) {
            polarity = SentimentAnalysis.safeParseFloat(values.split("\t")[1]);
            toReturn.put(values.split("\t")[0], polarity);
        }
        return toReturn;
    }

    private static HashMap<String,String> instantiateModMap() {
        String txt = ReadFiler.filepathToString("library/modifiers.csv");
        String[] partial = txt.split("\n");
        HashMap<String,String> toReturn = new HashMap<>();
        for (String values : partial) {
            toReturn.put(values.split(",")[0], values.split(",")[1]);
        }
        return toReturn;
    }

    public static float safeParseFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double tunedPolarity(Article article) {
        String[] sentences = article.parsedContent.split("\\.");
        boolean bad; double total = 0;
        for (String sentence : sentences) {
            bad = false;
            String[] words = sentence.split(" ");
            for (String word : words) {
                if (modMap.containsKey(word) && modMap.get(word).equals("reverse")) bad = !bad;
                if (polarityMap.containsKey(word)) total += bad ? -polarityMap.get(word) : polarityMap.get(word);
            }
        }
        return total;
    }
}
