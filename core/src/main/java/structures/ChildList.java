package structures;

import exception.ParentExistException;
import strategy.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class for storing a list of nodes of type TreeNode is used to store list descendants.
 */
public class ChildList extends ArrayList<TreeNode> {
    ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    /**
     * The method add node to the list children,
     * if node contains a filled parent, an exception is thrown.
     *
     * @param treeNode current node for add in list child.
     * @return the result of running the superclass method to add node to the list child.
     */
    @Override
    public boolean add(TreeNode treeNode) {
        if (treeNode.getParent() != null) {
            throw new ParentExistException(bundle.getString("EMPTY_PARENT_EXCEPTION"));
        }
        return super.add(treeNode);
    }

    /**
     * The method adds nodes to the list of children from the listTreeNodes,
     * if listTreeNodes contains a node with a filled parent, an exception is thrown.
     *
     * @param listTreeNodes list of nodes to add.
     * @return the result of running the superclass method to add nodes to the list child.
     */
    @Override
    public boolean addAll(Collection<? extends TreeNode> listTreeNodes) {
        if (listTreeNodes.stream().anyMatch(n -> n.getParent() != null)) {
            throw new ParentExistException(bundle.getString("EMPTY_PARENT_EXCEPTION"));
        }
        return super.addAll(listTreeNodes);
    }

}
