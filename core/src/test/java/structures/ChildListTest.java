package structures;

import exception.ParentExistException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import structures.ChildList;
import structures.TreeNode;

import java.util.LinkedList;

public class ChildListTest {
    ChildList list = new ChildList();

    @Before
    public void beforeMethod() {
        list.clear();
    }

    @Test(expected = ParentExistException.class)
    public void addTestFailData() {
        TreeNode nodeNotCorrect = new TreeNode("test", "");
        nodeNotCorrect.setParent(new TreeNode());
        list.add(nodeNotCorrect);
    }

    @Test
    public void addTestCorrectData() {
        TreeNode parentNode = new TreeNode("parent", "");
        TreeNode insertNode = new TreeNode("child", "");
        list.add(insertNode);
        if (list.size() == 1) {
            Assert.assertEquals(insertNode, list.get(0));
        } else {
            Assert.fail();
        }
    }

    @Test(expected = ParentExistException.class)
    public void addAllFailData() {
        TreeNode parentNode = new TreeNode("parent", "");
        TreeNode insertNode1 = new TreeNode("child1", "");
        insertNode1.setParent(new TreeNode());
        TreeNode insertNode2 = new TreeNode("child2", "");
        ChildList nodes = new ChildList();
        nodes.add(insertNode1);
        nodes.add(insertNode2);
        parentNode.addAllChild(nodes);
    }

    @Test
    public void addAllCorrectData() {
        TreeNode parentNode = new TreeNode("parent", "");
        TreeNode insertNode1 = new TreeNode("child1", "");
        TreeNode insertNode2 = new TreeNode("child2", "");
        LinkedList<TreeNode> nodes = new LinkedList<>();
        nodes.add(insertNode1);
        nodes.add(insertNode2);
        parentNode.getListChild().addAll(nodes);

        if (parentNode.getListChild().size() == 2) {
            Assert.assertEquals(nodes, parentNode.getListChild());
        } else {
            Assert.fail();
        }
    }


}
