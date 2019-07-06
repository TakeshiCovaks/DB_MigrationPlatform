package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for loading VIEWS metadata into tree.
 */
@Load(typeMeta = TypeMeta.VIEWS)
public class ViewsLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(Queries.SHOW_VIEW);
        node.getListChild().clear();
        while (resultSet.next()) {
            String nameTable = resultSet.getString(1);
            TreeNode viewNode = new TreeNode(nameTable, TypeMeta.VIEW);
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