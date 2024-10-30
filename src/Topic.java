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
}
