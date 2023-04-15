// Parser Classes
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.File;

// Parsing elements
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

// Exceptions classes
import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;

// Functions Requirements
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;

// Writing file classes
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ARXMLParser {
    private Document doc; // DOM document that holds the nodes tree
    private File f; // File to parse

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

        if (file.length() == 0) { // If file is empty or doesn't exist
            throw new EmptyAutosarFileException();  // throw a EmptyAutosarFileException
        }

        f = file;
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

    /**
     * A function that return Element array of corresponding tag
     * @param node the tag to get its nodes or elements
     * @return  Element[] tag
     */
    private Element[] getElements(String node) {
        NodeList nodes = doc.getElementsByTagName(node); // Get NodeList of tag
        Element[] elements = new Element[nodes.getLength()];  // Element array to store the nodes (elements) of correponding tag

        for (int i = 0; i < nodes.getLength(); i++) {  // Loop through each node in NodeList
            elements[i] = (Element)nodes.item(i); // Get ith node, cast it to Element and store it in 'elements' array
        }

        return elements;
    }

    /**
     * The function takes the node to get its text and the Element[] where to find this node. It returns String[], where each element correponds the text of a node.
     * Note that: the function only returns the text of the first node it finds in an element
     * @param node the node to get its text
     * @param elements elements where to search for this node
     * @throws InvalidElementException
     * @return  String[] nodesText
     */
    private String[] getNodesText(String node, Element[] elements) throws InvalidElementException {
        String[] nodesText = new String[elements.length];   // Array to store the text of nodes extracted from elements
        for (int i = 0; i < elements.length; i++) { // Loop through each element
            try {
                nodesText[i] = elements[i].getElementsByTagName(node).item(0).getTextContent();   // Get the node text of the ith element
            } catch (java.lang.NullPointerException e) {    // If the node doesn't exist in the element
                throw new InvalidElementException();    // throw InvalidElementException
            }
        }

        return nodesText;
    }

    /**
     * Function takes the elements to sort and the array of InnerNodes Text to sort based upon. The function sorts the InnerNodesText alphabetically and sort the elements based upon that sorting.
     * @param elements array of container elements to sort
     * @param InnerNodesText array of text inside elements' child nodes to sort upon
     * @return ELEMENT[] sortedElements
     */
    private Element[] sortElements(Element[] elements, String[] InnerNodesText) {
        List<String> names = Arrays.asList(InnerNodesText);
        // Create sorted copy of SHORT-NAMES
        String[] sortedNames = InnerNodesText.clone();
        Arrays.sort(sortedNames);

        // Create sorted array of containers
        Element[] sortedElements = new Element[elements.length];
        for(int i = 0; i < sortedNames.length; i++) {   // Loop through each sorted text
            sortedElements[i] = elements[names.indexOf(sortedNames[i])];
        }

        return sortedElements;
    }

    private Element[] sortElements(Element[] elements, String node) {
        return sortElements(elements, getNodesText(node, elements));
    }

    /**
     * A function that writes a sorted version of XML's passed parentNodeToSort based on the InnerNodeToSortUpon. Note: the InnerNodeToSortUpon needs to contain String
     * @param parentNodeToSort
     * @param InnerNodetoSortUpon
     * @throws ParserConfigurationException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     */
    public void writeSortedVersion(String parentNodeToSort, String InnerNodetoSortUpon) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        // Sort the CONTAINER elements
        Element[] elements = getElements(parentNodeToSort);
        Element[] sortedElements = sortElements(elements, InnerNodetoSortUpon);

        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument(); // Create new XML document using DocumentBuilder
        // Create the XML file structure
        Element rootElement = document.createElement("AUTOSAR"); // Create the root element of document 'AUTOSAR'
        for(Element element: sortedElements) {  // loop through each container
            rootElement.appendChild(document.importNode(element, true)); // Add it inside the root element 'AUTOSAR'
        }
        document.appendChild(rootElement);   // Add the root element to the document

        // Write content into XML file
        Transformer transformer = TransformerFactory.newInstance().newTransformer();    // Create a transformer to write the xml data to a file
        DOMSource source = new DOMSource(document);  // The source from which we will write the file
        StreamResult result = new StreamResult(new File("./files/" + f.getName().substring(0, f.getName().indexOf('.')) + "_mod.arxml"));
        transformer.transform(source, result);
    }

    public static void printElementsData(Element[] elements) {
        for(Element elem: elements) {
            System.out.println(elem.getTextContent());
        }
    }


}