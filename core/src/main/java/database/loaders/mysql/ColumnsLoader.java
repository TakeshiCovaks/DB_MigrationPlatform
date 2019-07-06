package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for loading COLUMNS metadata into tree.
 */
@Load(typeMeta = TypeMeta.COLUMNS)
public class ColumnsLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Queries.SHOW_COLUMNS_TABLE + node.getParent().getNameElement());
        node.getListChild().clear();
        while (resultSet.next()) {
            node.addChild(new TreeNode(resultSet.getString("Field"), TypeMeta.COLUMN));
        }
        return node;
    }

    @Override
    public TreeNode full(TreeNode node) throws SQLException {
        fullLoadChild(node);
        return node;
    }
}
