import commands.SearchCommand;
import exception.ValidateException;
import org.junit.Assert;
import org.junit.Test;
import structures.TreeNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.function.Predicate;

public class SearchCommandTest {

    @Test(expected = ValidateException.class)
    public void createPredicateExceptionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ValidateException {
        SearchCommand command = new SearchCommand("");
        Method method = SearchCommand.class.getDeclaredMethod("createPredicate", String[].class);
        method.setAccessible(true);
        try {
            method.invoke(command, new Object[]{new String[]{}});
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof ValidateException) {
                throw new ValidateException();
            }
            throw new RuntimeException();
        }
    }

    @Test
    public void createPredicateTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TreeNode nodeForTest = createExpectedNode();

        SearchCommand command = new SearchCommand("");
        Method method = SearchCommand.class.getDeclaredMethod("createPredicate", String[].class);
        method.setAccessible(true);
        Predicate<TreeNode> predicateActualName = (Predicate) method.invoke(command, new Object[]{new String[]{"component", "-d"}});
        String name = "component";
        Predicate<TreeNode> predicateExpectedName = node -> node.getNameElement().equals(name);

        Assert.assertEquals(predicateExpectedName.test(nodeForTest), predicateActualName.test(nodeForTest));


        Predicate<TreeNode> predicateExpectedKeyValue = node -> {
            String attrValue = node.getAttributes().get("key");
            return attrValue != null && attrValue.equals("value");
        };
        Predicate<TreeNode> predicateActualKeyValue = (Predicate) method.invoke(command, new Object[]{new String[]{"key", "value", "-d"}});
        Assert.assertEquals(predicateExpectedKeyValue.test(nodeForTest), predicateActualKeyValue.test(nodeForTest));

    }

    public static TreeNode createExpectedNode() {

        TreeNode node = new TreeNode("project", "");
        TreeNode nodeComponent1 = new TreeNode("component", "1.55");
        TreeNode nodeEntry = new TreeNode("entry", "text");
        TreeNode nodeComponent2 = new TreeNode("component", "");

        node.addChild(nodeComponent1);
        node.addChild(nodeComponent2);
        nodeComponent1.addChild(nodeEntry);

        HashMap<String, String> attr = new HashMap<>();
        attr.put("version", "4");
        node.setAttributes(attr);

        HashMap<String, String> attrComponent1 = new HashMap<>();
        attrComponent1.put("name", "editorHistoryManager");
        nodeComponent1.setAttributes(attrComponent1);

        HashMap<String, String> attrEntry = new HashMap<>();
        attrEntry.put("file", "file://$PROJECT_DIR$/src/TreeNode.java");
        nodeEntry.setAttributes(attrEntry);

        HashMap<String, String> attrComponent2 = new HashMap<>();
        attrComponent2.put("name", "masterDetails");
        nodeComponent2.setAttributes(attrComponent2);

        return node;
    }

}
