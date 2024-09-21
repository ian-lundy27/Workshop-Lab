import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {


        //  testing article class to make sure stuff works
        Article stop = new Article("media/project-information.txt");
        System.out.println(stop.rawContent);
        System.out.println(stop.parsedContent);
        System.out.println(Arrays.toString(stop.wordList));
        System.out.println(stop.wordFrequency.toString());
        System.out.println(stop.wordFrequencyList.toString());
        System.out.println(stop.getNumStatements());
        for (int i = 0; i < stop.wordFrequencyList.toArray().length; i++) {

            System.out.println(stop.wordFrequency.get(stop.wordFrequencyList.get(i)) + "\t" + stop.wordFrequencyList.get(i));

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