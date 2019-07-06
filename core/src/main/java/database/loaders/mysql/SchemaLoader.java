package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for loading DATABASE metadata into tree.
 */
@Load(typeMeta = TypeMeta.DATABASE)
public class SchemaLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode root) throws SQLException {
        root.getListChild().clear();
        root.addChild(new TreeNode(TypeMeta.TABLES, TypeMeta.TABLES));
        root.addChild(new TreeNode(TypeMeta.VIEWS, TypeMeta.VIEWS));
        root.addChild(new TreeNode(TypeMeta.STORED_PROCEDURES, TypeMeta.STORED_PROCEDURES));
        root.addChild(new TreeNode(TypeMeta.FUNCTIONS, TypeMeta.FUNCTIONS));

        loadIntermediateMeta(root);

        return root;
    }

    @Override
    public TreeNode details(TreeNode node) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.SHOW_META_DB);
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
