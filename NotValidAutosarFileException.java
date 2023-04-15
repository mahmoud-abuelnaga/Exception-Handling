public class NotValidAutosarFileException extends Exception {
    public NotValidAutosarFileException() {
        super("The file is not a valid Autosar file");
    }
}
