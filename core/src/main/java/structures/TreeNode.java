package structures;

import exception.LoadException;
import org.apache.log4j.Logger;
import strategy.Constants;

import java.util.*;
import java.util.function.Predicate;

/**
 * The class implements the functionality of the tree node.
 * Hierarchy of relationships between instances of this class
 * form a tree structure with many children at the node for data storage
 */
public class TreeNode {

    private HashMap<String, String> attributes = new HashMap<>();
    private ChildList listChild = new ChildList();
    private String nameElement;
    private TreeNode parent;
    private String data;

    public static final Logger LOG = Logger.getLogger(TreeNode.class);
    public static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    public TreeNode() {
    }

    public TreeNode(HashMap<String, String> attributes, ChildList listChild, String nameElement, TreeNode parent, String data) {
        this.attributes = attributes;
        this.listChild = listChild;
        this.nameElement = nameElement;
        this.parent = parent;
        this.data = data;
    }

    public TreeNode(String nameElement, String data) {
        this.nameElement = nameElement;
        this.data = data;
    }

    /**
     * Prepare result list for search and init search (Depth).
     *
     * @param comparisonParameters check name element or key/value of attributes.
     */
    public ArrayList<TreeNode> searchDepth(Predicate<TreeNode> comparisonParameters) {

        ArrayList<TreeNode> resultListNodes = new ArrayList<>();
        searchDepthAndInsertInResultList(resultListNodes, comparisonParameters);

        return resultListNodes;
    }

    /**
     * Search node by name element or key/value attribute
     *
     * @param comparisonParameters check name element or key/value of attributes.
     */
    private void searchDepthAndInsertInResultList(ArrayList<TreeNode> resultListNodes, Predicate<TreeNode> comparisonParameters) {
        for (TreeNode node : listChild) {
            if (comparisonParameters.test(node)) {
                resultListNodes.add(node);
            }
            node.searchDepthAndInsertInResultList(resultListNodes, comparisonParameters);
        }
    }

    /**
     * Search node by name element or key/value attribute   (Width)
     *
     * @param comparisonParameters check name element or key/value of attributes.
     */
    public ArrayList<TreeNode> searchWidth(Predicate<TreeNode> comparisonParameters) {

        ArrayList<TreeNode> resultListNodes = new ArrayList();
        Queue<TreeNode> queueForWidthSearch = new LinkedList();
        queueForWidthSearch.add(this);

        while (!queueForWidthSearch.isEmpty()) {
            TreeNode myNode = queueForWidthSearch.poll();
            if (comparisonParameters.test(myNode)) {
                resultListNodes.add(myNode);
            }
            queueForWidthSearch.addAll(myNode.getListChild());
        }
        return resultListNodes;
    }

    /**
     * The method will return the node at the specified path.
     *
     * @param arrPath path for search node.
     * @return node for load.
     */
    public TreeNode searchNodeByPath(String[] arrPath) {

        if (arrPath.length > 0) {
            if (this.getNameElement().equalsIgnoreCase(arrPath[0]) & arrPath.length == 1) {
                return this;
            } else if (arrPath.length == 1) {
                return null;
            }

            for (TreeNode currentNode : this.getListChild()) {
                if (currentNode.getNameElement().equalsIgnoreCase(arrPath[1])) {
                    String[] newArr = new String[arrPath.length - 1];
                    System.arraycopy(arrPath, 1, newArr, 0, arrPath.length - 1);
                    return currentNode.searchNodeByPath(newArr);
                }
            }

            LOG.info(bundle.getString("noFoundNodeThePath"));
        } else {
            LOG.info(bundle.getString("noFoundNodeThePath"));
            throw new LoadException();
        }
        return null;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TreeNode myNode = (TreeNode) obj;

        if (Objects.equals(data, myNode.data)
                && Objects.equals(attributes, myNode.attributes)
                && Objects.equals(nameElement, myNode.nameElement)) {

            if (listChild.size() == myNode.listChild.size()) {
                for (int i = 0; i < listChild.size(); i++) {
                    if (!(listChild.get(i).equals(myNode.listChild.get(i)))) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes, listChild, nameElement, parent, data);
    }

    public TreeNode getParent() {
        return parent;
    }

    public ArrayList<TreeNode> getListChild() {
        return listChild;
    }

    /**
     * Adds to the list of children nodes.
     *
     * @param node node for added as a child
     */
    public boolean addChild(TreeNode node) {
        listChild.add(node);
        node.setParent(this);
        return true;
    }

    /**
     * Method add list child for current node.
     */
    public boolean addAllChild(ChildList childList) {
        if (listChild.addAll(childList)) {
            for (TreeNode node : childList) {
                node.setParent(this);
            }
            return true;
        }
        return false;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public String getNameElement() {
        return nameElement;
    }

    public void setNameElement(String nameElement) {
        this.nameElement = nameElement;
    }

    /**
     * Method get root for Tree.
     *
     * @return tree root.
     */
    public TreeNode getRootTree() {
        if (this.getParent() != null) {
            return this.getParent().getRootTree();
        }
        return this;
    }

    public void setListChild(ChildList listChild) {
        this.listChild = listChild;
    }

    /**
     * Method return TreeNode path in tree.
     *
     * @return actual path node.
     */
    public String getPath() {
        if (this.getParent() != null) {
            return this.getParent().getPath() + "." + this.getNameElement();
        }
        return this.getNameElement();
    }
}



