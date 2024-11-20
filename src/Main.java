import java.io.*;
import java.util.*;

public class Main {

    public Scanner in = new Scanner(System.in);
    public Topic[] allTopics;

    public static void main(String[] args) {

        new SentimentAnalysis();

        Main main = new Main();
        main.selectOptions();

    }

    public Main() {
        this.allTopics = discoverTopics();
    }

    public Topic[] discoverTopics() {
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
            return topics.toArray(new Topic[0]);
        } else {
            System.out.println("No files found");
            return new Topic[0];
        }
    }

    public void selectOptions() {
        System.out.println("Select an option:\n0.\tExit\n1.\tGet article positivity\n2.\tAdd a topic\n3.\tAdd an article");
        int selection = getIntInput(0,3);
        if (selection != 0) {
            switch (selection) {
                case 1:
                    getArticlePositivity();
                    break;
                case 2:
                    addTopic();
                    break;
                case 3:
                    addArticle();
            }
            selectOptions();
        }
    }

    public void getArticlePositivity() {
        Topic topic = selectTopic();
        Article article = selectArticle(topic);
        System.out.printf("Polarity of " + article.name + ", weighted linearly by word count: %.2f%n", SentimentAnalysis.tunedPolarity(article) / article.wordList.length * 100);
        System.out.printf("Polarity of " + article.name + ", unweighted by word count: %.2f%n", SentimentAnalysis.tunedPolarity(article));
    }

    public Topic selectTopic() {
        System.out.println("Select a topic:");
        for (int i = 0; i < allTopics.length; i++) {
            System.out.println(i + 1 + ".\t" + allTopics[i].name);
        }
        int selection = getIntInput(1,allTopics.length);
        return allTopics[selection - 1];
    }

    public Article selectArticle(Topic topic) {
        System.out.println("Select an article:");
        for (int i = 0; i < topic.articles.size(); i++) {
            System.out.println(i + 1 + ".\t" + topic.articles.get(i).name);
        }
        int selection = getIntInput(1,topic.articles.size());
        return topic.articles.get(selection - 1);
    }

    public void addTopic() {
        System.out.print("Enter topic name: ");
        String topicName = in.nextLine();
//      add topic directory
        discoverTopics();
    }

    public void addArticle() {
        Topic topic = selectTopic();
        System.out.print("Enter path to article text file: ");
        String path = in.nextLine();
//      add article file to topic dir
        discoverTopics();
    }

    public int getIntInput(int min, int max) {
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