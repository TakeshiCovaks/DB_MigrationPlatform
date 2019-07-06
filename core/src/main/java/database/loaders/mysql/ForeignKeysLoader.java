package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for loading FOREIGN_KEYS metadata into tree.
 */
@Load(typeMeta = TypeMeta.FOREIGN_KEYS)
public class ForeignKeysLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.SHOW_FOREIGN_KEY_TABLE);
        preparedStatement.setString(1, node.getParent().getNameElement());

        ResultSet resultSet = preparedStatement.executeQuery();
        node.getListChild().clear();
        while (resultSet.next()) {
            node.addChild(new TreeNode(resultSet.getString("CONSTRAINT_NAME"), TypeMeta.FOREIGN_KEY));
        }
        return node;
    }

    @Override
    public TreeNode full(TreeNode node) throws SQLException {
        lazy(node);
        fullLoadChild(node);
        return node;
    }
}
