// Parser Classes
import javax.xml.parsers.DocumentBuilderFactory;
// import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.File;

// Parsing elements
import org.w3c.dom.NodeList;
// import org.w3c.dom.Node;
import org.w3c.dom.Element;

// Exceptions classes
import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;

import java.util.Arrays;
import java.util.List;
// Regex
import java.util.regex.Pattern;

public class ARXMLParser {
    private Document doc; // DOM document that holds the nodes tree

    /**
     * Constructor for ARXML parser that takes the File to parse and create a XML Document out of it
     * @param file ARXML file to parse
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws NotValidAutosarFileException
     * @throws EmptyAutosarFileException
     */
    public ARXMLParser(File file) throws IOException, SAXException, ParserConfigurationException, NotValidAutosarFileException, EmptyAutosarFileException {
        if (!Pattern.compile(".+\\.arxml$").matcher(file.getName()).matches()) { // If the file extension isn't '.arxml'
            throw new NotValidAutosarFileException();   // throw a NotValidAutosarFileException
        }

        if (file.length() == 0) { // If file is empty
            throw new EmptyAutosarFileException();  // throw a EmptyAutosarFileException
        }

        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file); // parsing the file into Document object
        doc.getDocumentElement().normalize(); // Process text nodes deep down the tree
    }

    /**
     * Constructor for ARXML parser that takes the path of the file to parse and create a XML Document out of it
     * @param path Path of the file to parse
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws NotValidAutosarFileException
     * @throws EmptyAutosarFileException
     * @throws EmptyAutosarFileException
     */
    public ARXMLParser(String path) throws IOException, SAXException, ParserConfigurationException, NotValidAutosarFileException, EmptyAutosarFileException, EmptyAutosarFileException {
        this(new File(path));
    }

    private Element[] getContainers() {
        NodeList containers = doc.getElementsByTagName("CONTAINER"); // Get CONTAINERs NodeList
        Element[] conts = new Element[containers.getLength()];  // Element array to store CONTAINERs

        for (int i = 0; i < containers.getLength(); i++) {  // Loop through each CONTAINER
            conts[i] = (Element)containers.item(i); // Get ith container, cast it to Element and store it in 'conts' array
        }

        return conts;
    }

    private String[] getShortNames(Element[] containers) {
        String[] names = new String[containers.length];
        for (int i = 0; i < containers.length; i++) {
            names[i] = containers[i].getElementsByTagName("SHORT-NAME").item(0).getTextContent();   // Get the SHORT-NAME in CONTAINER
        }

        return names;
    }

    private Element[] sortContainers(Element[] containers, String[] shortNames) {
        List<String> names = Arrays.asList(shortNames);
        // Create sorted copy of SHORT-NAMES
        String[] sortedNames = shortNames.clone();
        Arrays.sort(sortedNames);

        // Create sorted array of containers
        Element[] sortedContainers = new Element[containers.length];
        for(int i = 0; i < sortedNames.length; i++) {
            sortedContainers[i] = containers[names.indexOf(sortedNames[i])];
        }

        return sortedContainers;
    }

    public void sort() {
        Element[] containers = getContainers();
        Element[] sortedContainers = sortContainers(containers, getShortNames(containers));
        printElementsData(sortedContainers);
    }

    public static void printElementsData(Element[] elements) {
        for(Element elem: elements) {
            System.out.println(elem.getTextContent());
        }
    }


}