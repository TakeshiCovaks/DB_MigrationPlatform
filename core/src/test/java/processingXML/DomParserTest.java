package processingXML;

import exception.ParserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import strategy.processingXML.DomParserXML;
import structures.Methods;
import structures.TreeNode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DomParserTest {

    private static final String PATH_TEST_INPUT_FILE = "src\\test\\resources\\expectedNode.xml";
    private static TreeNode expectedNode;
    private static TreeNode root;
    private static Document document;
    private static DomParserXML parser;
    private static Element element;

    @BeforeClass
    public static void initData() throws ParserException, ParserConfigurationException, IOException, SAXException {
        parser = new DomParserXML();
        root = new TreeNode("", "");
        parser.execute(root, PATH_TEST_INPUT_FILE);

        expectedNode = Methods.createExpectedNode();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(new File(PATH_TEST_INPUT_FILE));
        element = document.getDocumentElement();
    }

    @Before
    public void beforeMethod() {
        root.getListChild().clear();
    }

    @Test
    public void parseFileXMLTest() throws ParserException {

        parser.createTreeFromFileXML(root, PATH_TEST_INPUT_FILE);
        Assert.assertEquals(root, expectedNode);

        root.setData("fail data");
        Assert.assertNotEquals(root, expectedNode);
        root.setData("");
    }

    @Test
    public void spreadOutElementsTest() throws ParserException {
        parser.spreadOutElements(element.getChildNodes(), root);
        Assert.assertEquals(expectedNode, root);
    }

    @Test
    public void createMyNodeTest() {
        TreeNode nodeActual = new TreeNode(element.getTagName().trim(), "");
        nodeActual.setAttributes(parser.createAttributes(element.getAttributes()));

        TreeNode nodeExpected = expectedNode;
        Assert.assertNotEquals(expectedNode, nodeActual);

        nodeExpected.getListChild().clear();
        Assert.assertEquals(expectedNode, nodeActual);
    }



}
