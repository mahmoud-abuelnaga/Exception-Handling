public class EmptyAutosarFileException extends RuntimeException {
    public EmptyAutosarFileException() {
        super("The file is empty or doesn't exist.");
    }
}