import lexicon.LoxScanner;
import lexicon.ScanException;
import lexicon.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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

        if (!fileContents.isEmpty()) {
            LoxScanner scanner = new LoxScanner(fileContents);
            List<Token> tokens ;
            try{
                tokens = scanner.scan();
            } catch (ScanException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(tokens);
        } else {
            System.out.println("EOF  null");
        }
    }
}
