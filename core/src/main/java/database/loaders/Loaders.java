package database.loaders;

import structures.TreeNode;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Interface for loading database metadata items based on download type load.
 */
public interface Loaders {

    /**
     * Load tree structure from metadata database.
     * This method load metadata hierarchy with names only.
     *
     * @param node node tree.
     */
    TreeNode lazy(TreeNode node) throws SQLException;

    /**
     * Load tree structure from metadata database.
     * First node has full attribute(metadata database)
     *
     * @param node node tree.
     * @return
     */
    TreeNode details(TreeNode node) throws SQLException;

    /**
     * Load tree structure from metadata database.
     * This method load full metadata hierarchy with attributes.
     *
     * @param node node tree.
     */
    TreeNode full(TreeNode node) throws SQLException;

    void setConnection(Connection connection);

    void setMapInstance(Map<String, Loaders> mapInstance);
}
