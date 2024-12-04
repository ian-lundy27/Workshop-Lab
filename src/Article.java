import java.util.*;

public class Article {

    String name;
    String rawContent;
    String parsedContent;
    String[] wordList;

    public Article (String filePath, String name) {

        this.name = name.split("\\.")[0];

        try {
            //  Create file object for target file
            filePath += "/" + name;

            //  Assign read content to object field
            this.rawContent = ReadFiler.filepathToString(filePath);

            //  Initialize object fields
            parseContent();
            buildWordList();
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
        this.parsedContent = this.parsedContent.replaceAll("-- ","");
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
        if (!this.name.equals("stopwords")) {
            this.wordList = this.removeStopWords().toArray(new String[0]);
        }
    }

    public ArrayList<String> removeStopWords() {

        ArrayList<String> condensedWordList = new ArrayList<>(Arrays.asList(wordList));

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
}