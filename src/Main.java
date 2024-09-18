import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {

        File file = new File("project-information.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String in;
        StringBuilder text = new StringBuilder();

        while ((in = br.readLine()) != null) {
            text.append(in).append("\n");
        }
        
        System.out.println(text);


    }

}
