import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {


        //  testing article class to make sure stuff works
        Article yale = new Article("media/Article1.txt");
        Article oneEarth = new Article("media/article2.txt");
        Article energy = new Article("media/article3.txt");
        //Article 1: https://e360.yale.edu/features/why-nuclear-power-must-be-part-of-the-energy-solution-environmentalists-climate
        System.out.println(yale.rawContent);
        System.out.println(yale.parsedContent);
        System.out.println(Arrays.toString(yale.wordList));
        System.out.println(yale.wordFrequency.toString());
        System.out.println(yale.wordFrequencyList.toString());
        System.out.println(yale.getNumStatements());
        for (int i = 0; i < yale.wordFrequencyList.toArray().length; i++) {

            System.out.println(yale.wordFrequency.get(yale.wordFrequencyList.get(i)) + "\t" + yale.wordFrequencyList.get(i));

        }

        System.exit(0);



        File file = new File("media/project-information.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String in;
        StringBuilder text = new StringBuilder();

        while ((in = br.readLine()) != null) {
            text.append(in).append("\n");
        }

        System.out.println(text);

        String strippedText = text.toString().replaceAll("[^A-Za-z0-9]"," ").replaceAll(" +"," ").toLowerCase().trim();
        System.out.println(strippedText);

        String[] wordList = strippedText.split(" ");
        System.out.println(wordList.length);
        System.out.println(Arrays.toString(wordList));

        HashMap<String,Integer> wordMap = new HashMap<>();

        for (String word : wordList) {

            if (!wordMap.containsKey(word)) {
                wordMap.put(word, 1);
            } else {
                wordMap.put(word, wordMap.get(word) + 1);
            }

        }

        System.out.println(wordMap.toString());

        LinkedList<String> sortedWordCount = new LinkedList<>(wordMap.keySet());
        sortedWordCount.sort((o1,o2) -> wordMap.get(o2).compareTo(wordMap.get(o1)));

        System.out.println(sortedWordCount);
        System.out.println(sortedWordCount.getFirst());
        System.out.println(wordMap.get(sortedWordCount.getFirst()));
        System.out.println(sortedWordCount.getLast());
        System.out.println(wordMap.get(sortedWordCount.getLast()));

    }

}