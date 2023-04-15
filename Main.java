// Exceptions Classes
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.xml.sax.SAXException;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, NotValidAutosarFileException, TransformerFactoryConfigurationError, TransformerException {
        ARXMLParser parser = new ARXMLParser("./files/test.arxml");
        parser.writeSortedVersion();
    }
}
