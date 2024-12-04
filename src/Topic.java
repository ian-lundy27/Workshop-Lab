import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    public void addFileToDir(String path){
        //  stupid logic to remove " at start and end of string, because copied filepath on windows adds ""
        while (path.charAt(0) == '"') path = path.substring(1);
        while (path.charAt(path.length() - 1) == '"') path = path.substring(0, path.length() - 1);

        Path source = Paths.get(path);
        Path target = Paths.get(this.filePath);
        try {
            Files.copy(source, target.resolve(source.getFileName()));
            System.out.println("File added successfully");
        } catch (IOException e) {
            System.out.println("Failed to move file: " + e.getMessage());
        }
    }

    public static void newTopic(String name) throws IOException {
        Path newDirectory = Paths.get(System.getProperty("user.dir") + "/library/" + name);
        Files.createDirectories(newDirectory);
    }
}
