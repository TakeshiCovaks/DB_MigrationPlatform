package structures;

import exception.ParserException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.processingXML.DomParserXML;
import structures.TreeNode;

import java.util.ArrayList;
import java.util.function.Predicate;

public class MyNodeTest {
    private static final String PATH_TEST_INPUT_FILE = "src\\test\\resources\\expectedNode.xml";
    private final static String VALUE_ATTR_FOR_SEARCH = "file://$PROJECT_DIR$/src/TreeNode.java";
    private final static String NAME_FOR_SEARCH = "component";
    private static final String KEY_ATTR_FOR_SEARCH = "file";

    private static Predicate<TreeNode> predicateName;
    private static Predicate<TreeNode> predicateKeyValueAttr;
    private static TreeNode correctTree;
    private static TreeNode expectedNode;
    private static TreeNode actualNode;
    private static TreeNode rootNode;

    @BeforeClass
    public static void getRootNode() throws ParserException {
        DomParserXML parser = new DomParserXML();
        try {
            rootNode = new TreeNode("", "");
            parser.createTreeFromFileXML(rootNode, PATH_TEST_INPUT_FILE);
        } catch (ParserException e) {
            e.printStackTrace();
            throw new ParserException("Dont create tree for test", e);
        }

        predicateName = node -> node.getNameElement().equals(NAME_FOR_SEARCH);

        predicateKeyValueAttr = node -> {
            String attrValue = node.getAttributes().get(KEY_ATTR_FOR_SEARCH);
            return attrValue != null && attrValue.equals(VALUE_ATTR_FOR_SEARCH);
        };
        correctTree = Methods.createExpectedNode();
    }

    @Test
    public void searchDepthTest() {

        ArrayList resultLis = rootNode.searchDepth(predicateName);
        Assert.assertEquals("Check count found element", 2, resultLis.size());

        expectedNode = correctTree.getListChild().get(0);
        actualNode = (TreeNode) resultLis.get(0);
        Assert.assertEquals(expectedNode, actualNode);

        expectedNode = correctTree.getListChild().get(1);
        actualNode = (TreeNode) resultLis.get(1);
        Assert.assertEquals(expectedNode, actualNode);

        resultLis.clear();

        resultLis = rootNode.searchDepth(predicateKeyValueAttr);
        Assert.assertEquals("Check count found element", 1, resultLis.size());

        actualNode = (TreeNode) resultLis.get(0);
        expectedNode = correctTree.getListChild().get(0).getListChild().get(0);
        Assert.assertEquals(expectedNode, actualNode);
    }

    @Test
    public void searchWidthTest() {

        ArrayList<TreeNode> resultLis = rootNode.searchWidth(predicateName);
        Assert.assertEquals("Check count found element", 2, resultLis.size());

        expectedNode = correctTree.getListChild().get(0);
        actualNode = resultLis.get(0);
        Assert.assertEquals(expectedNode, actualNode);

        expectedNode = correctTree.getListChild().get(1);
        actualNode = resultLis.get(1);
        Assert.assertEquals(expectedNode, actualNode);

        resultLis.clear();

        resultLis = rootNode.searchWidth(predicateKeyValueAttr);
        Assert.assertEquals("Check count found element", 1, resultLis.size());

        expectedNode = correctTree.getListChild().get(0).getListChild().get(0);
        actualNode = resultLis.get(0);
        Assert.assertEquals(expectedNode, actualNode);
    }
}
