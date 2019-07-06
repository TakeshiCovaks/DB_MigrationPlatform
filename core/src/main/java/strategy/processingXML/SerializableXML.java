package strategy.processingXML;

import exception.SerialzableException;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import strategy.Constants;
import strategy.SerializableStrategy;
import structures.TreeNode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for serializing the tree object model to a file .xml
 */
public class SerializableXML implements SerializableStrategy {
    private static final Logger LOG = Logger.getLogger(SerializableXML.class);
    private ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    public Document document;

    public SerializableXML() {
    }

    /**
     * Method creates File.xml from the tree.
     *
     * @param node     root element tree.
     * @param fileName name file for serialization.
     */
    public void createXMLFileFromTheTree(TreeNode node, String fileName) throws SerialzableException {

        LOG.debug(bundle.getString("startSerialize") + fileName);
        Element elementRoot = prepareDocumentToFill(node);
        insertChildInParent(node, elementRoot);
        saveCreatedDocument(fileName);
        LOG.info(bundle.getString("endSerialize") + fileName);
    }

    /**
     * Creates a document to fill out from the tree.
     */
    private Element prepareDocumentToFill(TreeNode node) throws SerialzableException {
        Element elementRoot;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder builder = factory.newDocumentBuilder();

            document = builder.newDocument();
            document.setXmlStandalone(true);

            elementRoot = document.createElement(node.getNameElement());
            document.appendChild(elementRoot);

        } catch (ParserConfigurationException | DOMException e) {
            throw new SerialzableException(bundle.getString("PREPARE_EXCEPTION"), e);
        }
        return elementRoot;
    }

    /**
     * Saves the created document to a file .xml
     */
    private void saveCreatedDocument(String fileName) throws SerialzableException {

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult file = new StreamResult(new File(fileName));

            transformer.transform(domSource, file);

        } catch (TransformerException e) {
            throw new SerialzableException(bundle.getString("SERIALIZABLE_EXCEPTION"), e);
        }
    }

    /**
     * Insert all elements, attributes etc. in the .xml from TreeNode parent.
     */
    private void insertChildInParent(TreeNode parent, Element element) {

        if (parent != null) {
            HashMap<String, String> map = parent.getAttributes();
            for (HashMap.Entry<String, String> entry : map.entrySet()) {
                Attr attr = document.createAttribute(entry.getKey());
                attr.setValue(entry.getValue());
                element.setAttributeNode(attr);
            }
        }
        insertValueData(parent, element);

        for (int i = 0; i < parent.getListChild().size(); i++) {
            Element elementChild = document.createElement(parent.getListChild().get(i).getNameElement());
            insertChildInParent(parent.getListChild().get(i), elementChild);
            element.appendChild(elementChild);
        }
    }

    /**
     * Insert Text or CDATA in Element.
     */
    private void insertValueData(TreeNode parent, Element element) {
        if (haveCDATA(parent.getData())) {
            Node cdata = document.createCDATASection(parent.getData());
            element.appendChild(cdata);
        } else {
            element.setTextContent(parent.getData());
        }
    }

    /**
     * Check text field for CDATA object.
     *
     * @param data text value of element
     */
    private boolean haveCDATA(String data) {
        String regexp = "((<|>|'|\"|&)+)";
        Pattern pattern = Pattern.compile(regexp, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    @Override
    public void execute(TreeNode node, String fileName) throws SerialzableException {
        LOG.info(bundle.getString("executeSerialize"));

        if (node != null) {
            createXMLFileFromTheTree(node, fileName);
        } else {
            LOG.warn(bundle.getString("EMPTY_TREE_EXCEPTION"));
            throw new SerialzableException(bundle.getString("EMPTY_TREE_EXCEPTION"));
        }
    }
}
