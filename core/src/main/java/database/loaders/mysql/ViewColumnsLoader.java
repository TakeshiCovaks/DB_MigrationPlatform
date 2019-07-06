package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for loading VIEW_COLUMN metadata into tree.
 */
@Load(typeMeta = TypeMeta.VIEW_COLUMN)
public class ViewColumnsLoader extends AbstructLoaders {

    @Override
    public TreeNode details(TreeNode node) throws SQLException {
        PreparedStatement psAttr = connection.prepareStatement(Queries.SHOW_META_VIEW_COLUMNS_ATTR);
        psAttr.setString(1, node.getParent().getNameElement());
        ResultSet resultSet = psAttr.executeQuery();
        createAttrForDbElements(node, resultSet);

        return node;
    }

    @Override
    public TreeNode full(TreeNode node) throws SQLException {
        details(node);
        return node;
    }
}
