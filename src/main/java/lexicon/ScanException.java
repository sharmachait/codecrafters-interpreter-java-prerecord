package lexicon;

public class ScanException extends Exception {
    public ScanException(String message) {
        super(message);
        System.err.println(message);
    }
}
