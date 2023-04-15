public class NotValidAutosarFileException extends RuntimeException {
    public NotValidAutosarFileException() {
        super("The file is not a valid Autosar file");
    }
}
