package database.printers;

import structures.TreeNode;

import java.util.Map;


/**
 * Interface must be used with any printer implementation.
 */
public interface Printers{

    /**
     * Print implementation method for the current tree node.
     *
     * @param node tree node for print.
     * @param mapInstance map with instance printers.
     */
    String print(TreeNode node, Map<String, Printers> mapInstance);
}
