package strategy.processingXML;

import exception.ParserException;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import strategy.Constants;
import strategy.DeserializeStrategy;
import structures.TreeNode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Reads a file and fills the tree's structural model with values.
 */
public final class DomParserXML implements DeserializeStrategy {
    private static final Logger LOG = Logger.getLogger(DomParserXML.class);
    private ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    public DomParserXML() {
    }

    /**
     * Creates a tree structure from the set of nodes read from the file.
     *
     * @return tree root.
     */
    public void createTreeFromFileXML(TreeNode node, String fileName) throws ParserException {
        Element element = null;
        try {
            element = createElementFromFile(fileName);
        } catch (Exception exc){
            throw  new ParserException(bundle.getString("cantPrepareElement"), exc);
        }

        if (!(element.getTagName().trim().isEmpty())) {
            node.setNameElement(element.getTagName().trim());
            node.setAttributes(createAttributes(element.getAttributes()));
            LOG.debug("Created root element-> " + node);
        }
        LOG.debug(bundle.getString("startCreateTree"));
        spreadOutElements(element.getChildNodes(), node);
        LOG.info(bundle.getString("successfullyParsing"));
    }

    /**
     * Method read XML file and create Element from which the tree will be built
     */
    private Element createElementFromFile(String fileName) throws ParserConfigurationException, IOException, SAXException {

        Element element = null;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            LOG.debug(bundle.getString("initDocument"));

            element = document.getDocumentElement();

        return element;
    }

    /**
     * The method reads all children of the node and writes them to the tree.
     *
     * @param nodeList list children
     * @param parent   current parent
     */
    public void spreadOutElements(NodeList nodeList, TreeNode parent) {

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {
                TreeNode node = new TreeNode(nodeList.item(i).getNodeName(), getTextElement(nodeList.item(i)));
                node.setAttributes(createAttributes(nodeList.item(i).getAttributes()));
                parent.addChild(node);
                if (nodeList.item(i).getChildNodes().getLength() > 0) {
                    spreadOutElements(nodeList.item(i).getChildNodes(), node);
                }
            }
        }
    }

    /**
     * Method create HashMap attributes for Node from NamedNodeMap
     *
     * @param attributes from NamedNodeMap
     * @return HashMap with attributes for Node
     */
    public HashMap<String, String> createAttributes(NamedNodeMap attributes) {
        NamedNodeMap nodeMap = attributes;
        HashMap<String, String> attr = new HashMap<>();
        if (nodeMap.getLength() > 0) {
            for (int i = 0; i < nodeMap.getLength(); i++) {
                attr.put(nodeMap.item(i).getNodeName(), nodeMap.item(i).getTextContent());
            }
        }
        return attr;
    }

    /**
     * Method get item text.
     *
     * @param item current node.
     * @return text of the current item
     */
    private String getTextElement(Node item) {
        String value = "";
        if ((!item.getTextContent().trim().isEmpty()
                && !((Text) item.getFirstChild()).getData().trim().isEmpty())
                && !((Text) item.getFirstChild()).getData().trim().equals("\n")) {
            Text text = (Text) item.getFirstChild();
            value += text.getData().trim();
        }
        return value;
    }

    @Override
    public TreeNode execute(TreeNode node, String fileName) throws ParserException {
        LOG.info(bundle.getString("startParsing"));
        node = (node == null) ? new TreeNode("",""): node;
        createTreeFromFileXML(node, fileName);
        return node;
    }
}
