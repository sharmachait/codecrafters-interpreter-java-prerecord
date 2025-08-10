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
            Lexer lexer = new Lexer(fileContents);
            Lexer.Result result = lexer.scan();
            if(result.e!=null){
                System.out.println("EOF  null");
                System.exit(65);
            }
            for(Token token : result.tokens){
                System.out.println(token);
            }
        } else {
            System.out.println("EOF  null");
        }
    }
}
