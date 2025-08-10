import lexicon.Lexer;
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
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }

        if (!fileContents.isEmpty()) {
            try{
                Lexer lexer = new Lexer(fileContents);
                List<Token> tokens = lexer.scan();
                for(Token token : tokens){
                    System.out.println(token);
                }
            } catch (ScanException e) {
                System.out.println(e.getMessage());
                System.exit(65);
            }
        } else {
            System.out.println("EOF  null");
        }
    }
}
