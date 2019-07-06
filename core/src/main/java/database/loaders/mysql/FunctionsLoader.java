package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for loading FUNCTIONS metadata into tree.
 */
@Load(typeMeta = TypeMeta.FUNCTIONS)
public class FunctionsLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Queries.SHOW_FUNCTION);
        node.getListChild().clear();
        while (resultSet.next()) {
            String nameFunction = resultSet.getString("name");
            TreeNode viewNode = new TreeNode(nameFunction, TypeMeta.FUNCTION);
            node.addChild(viewNode);
        }
        return node;
    }

    @Override
    public TreeNode full(TreeNode node) throws SQLException {
        fullLoadChild(node);
        return node;
    }
}
