import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Topic {

    String name;
    ArrayList<Article> articles;

    public Topic(String path) throws IOException {

        String[] pathArray = path.split("/");
        pathArray = pathArray[pathArray.length - 1].split("\\.");
        this.name = pathArray[0].replaceAll("-"," ");
        File dir = new File(path);
        File[] files = dir.listFiles();
        this.articles = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                this.articles.add(new Article(path,file.getName()));
            }
        }
    }

}
