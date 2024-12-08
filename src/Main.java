import java.io.*;
import java.util.*;

public class Main {

    public Scanner in = new Scanner(System.in);
    public Topic[] allTopics;

    public static void main(String[] args) {

        Main main = new Main();
        main.selectOptions();

    }

    public Main() {
        discoverTopics();
    }

    public void discoverTopics() {
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
            this.allTopics =  topics.toArray(new Topic[0]);
        } else {
            System.out.println("No files found");
            this.allTopics = new Topic[0];
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
        if (article != null) {
            System.out.printf("Polarity of " + article.name + ": %.2f%n", SentimentAnalysis.tunedPolarity(article) / article.wordList.length * 100);
        }
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
        if (!topic.articles.isEmpty()) {
            for (int i = 0; i < topic.articles.size(); i++) {
                System.out.println(i + 1 + ".\t" + topic.articles.get(i).name);
            }
            int selection = getIntInput(1, topic.articles.size());
            return topic.articles.get(selection - 1);
        }
        System.out.println("No articles found under topic '" + topic.name + "'");
        return null;
    }

    public void addTopic() {
        System.out.print("Enter topic name: ");
        String topicName = in.nextLine();
        try {
            Topic.newTopic(topicName);
            System.out.println("Successfully created directory: " + topicName);
            discoverTopics();
        } catch (Exception e) {
            System.out.println("Failed to create directory: " + e.getMessage());
        }
    }

    public void addArticle() {
        Topic topic = selectTopic();
        System.out.print("Enter path to article text file: ");
        String path = in.nextLine();
        topic.addFileToDir(path);
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