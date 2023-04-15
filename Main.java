// Exceptions Classes
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.xml.sax.SAXException;

// Scanner class
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, NotValidAutosarFileException, TransformerFactoryConfigurationError, TransformerException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the path of the file to parse: ");
        String path = sc.nextLine();
        sc.close();
        ARXMLParser parser = new ARXMLParser(path);
        parser.writeSortedVersion("CONTAINER", "SHORT-NAME");
    }
}
