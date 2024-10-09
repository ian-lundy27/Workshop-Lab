import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Topic {

    String name;
    LinkedList<Article> articles;

    public Topic(String path) throws IOException {

        String[] pathArray = path.split("/");
        pathArray = pathArray[pathArray.length - 1].split("\\.");
        this.name = pathArray[0];

        File dir = new File(path);
        File[] files = dir.listFiles();
        this.articles = new LinkedList<>();
        if (files != null) {
            for (File file : files) {
                this.articles.push(new Article(path,file.getName()));
            }
        }
    }

}
