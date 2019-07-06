package database.loaders.mysql;

import database.annotations.load.Load;
import structures.TreeNode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for loading STORED_PROCEDURE metadata into tree.
 */
@Load(typeMeta = TypeMeta.STORED_PROCEDURES)
public class ProceduresLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Queries.SHOW_STORED_PROCEDURE);
        node.getListChild().clear();
        while (resultSet.next()) {
            String nameProcedure = resultSet.getString("name");
            TreeNode viewNode = new TreeNode(nameProcedure, TypeMeta.STORED_PROCEDURE);
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

