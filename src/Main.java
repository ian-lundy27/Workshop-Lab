import java.io.*;
import java.util.*;

public class Main {

    public static Scanner in = new Scanner(System.in);
    public static Topic[] allTopics;
    public static Topic curTopic;
    public static Article curArticle;

    public static void main(String[] args) {

        new SentimentAnalysis();
        discoverTopics();
        selectTopic();

    }

    public static void discoverTopics() {
        File files = new File("library");
        String[] contents = files.list();
        ArrayList<Topic> topics = new ArrayList<>();
        if (contents != null) {
            ArrayList<String> subdirs = new ArrayList<>(Arrays.asList(contents));
            for (int i = 0; i < subdirs.size(); i++) {
                if (subdirs.get(i).contains(".")) {
                    subdirs.remove(i);
                    i--;
                } else {
                    topics.add(new Topic("library/" + subdirs.get(i)));
                }
            }
            allTopics = topics.toArray(new Topic[0]);
        } else {
            System.out.println("No files found, exiting");
            System.exit(1);
        }
    }

    public static void selectTopic() {
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

    public static void selectArticle() {
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

    public static void selectOption() {
        System.out.println("Select a statistic:");
        System.out.println("0.\tBack\n1.\tWord count\n2.\tNumber of sentences\n3.\tWord frequency (range)\n4.\tWord frequency (single word)\n5.\tRichest vocabulary\n6.\tPolarity\n7. \tTop 20 words");
        int selection = getIntInput(0,7);
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
                    getMultiWordFrequency();
                    break;
                case 4:
                    getSingleWordFrequency();
                    break;
                case 5:
                    Article richestArticle = curTopic.richestText();
                    System.out.println(richestArticle.name + " has the richest vocabulary among " + curTopic.name + " articles with " + richestArticle.wordFrequencyList.size() + " unique words");
                    break;
                case 6:
//                    System.out.printf("Polarity of " + curArticle.name + ": %.2f%n", SentimentAnalysis.tunedPolarity(curArticle) / Math.sqrt(curArticle.wordList.length) * 100);
                    System.out.printf("Polarity of " + curArticle.name + ", weighted linearly by word count: %.2f%n", SentimentAnalysis.tunedPolarity(curArticle) / curArticle.wordList.length * 100);
                    System.out.printf("Polarity of " + curArticle.name + ", unweighted by word count: %.2f%n", SentimentAnalysis.tunedPolarity(curArticle));
                    break;
                case 7:
                    curTopic.top20Words(curArticle);

            }
            selectOption();
        }
    }

    public static void getSingleWordFrequency() {
        System.out.print("Enter word: ");
        String word = in.nextLine();
        int frequency = 0;
        if (curArticle.wordFrequency.containsKey(word)) {
            frequency = curArticle.wordFrequency.get(word.toLowerCase());
        }
        System.out.println("The word '" + word  + "' occurs " + frequency + " times");
        selectOption();
    }

    public static void getMultiWordFrequency() {
        System.out.print("There are " + curArticle.wordFrequencyList.size() + " unique words in the article");
        int range = getIntInput(0,curArticle.wordFrequencyList.size());
        if (range == 0) return;
        for (int i = 0; i < range; i++) {
            String word = curArticle.wordFrequencyList.get(i);
            System.out.println(i + 1 + ".\t" + word + "\t" + curArticle.wordFrequency.get(word));
        }
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