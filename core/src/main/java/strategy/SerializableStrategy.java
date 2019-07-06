
package strategy;

import exception.SerialzableException;
import structures.TreeNode;

/**
 * Interface to implement the right strategy for data serialization.
 */
public interface SerializableStrategy {

    /**
     * Method that starts the execution of the strategy in the instance of the class that implements this strategy.
     *
     * @param node     root node for saving.
     * @param fileName file name for new file saving.
     */
    void execute(TreeNode node, String fileName) throws SerialzableException;

}
