import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Runner {
    public static void run(String filename) {
        String fileContents = "";
        try {
            fileContents = Files.readString(Path.of(filename));
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
