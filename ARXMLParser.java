// Parser Classes
import javax.xml.parsers.DocumentBuilderFactory;
// import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.File;

// Parsing elements
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

// Exceptions classes
import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;

// Regex
import java.util.regex.Pattern;

public class ARXMLParser {
    Document doc; // DOM document that holds the nodes tree
    public ARXMLParser(File file) throws IOException, SAXException, ParserConfigurationException, NotValidAutosarFileException {
        // if (file.getName().indexOf(".arxml") == -1) {
        //     throw new NotValidAutosarFileException();
        // }

        if (!Pattern.compile(".+\\.arxml$").matcher(file.getName()).matches()) { // If the file extension isn't '.arxml'
            throw new NotValidAutosarFileException();   // throw a NotValidAutosarFileException
        }

        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file); // parsing the file into Document object
    }

    public ARXMLParser(String path) throws IOException, SAXException, ParserConfigurationException, NotValidAutosarFileException {
        this(new File(path));
    }

    




}