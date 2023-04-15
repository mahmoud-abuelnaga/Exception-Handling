public class InvalidElementException extends RuntimeException {
    public InvalidElementException() {
        super("One of the elements doesn't have the specified node. Couldn't parse the XML.");
    }
}
