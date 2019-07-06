package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for loading Index metadata into tree.
 */
@Load(typeMeta = TypeMeta.INDEX)
public class IndexLoader extends AbstructLoaders {

    @Override
    public TreeNode details(TreeNode node) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Queries.SHOW_INDEXES_TABLE + node.getParent().getParent().getNameElement());
        createAttrForDbElements(node, resultSet);
        return node;
    }

    @Override
    public TreeNode full(TreeNode node) throws SQLException {
        details(node);
        return node;
    }
}
