import java.io.File;
import java.util.ArrayList;

public class Topic {

    String name;
    ArrayList<Article> articles;

    public Topic(String path) {

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
        int richestWordCount = 0;
        Article richestArticle = null;
        for (Article article : articles) {
            if (article.wordFrequencyList.size() > richestWordCount){
                richestArticle = article;
                richestWordCount = article.wordFrequencyList.size();
            }
        }
        return richestArticle;
    }

}
