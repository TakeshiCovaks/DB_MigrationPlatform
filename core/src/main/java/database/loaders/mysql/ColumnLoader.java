package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for loading COLUMN metadata into tree.
 */
@Load(typeMeta = TypeMeta.COLUMN)
public class ColumnLoader extends AbstructLoaders {

    @Override
    public TreeNode details(TreeNode node) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.SHOW_COLUMNS_TABLE + node.getParent().getParent().getNameElement() + " ?");
        preparedStatement.setString(1, node.getNameElement());
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
