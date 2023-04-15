// Exceptions Classes
import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, NotValidAutosarFileException {
        ARXMLParser parser = new ARXMLParser("./files/test.arxml");
        parser.sort();
    }
}
