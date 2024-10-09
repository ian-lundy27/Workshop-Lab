import java.io.*;
import java.util.*;

public class Article {

    String name;
    String rawContent;
    String parsedContent;
    String[] wordList;
    HashMap<String,Integer> wordFrequency;
    LinkedList<String> wordFrequencyList;

    public Article (String filePath, String name) {

        this.name = name.split("\\.")[0];

        try {
            //  Create file object for target file
            filePath += "/" + name;
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
        } catch (Exception e) {
            System.out.println("Failed to initialize article '" + name + "': " + e.getMessage());
        }

    }

    public void parseContent() {

        //  Remove anything not word or number or period, reduce excess spaces
        //  These are a lot of regex rules that handle some non-obvious cases where
        //      removing any non-word character may cause errors, such as with the word x-ray
        this.parsedContent = this.rawContent.replaceAll("’s","");
        this.parsedContent = this.parsedContent.replaceAll("[',\"]","");
        this.parsedContent = this.parsedContent.replaceAll("([0-9])\\-([0-9])","$1 $2");
        this.parsedContent = this.parsedContent.replaceAll("([a-zA-Z])-([^a-zA-Z])","$1 $2");
        this.parsedContent = this.parsedContent.replaceAll("([^a-zA-Z])-([a-zA-Z])","$1 $2");
        this.parsedContent = this.parsedContent.replaceAll("[^A-Za-z0-9\\.\\-]+"," ");

        //  Replace any acronyms (such as U.S.A.) with non-period version (USA) so they don't get counted as sentences later
        //      Demarcate acronyms with %%%%, gets acronym sections and removes periods
        this.parsedContent = this.parsedContent.replaceAll("((?:\\w+\\.){2,})","%%$1%%");
        String[] temp = parsedContent.split("%%");
        for (int i = 1; i < temp.length; i += 2) {
            temp[i] = temp[i].replaceAll("\\.","");
        }
        this.parsedContent = String.join("", temp);

        //  Trim any excess whitespace
        this.parsedContent = this.parsedContent.trim();


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

    public int getArticleLength() {
        return this.wordList.length;
    }

    public ArrayList<String> removeStopWords() {

        ArrayList<String> condensedWordList = new ArrayList<>();

        condensedWordList.addAll(Arrays.asList(wordList));

        Article stopWords = new Article("library","stopwords.txt");
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

    public int statementCount() {
        int count = 0;

        for(int i = 0;i < parsedContent.length() - 1;i++) {
            if(parsedContent.substring(i,i+2).equals(". ") || parsedContent.substring(i,i+2).equals("? ") || parsedContent.substring(i,i+2).equals("! ")) {
                count++;
            }
        }

        return count + 1;
    }
}
