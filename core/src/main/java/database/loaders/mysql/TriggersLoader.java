package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for loading TRIGGERS metadata into tree.
 */
@Load(typeMeta = TypeMeta.TRIGGERS)
public class TriggersLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.SHOW_TRIGGERS_TABLE);
        preparedStatement.setString(1, node.getParent().getNameElement());

        ResultSet resultSet = preparedStatement.executeQuery();
        node.getListChild().clear();
        while (resultSet.next()) {
            node.addChild(new TreeNode(resultSet.getString("TRIGGER_NAME"), TypeMeta.TRIGGER));
        }
        return node;
    }

    @Override
    public TreeNode full(TreeNode node) throws SQLException {
        fullLoadChild(node);
        return node;
    }
}
