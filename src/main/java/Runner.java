import lexicon.Lexer;
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

        if(fileContents.isEmpty()){
            System.out.println("EOF  null");
            return;
        }

        Lexer lexer = new Lexer(fileContents);
        Lexer.Result result = lexer.scan();
        for(Token token : result.tokens){
            System.out.println(token);
        }
        if(result.exception != null) {
            System.exit(65);
        }
    }
}
