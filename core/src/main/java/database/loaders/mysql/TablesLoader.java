package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for loading TABLES metadata into tree.
 */
@Load(typeMeta = TypeMeta.TABLES)
public class TablesLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(Queries.SHOW_TABLES);
        node.getListChild().clear();
        while (resultSet.next()) {
            String nameTable = resultSet.getString(1);
            TreeNode tableNode = new TreeNode(nameTable, TypeMeta.TABLE);
            node.addChild(tableNode);
        }
        return node;
    }

    @Override
    public TreeNode full(TreeNode node) throws SQLException {
        fullLoadChild(node);
        return node;
    }
}
