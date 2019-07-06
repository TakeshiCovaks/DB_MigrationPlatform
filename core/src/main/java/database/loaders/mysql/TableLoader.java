package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for loading TABLE metadata into tree.
 */
@Load(typeMeta = TypeMeta.TABLE)
public class TableLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {
        node.getListChild().clear();
        node.addChild(new TreeNode(TypeMeta.COLUMNS, TypeMeta.COLUMNS));
        node.addChild(new TreeNode(TypeMeta.INDEXES, TypeMeta.INDEXES));
        node.addChild(new TreeNode(TypeMeta.FOREIGN_KEYS, TypeMeta.FOREIGN_KEYS));
        node.addChild(new TreeNode(TypeMeta.TRIGGERS, TypeMeta.TRIGGERS));

        loadIntermediateMeta(node);

        return node;
    }

    @Override
    public TreeNode details(TreeNode node) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.SHOW_META_TABLE);
        preparedStatement.setString(1, node.getNameElement());
        ResultSet resultSet = preparedStatement.executeQuery();

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
