package strategy;

import exception.ParserException;
import structures.TreeNode;

/**
 * Interface to implement the right strategy for data deserialization.
 */
public interface DeserializeStrategy {

    /**
     * Method that starts the execution of the strategy in the instance of the class that implements this strategy.
     *
     * @param node     root node of the tree for saving dataю
     * @param fileName file name from which information will be read.
     * @return root node.
     */
    TreeNode execute(TreeNode node, String fileName) throws ParserException;
}
