package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for loading Foreign Key metadata into tree.
 */
@Load(typeMeta = TypeMeta.FOREIGN_KEY)
public class ForeignKeyLoader extends AbstructLoaders {

    @Override
    public TreeNode details(TreeNode node) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.SHOW_FOREIGN_KEY_TABLE_ATTR);
        preparedStatement.setString(1, node.getParent().getParent().getNameElement());
        ResultSet resultSet = preparedStatement.executeQuery();
        createAttrForDbElements(node, resultSet);
        return node;
    }

    @Override
    public TreeNode full(TreeNode node) throws SQLException {
        details(node);
        return node;
    }
}
