import java.io.*;
import java.util.*;

public class Main {

    public static Scanner in = new Scanner(System.in);
    public static Topic[] allTopics;
    public static Topic curTopic;
    public static Article curArticle;

    public static void main(String[] args) throws Exception {


        discoverTopics();
        selectTopic();

        thing();
        System.exit(0);


        //  testing article class to make sure stuff works
        Article yale = new Article("library/nuclear-power","article1.txt");
        Article oneEarth = new Article("library/nuclear-power","article2.txt");
        Article energy = new Article("library/nuclear-power","article3.txt");
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



        File file = new File("library/project-information.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String input;
        StringBuilder text = new StringBuilder();

        while ((input = br.readLine()) != null) {
            text.append(input).append("\n");
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



    public static void thing() throws IOException {

        Topic t = new Topic("library/nuclear-power");
        System.out.println(t.articles);

        Topic[] topics;
        topics = new Topic[]{new Topic("library/nuclear-power")};

        String input;
        Topic currentTopic;
        Article currentArticle;

        label:
        while (true) {
            currentTopic = null;
            currentArticle = null;
            System.out.println("See article stats");
            System.out.println("Topics:");
            for (Topic topic : topics) {
                System.out.println(topic.name);
            }
            System.out.print("Enter a topic name: ");
            input = in.nextLine();
            for (Topic topic : topics) {
                if (topic.name.equals(input)) {
                    currentTopic = topic;
                    break;
                }
            }
            if (currentTopic == null) {
                System.out.println("Topic not found");
                continue;
            }

            System.out.println("Which article do you want to see?");

            for (Article article : currentTopic.articles) {
                System.out.println(article.name);
            }

            System.out.print("Enter the article you want to see: ");
            input = in.nextLine();
            for (Article article : currentTopic.articles) {
                if (article.name.equals(input)) {
                    currentArticle = article;
                    break;
                }
            }
            if (currentArticle == null) {
                System.out.println("Article not found");
                continue;
            }

            System.out.println("What stats do you want to see?");
            System.out.println("1\tWord count\n2\tNumber of sentences");
            System.out.print("Enter selection: ");
            input = in.nextLine();
            switch (input) {
                case "1":
                    System.out.println(currentArticle.wordList.length);
                    break;
                case "2":
                    System.out.println(currentArticle.statementCount());
                    break;
                case "3":
                    break label;
                default:
                    System.out.println("Invalid selection");
                    break;
            }




        }

    }

    public static void discoverTopics() throws IOException {
        allTopics = new Topic[]{new Topic("library/nuclear-power")};
    }

    public static void selectTopic() throws IOException {
        System.out.println("Select a topic:\n0.\tExit");
        for (int i = 0; i < allTopics.length; i++) {
            System.out.println(i + 1 + ".\t" + allTopics[i].name);
        }
        int selection = getIntInput(0,allTopics.length);
        if (selection == 0) System.exit(0);
        else {
            curTopic = allTopics[selection - 1];
            selectArticle();
        }
    }

    public static void selectArticle() throws IOException {
        System.out.println("Select an article:\n0.\tBack");
        for (int i = 0; i < curTopic.articles.size(); i++) {
            System.out.println(i + 1 + ".\t" + curTopic.articles.get(i).name);
        }
        int selection = getIntInput(0,curTopic.articles.size());
        if (selection == 0) selectTopic();
        else {
            curArticle = curTopic.articles.get(selection - 1);
            selectOption();
        }
    }

    public static void selectOption() throws IOException {
        System.out.println("Select a statistic:");
        System.out.println("0.\tBack\n1.\tWord count\n2.\tNumber of sentences\n3.\tWord frequency (all)\n4.\tWord frequency (specific)");
        int selection = getIntInput(0,4);
        if (selection == 0) selectArticle();
        else {
            switch (selection) {
                case 1:
                    System.out.println("There are " + curArticle.wordList.length + " words in the article");
                    break;
                case 2:
                    System.out.println("There are " + curArticle.statementCount() + " sentences in the article");
                    break;
                case 3:
                    for (int i = 0; i < curArticle.wordFrequencyList.size(); i++) {
                        String word = curArticle.wordFrequencyList.get(i);
                        System.out.println(i + 1 + ".\t" + word + "\t" + curArticle.wordFrequency.get(word));
                    }
                    break;
                case 4:
                    getSingleWordFrequency();
                    break;
            }
            selectOption();
        }
    }

    public static void getSingleWordFrequency() throws IOException {
        System.out.print("Enter word: ");
        String word = in.nextLine();
        int frequency = 0;
        if (curArticle.wordFrequency.containsKey(word)) {
            frequency = curArticle.wordFrequency.get(word);
        }
        System.out.println("The word '" + word  + "' occurs " + frequency + " times");
        selectOption();
    }

    public static int getIntInput(int min, int max) {
        while (true) {
            System.out.print("Enter selection: ");
            if (in.hasNextInt()) {
                int selection = in.nextInt();
                in.nextLine();
                if (selection >= min && selection <= max) return selection;
            } else {
                in.nextLine();
            }
            System.out.println("Enter a number between " + min + " and " + max);
        }
    }

}