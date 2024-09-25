import javax.lang.model.type.ArrayType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Topic {

    LinkedList<Article> articles;

    public Topic(String path) throws IOException {
        File dir = new File(path);
        File[] files = dir.listFiles();
        System.out.println(Arrays.toString(files));
        this.articles = new LinkedList<Article>();
        for (File file : files) {
            this.articles.push(new Article(file.toString()));
        }
        System.out.println(this.articles.getFirst().rawContent);
    }

}
