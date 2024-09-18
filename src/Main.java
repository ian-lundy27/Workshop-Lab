import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

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

        HashMap<String,Integer> wordMap = new HashMap<String,Integer>();

        for (String word : wordList) {

            if (!wordMap.containsKey(word)) {
                wordMap.put(word, 1);
            } else {
                wordMap.put(word, wordMap.get(word) + 1);
            }

        }

        System.out.println(wordMap.toString());



    }

}