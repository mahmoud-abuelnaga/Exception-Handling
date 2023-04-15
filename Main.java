// Exceptions Classes
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        ARXMLParser parser = new ARXMLParser("./files/test.arxml");
    }
}
