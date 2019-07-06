package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for loading INDEXES metadata into tree.
 */
@Load(typeMeta = TypeMeta.INDEXES)
public class IndexesLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Queries.SHOW_INDEXES_TABLE + node.getParent().getNameElement());
        node.getListChild().clear();
        while (resultSet.next()) {
            node.addChild(new TreeNode(resultSet.getString("Key_name"), TypeMeta.INDEX));
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
