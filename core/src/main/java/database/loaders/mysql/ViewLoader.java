package database.loaders.mysql;

import database.annotations.load.Load;
import structures.ChildList;
import structures.TreeNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Class for loading VIEW metadata into tree.
 */
@Load(typeMeta = TypeMeta.VIEW)
public class ViewLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(Queries.SHOW_VIEW_COLUMNS);
        statement.setString(1, node.getRootTree().getNameElement());
        statement.setString(2, node.getNameElement());

        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData rsmd = resultSet.getMetaData();

        node.getListChild().clear();
        while (resultSet.next()) {
            TreeNode columnsView = new TreeNode(resultSet.getString("COLUMN_NAME"), TypeMeta.VIEW);

            HashMap<String, String> attr = new HashMap<>();
            for (int i = 1; i < rsmd.getColumnCount(); i++) {
                String nameColumnMeta = rsmd.getColumnLabel(i);
                attr.put(nameColumnMeta, resultSet.getString(nameColumnMeta));
            }
            columnsView.setAttributes(attr);
            node.addChild(columnsView);
        }

        return node;
    }

    @Override
    public TreeNode details(TreeNode node) throws SQLException {

        PreparedStatement psAttr = connection.prepareStatement(Queries.SHOW_META_VIEW_ATTR);
        psAttr.setString(1, node.getNameElement());
        ResultSet resultSet = psAttr.executeQuery();
        createAttrForDbElements(node, resultSet);

        return node;
    }

    @Override
    public TreeNode full(TreeNode node) throws SQLException {
        lazy(node);
        details(node);
        fullLoadChild(node);

        return node;
    }
}
