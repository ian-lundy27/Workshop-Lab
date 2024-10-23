import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadFiler {

    public static String filepathToString(String filePath) {
        File file = new File(filePath);
        return fileToString(file);
    }

    public static String fileToString(File file) {
        try {
            //  Open reader for file
            BufferedReader br = new BufferedReader(new FileReader(file));

            //  Declare vars for temp storing each line and for building string of all text
            String line;
            StringBuilder text = new StringBuilder();

            //  Read file line by line, adding to string builder
            while ((line = br.readLine()) != null) {
                text.append(line).append("\n");
            }

            br.close();

            return text.toString();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
