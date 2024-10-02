import java.io.*;
import java.util.*;

public class Article {

    String rawContent;
    String parsedContent;
    String[] wordList;
    HashMap<String,Integer> wordFrequency;
    LinkedList<String> wordFrequencyList;

    public Article (String filePath) throws IOException {

        //  Create file object for target file
        File article = new File(filePath);

        //  Open reader for file
        BufferedReader br = new BufferedReader(new FileReader(article));

        //  Declare vars for temp storing each line and for building string of all text
        String line;
        StringBuilder text = new StringBuilder();

        //  Read file line by line, adding to string builder
        while ((line = br.readLine()) != null) {
            text.append(line).append("\n");
        }

        br.close();

        //  Assign read content to object field
        this.rawContent = text.toString();

        //  Initialize object fields
        parseContent();
        buildWordList();
        findWordFrequency();
        sortFrequencyList();

    }

    public void parseContent() {

        //  Remove anything not word or number or period, reduce excess spaces
        this.parsedContent = this.rawContent.replaceAll("[^A-Za-z0-9\\.]"," ").replaceAll(" +"," ").trim();

    }

    public void buildWordList() {

        //  Reduces strings to just words + numbers, splits string into array
        this.wordList = this.parsedContent.replaceAll("\\.","").toLowerCase().split(" ");

    }

    public void findWordFrequency() {

        //  Initialize frequency key/value map
        this.wordFrequency = new HashMap<>();

        //  Add occurrences of each word to map
        for (String word : this.wordList) {

            if (!wordFrequency.containsKey(word)) {

                wordFrequency.put(word, 1);

            } else {

                wordFrequency.put(word, wordFrequency.get(word) + 1);

            }

        }

    }

    public void sortFrequencyList() {

        //  Creates linked list of all keys/words in the frequency map
        this.wordFrequencyList = new LinkedList<>(this.wordFrequency.keySet());

        //  Sorts list based on word frequency
        //  If two words have same frequency, sorts alphabetically
        this.wordFrequencyList.sort((k1, k2) ->
                this.wordFrequency.get(k2).compareTo(this.wordFrequency.get(k1)) == 0
                ? k1.compareTo(k2)
                : this.wordFrequency.get(k2).compareTo(this.wordFrequency.get(k1))
        );

    }

    public int getNumStatements() {

        //  Counts num periods, takes length of full text and subtracts length of text w/o periods to get # periods
        //  Assumes sentences have '. ', doesn't count last sentence. Oops
        return this.parsedContent.length() - this.parsedContent.replaceAll("\\. "," ").length();

    }

    public ArrayList<String> removeStopWords() throws IOException {

        ArrayList<String> condensedWordList = new ArrayList<>();

        condensedWordList.addAll(Arrays.asList(wordList));

        Article stopWords = new Article("library/stopwords.txt");
        stopWords.parseContent();
        stopWords.buildWordList();

        for(int i = 0;i < stopWords.wordList.length;i++) {

            for(int j = 0;j < condensedWordList.size();j++) {

                if (stopWords.wordList[i].equals(condensedWordList.get(j))) {

                    condensedWordList.remove(j);
                    j--;
                }
            }
        }

        return condensedWordList;
    }
}
