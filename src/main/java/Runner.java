import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Runner {
    public static void run(String filename) {
        String fileContents = "";
        try {
            byte[] bytes = Files.readAllBytes(Path.of(filename));
            fileContents = new String(bytes, Charset.defaultCharset());
            System.out.println(fileContents);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }

        if (fileContents.length() > 0) {
            throw new RuntimeException("Scanner not implemented");
        }
    }
}
