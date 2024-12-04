import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class Topic {

    String name;
    ArrayList<Article> articles;
    String filePath;

    public Topic(String path) {

        filePath = path;
        String[] pathArray = path.split("/");
        pathArray = pathArray[pathArray.length - 1].split("\\.");
        this.name = pathArray[0].replaceAll("-"," ");
        try {
            File dir = new File(path);
            File[] files = dir.listFiles();
            this.articles = new ArrayList<>();
            if (files != null) {
                for (File file : files) {
                    this.articles.add(new Article(path, file.getName()));
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to initialize topic '" + name + "': " + e.getMessage());
        }
    }

    public Article richestText(){
        double richestWordCount = 0;
        Article richestArticle = null;
        for (Article article : articles) {
            if ((double)article.wordFrequencyList.size()/article.wordList.length > richestWordCount){
                richestArticle = article;
                richestWordCount = article.wordFrequencyList.size();
            }
        }
        return richestArticle;
    }

    public void top20Words(Article article){
        System.out.println("The top 20 non-stop words in this article are:");
        for (int i = 0; i < 20; i++) {
            String word = article.wordFrequencyList.get(i);
            System.out.println(i + 1 + ".\t" + word + "\t" + article.wordFrequency.get(word));
        }

    }

    public boolean addFileToDir(String path){
        Path source = Paths.get(path);
        Path target = Paths.get(this.filePath);
        try {
            Files.copy(source, target.resolve(source.getFileName()));
            System.out.println("File added successfully");
            return true;
        } catch (IOException e) {
            System.out.println("Failed to move file: " + e.getMessage());
            return false;
        }
    }

    public static void newTopic(String name) throws IOException {
        Path newDirectory = Paths.get(System.getProperty("user.dir") + "/library/" + name);
        Files.createDirectories(newDirectory);
    }
}
